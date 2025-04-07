package tt.smart.agency.sql.inner;

import lombok.Getter;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.*;

/**
 * <p>
 * 类元数据
 * </p>
 *
 * @author MC_Yang
 * @version V1.0
 **/
@Getter
public class ClassMetadata {

    /**
     * 类名
     */
    private final String className;

    /**
     * 字段
     */
    private final Map<String, Field> fields;

    /**
     * setter 方法
     */
    private final Map<String, Method> setterMethods;

    /**
     * getter 方法
     */
    private final Map<String, Method> getterMethods;

    /**
     * 正常的方法
     */
    private final Map<String, Method> normalMethods;

    public ClassMetadata(Class<?> clazz) {
        this(clazz, Collections.emptyList(), Collections.emptyList());
    }

    public ClassMetadata(Class<?> clazz, List<String> ignoreFields, List<String> ignoreMethods) {
        className = clazz.getName();
        fields = new HashMap<>();
        setterMethods = new HashMap<>();
        getterMethods = new HashMap<>();
        normalMethods = new HashMap<>();
        Class<?> c = clazz;
        while (c != null) {
            Field[] declaredFields = c.getDeclaredFields();
            Method[] declaredMethods = c.getDeclaredMethods();
            Arrays.stream(declaredFields).filter(e -> !ignoreFields.contains(e.getName())).forEach(e -> fields.putIfAbsent(e.getName(), e));
            for (Method declaredMethod : declaredMethods) {
                if (!declaredMethod.isBridge() && !ignoreMethods.contains(declaredMethod.getName())) {
                    if (declaredMethod.getName().startsWith("set") && !setterMethods.containsKey(declaredMethod.getName())) {
                        setterMethods.put(declaredMethod.getName(), declaredMethod);
                    } else if ((declaredMethod.getName().startsWith("get") || declaredMethod.getName().startsWith("is")) && !getterMethods.containsKey(declaredMethod.getName())) {
                        getterMethods.put(declaredMethod.getName(), declaredMethod);
                    } else if (!normalMethods.containsKey(declaredMethod.getName())) {
                        normalMethods.put(declaredMethod.getName(), declaredMethod);
                    }
                }
            }
            c = c.getSuperclass();
        }
    }

    public List<Field> getFields() {
        return new ArrayList<>(fields.values());
    }

    public List<Method> getSetterMethods() {
        return new ArrayList<>(setterMethods.values());
    }

    public List<Method> getGetterMethods() {
        return new ArrayList<>(getterMethods.values());
    }

    public List<Method> getNormalMethods() {
        return new ArrayList<>(normalMethods.values());
    }

    public Field getField(String fieldName) {
        return fields.get(fieldName);
    }

    public Method getMethod(String methodName) {
        Method method;
        if ((method = setterMethods.get(methodName)) != null) {
            return method;
        }
        if ((method = getterMethods.get(methodName)) != null) {
            return method;
        }
        if ((method = normalMethods.get(methodName)) != null) {
            return method;
        }
        return null;
    }

    public Method getSetterMethod(String fieldName) {
        return setterMethods.get("set" + title(fieldName));
    }

    public Method getGetterMethod(String fieldName) {
        String titleFieldName = title(fieldName);
        return getterMethods.getOrDefault("get" + titleFieldName, getterMethods.get("is" + titleFieldName));
    }

    public Method getNormalMethod(String methodName) {
        return normalMethods.get(methodName);
    }

    private static String title(String fieldName) {
        if (fieldName == null || fieldName.isEmpty()) {
            return "";
        }
        if (fieldName.length() == 1) {
            return fieldName.toUpperCase();
        }
        return fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
    }
}
