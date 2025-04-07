package tt.smart.agency.sql.constant;

/**
 * <p>
 * 常量
 * </p>
 *
 * @author MC_Yang
 * @version V1.0
 **/
public interface CommonConstant {

    /**
     * 空格
     */
    String BLANK_SPACE = " ";
    /**
     * 点
     */
    String POINT = ".";
    /**
     * 逗号
     */
    String COMMA = ",";
    /**
     * 分号
     */
    String SEMICOLON = ";";
    /**
     * 空字符串
     */
    String EMPTY_STRING = "";
    /**
     * 空对象数组
     */
    Object[] EMPTY_OBJECT_ARRAY = new Object[0];
    /**
     * 条件构造函数参数类型
     */
    Class<?>[] CONDITION_CONSTRUCTOR_PARAMETER_TYPES = new Class[]{Boolean.class, String.class, Object[].class,
            String.class, Object[].class};

}
