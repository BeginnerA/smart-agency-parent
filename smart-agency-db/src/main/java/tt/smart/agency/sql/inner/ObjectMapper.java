package tt.smart.agency.sql.inner;

import tt.smart.agency.sql.annotation.Column;
import tt.smart.agency.sql.annotation.Primary;
import tt.smart.agency.sql.annotation.Table;
import tt.smart.agency.sql.builder.sql.Tuple2;
import tt.smart.agency.sql.config.GlobalConfig;
import tt.smart.agency.sql.domain.Alias;
import tt.smart.agency.sql.exception.LambdaException;
import tt.smart.agency.sql.exception.SqlBuilderException;

import java.lang.annotation.Annotation;
import java.lang.invoke.SerializedLambda;
import java.lang.reflect.*;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * <p>
 * 对象映射工具
 * </p>
 *
 * @author MC_Yang
 * @version V1.0
 **/
public class ObjectMapper {

    private ObjectMapper() {
    }

    /**
     * 规范名称连接符
     */
    private static final String SEP = "_";

    /**
     * 类元数据映射
     */
    private final static Map<Class<?>, ClassMetadata> CLASS_METADATA_MAP = new ConcurrentHashMap<>();

    /**
     * 别名
     */
    private final static Map<Class<?>, List<Alias>> PRIMARY_MAP = new ConcurrentHashMap<>();

    /**
     * 构造函数
     */
    private final static Map<String, Constructor<?>> CONSTRUCTOR_MAP = new ConcurrentHashMap<>();

    /**
     * 单列对象
     */
    private final static Map<String, Object> SINGLE_OBJECT_MAP = new ConcurrentHashMap<>();

    /**
     * 类字段列映射
     */
    private final static Map<String, String> CLASS_FIELD_COLUMN_MAP = new ConcurrentHashMap<>();

    /**
     * 获取表名
     *
     * @param clazz 类
     * @return 表名
     */
    public static String getTableName(Class<?> clazz) {
        Table table = clazz.getAnnotation(Table.class);
        if (table == null) {
            return humpNameToUnderlinedName(clazz.getSimpleName(), SEP);
        }
        return table.value();
    }

    /**
     * 获取严格的列名
     *
     * @param clazz 类
     * @return 列
     */
    public static List<String> getStrictColumnName(Class<?> clazz) {
        if (GlobalConfig.OPEN_LAMBDA_TABLE_NAME_MODE) {
            String tableName = getTableName(clazz);
            return getColumnFields(clazz).stream().map(e -> tableName + "." + e.getOrigin()).collect(Collectors.toList());
        }
        return getColumns(clazz);
    }

    /**
     * 获取列
     *
     * @param clazz 类
     * @return 列
     */
    public static List<String> getColumns(Class<?> clazz) {
        return getColumnFields(clazz).stream().map(Alias::getOrigin).collect(Collectors.toList());
    }

    /**
     * 获取类注解
     *
     * @param clazz           类
     * @param fieldName       字段名
     * @param annotationClass 注释
     * @param <T>             注解
     * @return 注解
     */
    public static <T extends Annotation> T getAnnotation(Class<?> clazz, String fieldName, Class<T> annotationClass) {
        ClassMetadata meta = getClassMeta(clazz, Collections.emptyList(), Collections.emptyList());
        Field field = meta.getField(fieldName);
        T annotation = null;
        if (field != null) {
            annotation = field.getAnnotation(annotationClass);
        }
        if (annotation == null) {
            Method getterMethod = meta.getGetterMethod(fieldName);
            if (getterMethod != null) {
                annotation = getterMethod.getAnnotation(annotationClass);
            }
            if (annotation == null) {
                Method setterMethod = meta.getSetterMethod(fieldName);
                if (setterMethod != null) {
                    annotation = setterMethod.getAnnotation(annotationClass);
                }
            }
        }
        return annotation;
    }

