package tt.smart.agency.sql.tools;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import tt.smart.agency.sql.builder.sql.LambdaFunction;
import tt.smart.agency.sql.builder.sql.Tuple2;
import tt.smart.agency.sql.config.GlobalConfig;
import tt.smart.agency.sql.exception.LambdaException;
import tt.smart.agency.sql.inner.ObjectMapper;

/**
 * <p>
 * lambda 工具
 * </p>
 *
 * @author MC_Yang
 * @version V1.0
 **/
@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class LambdaTools {

    /**
     * 获取字段名
     *
     * @param lambda LambdaFunction
     * @param <T>    LambdaFunction
     * @return 字段名
     */
    public static <T extends LambdaFunction<?, ?>> String getFieldName(T lambda) {
        return getClassAndFieldName(lambda).t2;
    }

    /**
     * 获取列名
     *
     * @param lambda LambdaFunction
     * @param <T>    LambdaFunction
     * @return 列名
     */
    public static <T extends LambdaFunction<?, ?>> String getColumnName(T lambda) {
        Tuple2<String, String> classAndField = getClassAndFieldName(lambda);
        try {
            // 支持 @Column 注解
            Class<?> lambdaClass = Class.forName(classAndField.t1);
            // 使用 table.column 方式
            if (GlobalConfig.OPEN_LAMBDA_TABLE_NAME_MODE) {
                return ObjectMapper.getTableName(lambdaClass) + "."
                        + ObjectMapper.getColumnName(lambdaClass, classAndField.t2);
            }
            return ObjectMapper.getColumnName(lambdaClass, classAndField.t2);
        } catch (ClassNotFoundException e) {
            log.warn("lambda 类: {} 无法加载，忽略", classAndField.t1, e);
        }
        return ObjectMapper.humpNameToUnderlinedName(classAndField.t2, "_");
    }

    /**
     * 获取类和字段名
     *
     * @param lambda LambdaFunction
     * @param <T>    LambdaFunction
     * @return 类和字段名元组
     */
    private static <T extends LambdaFunction<?, ?>> Tuple2<String, String> getClassAndFieldName(T lambda) {
        Tuple2<String, String> classAndMethod = ObjectMapper.getLambdaImplementClassAndMethodName(lambda);
        String fieldName;
        if (classAndMethod.t2.startsWith("is")) {
            fieldName = classAndMethod.t2.substring(2);
        } else if (classAndMethod.t2.startsWith("get") || classAndMethod.t2.startsWith("set")) {
            fieldName = classAndMethod.t2.substring(3);
        } else {
            throw new LambdaException("解析属性名错误 '" + classAndMethod.t2 + "'.  没有以“is”、“get”或“set”开头。");
        }
        if (fieldName.length() == 1 || (fieldName.length() > 1 && !Character.isUpperCase(fieldName.charAt(1)))) {
            fieldName = fieldName.substring(0, 1).toLowerCase() + fieldName.substring(1);
        }
        return Tuple2.of(classAndMethod.t1, fieldName);
    }

}