    /**
     * 获取列字段
     *
     * @param clazz 类
     * @return 字段
     */
    public static List<Alias> getColumnFields(Class<?> clazz) {
        ClassMetadata meta = getClassMeta(clazz, Collections.emptyList(), Collections.emptyList());
        if (meta == null) {
            return Collections.emptyList();
        }
        List<Alias> columns = new ArrayList<>();
        meta.getFields().stream().filter(e -> !Modifier.isFinal(e.getModifiers())).forEach(e -> {
            String columnName = getColumnName(clazz, e.getName()), aliasName = e.getName();
            columns.add(Alias.of(columnName, aliasName));
        });
        return columns;
    }

    /**
     * 获取对象属性
     *
     * @param obj   对象
     * @param attr  属性
     * @param index 索引
     * @return 属性
     * @throws IllegalAccessException    非法访问异常
     * @throws InvocationTargetException 调用目标异常
     */
    public static Object getAttr(Object obj, String[] attr, int index) throws IllegalAccessException,
            InvocationTargetException {
        if (obj == null) {
            return null;
        }
        Object result = obj;
        if (obj instanceof Collection<?> o) {
            if (o.isEmpty()) {
                return null;
            }
            result = o.stream().map(e -> ObjectMapper.handleSubGetAttr(e, attr, index)).filter(Objects::nonNull)
                    .collect(Collectors.toList());
        } else if (obj instanceof Map) {
            result = ((Map<?, ?>) obj).get(attr);
        } else {
            result = getFieldValue(obj, attr[index]);
        }
        if (index == attr.length - 1) {
            return result;
        }
        return getAttr(result, attr, index + 1);
    }

    /**
     * 处理对象 get 属性
     *
     * @param obj   对象
     * @param attr  属性
     * @param index 索引
     * @return 属性
     */
    private static Object handleSubGetAttr(Object obj, String[] attr, int index) {
        try {
            return getAttr(obj, attr, index);
        } catch (IllegalAccessException | InvocationTargetException illegalAccessException) {
            throw new SqlBuilderException("属性获取失败，类型: \"" + obj.getClass().getName() + "\", attr: "
                    + Arrays.toString(attr), illegalAccessException);
        }
    }

    /**
     * 获取字段
     *
     * @param clazz     类
     * @param fieldName 字段名
     * @return 字段
     */
    public static Field getField(Class<?> clazz, String fieldName) {
        ClassMetadata classMeta = getClassMeta(clazz, Collections.emptyList(), Collections.emptyList());
        if (classMeta == null) {
            return null;
        }
        return classMeta.getField(fieldName);
    }

    /**
     * 设置字段值
     *
     * @param obj       对象
     * @param fieldName 字段名
     * @param value     值
     * @param <T>       对象
     * @throws IllegalAccessException 非法访问异常
     */
    public static <T> void setFieldValue(T obj, String fieldName, Object value) throws IllegalAccessException {
        Field field = getField(obj.getClass(), fieldName);
        if (field != null) {
            if (!field.canAccess(obj)) {
                field.setAccessible(true);
            }
            field.set(obj, value);
        }
    }

    /**
     * 获取字段值
     *
     * @param obj       对象
     * @param fieldName 字段名
     * @param <T>       对象
     * @param <V>       值
     * @return 字段值
     * @throws IllegalAccessException    非法访问异常
     * @throws InvocationTargetException 调用目标异常
     */
    @SuppressWarnings({"unchecked"})
    public static <T, V> V getFieldValue(T obj, String fieldName) throws IllegalAccessException,
            InvocationTargetException {
        ClassMetadata classMeta = getClassMeta(obj.getClass(), Collections.emptyList(), Collections.emptyList());
        if (classMeta == null) {
            return null;
        }

        Field field = classMeta.getField(fieldName);

        if (field == null) {
            Method method = classMeta.getGetterMethod(fieldName);
            if (method != null) {
                return (V) method.invoke(obj);
            } else {
                return null;
            }
        }

        if (!field.canAccess(obj)) {
            field.setAccessible(true);
        }
        return (V) field.get(obj);
    }

    /**
     * 获取列名
     *
     * @param clazz     类
     * @param fieldName 字段名
     * @return 列名
     */
    public static String getColumnName(Class<?> clazz, String fieldName) {
        String classFieldId = clazz.getName() + "@" + fieldName;
        String columnName = CLASS_FIELD_COLUMN_MAP.get(classFieldId);
        if (columnName == null) {
            Column column = getAnnotation(clazz, fieldName, Column.class);
            if (column != null) {
                columnName = column.value();
            } else {
                columnName = humpNameToUnderlinedName(fieldName, SEP);
            }
            CLASS_FIELD_COLUMN_MAP.put(classFieldId, columnName);
        }
        return columnName;
    }

    /**
     * 获得实例
     *
     * @param clazz          类
     * @param parameterTypes 参数类型
     * @param params         参数
     * @param <T>            类
     * @return 实例
     * @throws NoSuchMethodException     没有这样的方法例外
     * @throws InvocationTargetException 调用目标异常
     * @throws InstantiationException    实例化异常
     * @throws IllegalAccessException    非法访问异常
     */
    public static <T> T getInstance(Class<T> clazz, Class<?>[] parameterTypes, Object... params)
            throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        Constructor<T> constructor = getConstructor(clazz, parameterTypes);
        return constructor.newInstance(params);
    }

    /**
     * 获取单列对象
     *
     * @param clazz 类
     * @param args  参数
     * @return 对象
     * @throws NoSuchMethodException     没有这样的方法例外
     * @throws InvocationTargetException 调用目标异常
     * @throws InstantiationException    实例化异常
     * @throws IllegalAccessException    非法访问异常
     */
    public static Object getSingleObject(Class<?> clazz, Object... args) throws NoSuchMethodException,
            InvocationTargetException, InstantiationException, IllegalAccessException {
        String key = clazz.getName() + "@" + Arrays.stream(args).map(e -> e.getClass().getName())
                .collect(Collectors.joining("#"));
        Object obj = SINGLE_OBJECT_MAP.get(key);
        if (obj == null) {
            synchronized (SINGLE_OBJECT_MAP) {
                obj = SINGLE_OBJECT_MAP.get(key);
                if (obj == null) {
                    if (args.length == 0) {
                        obj = clazz.getConstructor().newInstance();
                    } else {
                        obj = clazz.getConstructor(Arrays.stream(args).map(Object::getClass).toArray(Class[]::new));
                    }
                    SINGLE_OBJECT_MAP.put(key, obj);
                }
            }
        }
        return obj;
    }

    /**
     * 获取别名
     *
     * @param clazz 类
     * @return 别名
     */
    public static List<Alias> getPrimaries(Class<?> clazz) {
        List<Alias> primaries = PRIMARY_MAP.get(clazz);
        if (primaries == null) {
            synchronized (PRIMARY_MAP) {
                primaries = PRIMARY_MAP.get(clazz);
                if (primaries == null) {
                    ClassMetadata classMeta = getClassMeta(clazz, Collections.emptyList(), Collections.emptyList());
                    if (classMeta == null) {
                        return null;
                    }
                    List<Field> fields = classMeta.getFields();
                    primaries = fields.stream().filter(e -> getAnnotation(clazz, e.getName(), Primary.class) != null)
                            .map(e -> Alias.of(getColumnName(clazz, e.getName()), e.getName())).distinct()
                            .collect(Collectors.toList());
                    PRIMARY_MAP.put(clazz, primaries);
                }
            }
        }
        return primaries;
    }

    /**
     * 获取列和值
     *
     * @param obj    对象
     * @param fields 字段
     * @param <T>    对象
     * @return 列和值
     * @throws IllegalAccessException    非法访问异常
     * @throws InvocationTargetException 调用目标异常
     */
    public static <T> Map<String, Object> getColumnAndValues(T obj, List<Alias> fields) throws IllegalAccessException,
            InvocationTargetException {
        if (fields == null || fields.isEmpty()) {
            return Collections.emptyMap();
        }
        Map<String, Object> fieldMapping = new HashMap<>();
        for (Alias field : fields) {
            Object value = getFieldValue(obj, field.getAlias());
            if (value != null) {
                fieldMapping.put(field.getOrigin(), value);
            }
        }
        return fieldMapping;
    }

    /**
     * 驼峰名称转下划线名称
     *
     * @param name 名称
     * @param sep  连接符
     * @return 下划线名称
     */
    public static String humpNameToUnderlinedName(String name, String sep) {
        StringBuilder nameBuilder = new StringBuilder();
        for (char c : name.toCharArray()) {
            if (c >= 'A' && c <= 'Z') {
                if (!nameBuilder.isEmpty()) {
                    nameBuilder.append(sep);
                }
                nameBuilder.append((char) (c + 32));
            } else {
                nameBuilder.append(c);
            }
        }
        return nameBuilder.toString();
    }

    /**
     * 获取 lambda 实现类和方法名
     *
     * @param lambda lambda
     * @return 实现类和方法名元组
     */
    public static Tuple2<String, String> getLambdaImplementClassAndMethodName(Object lambda) {
        Method method;
        try {
            method = lambda.getClass().getDeclaredMethod("writeReplace");
            method.setAccessible(true);
            SerializedLambda serializedLambda = (SerializedLambda) method.invoke(lambda);
            return Tuple2.of(serializedLambda.getImplClass().replace("/", "."),
                    serializedLambda.getImplMethodName());
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            throw new LambdaException("Lambda 无法提取有效的实现方法名。", e);
        }
    }

    /**
     * 获取类构造函数
     *
     * @param clazz          clazz
     * @param parameterTypes 参数类型
     * @param <T>            类
     * @return 类构造函数
     * @throws NoSuchMethodException 没有这样的方法例外
     */
    @SuppressWarnings({"unchecked"})
    protected static <T> Constructor<T> getConstructor(Class<T> clazz, Class<?>[] parameterTypes)
            throws NoSuchMethodException {
        String key = clazz.getName() + "@" + Arrays.stream(parameterTypes).map(Class::getName)
                .collect(Collectors.joining("#"));
        Constructor<?> constructor = CONSTRUCTOR_MAP.get(key);
        if (constructor == null) {
            synchronized (CONSTRUCTOR_MAP) {
                constructor = CONSTRUCTOR_MAP.get(key);
                if (constructor == null) {
                    constructor = clazz.getDeclaredConstructor(parameterTypes);
                    if (!constructor.canAccess(null)) {
                        constructor.setAccessible(true);
                    }
                    CONSTRUCTOR_MAP.put(key, constructor);
                }
            }
        }
        return (Constructor<T>) constructor;
    }

    /**
     * 获取类元数据
     *
     * @param clazz         clazz
     * @param ignoreFields  忽略字段
     * @param ignoreMethods 忽略方法
     * @return ClassMetadata
     */
    protected static ClassMetadata getClassMeta(Class<?> clazz, List<String> ignoreFields, List<String> ignoreMethods) {
        ClassMetadata classMetadata = CLASS_METADATA_MAP.get(clazz);
        if (classMetadata == null) {
            synchronized (CLASS_METADATA_MAP) {
                classMetadata = CLASS_METADATA_MAP.get(clazz);
                if (classMetadata == null) {
                    classMetadata = new ClassMetadata(clazz, ignoreFields, ignoreMethods);
                    CLASS_METADATA_MAP.put(clazz, classMetadata);
                }
            }
        }
        return classMetadata;
    }
}
