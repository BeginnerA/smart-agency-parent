package tt.smart.agency.component.word.domain.params;

import lombok.Data;
import tt.smart.agency.component.word.domain.BaseTypeConstants;

import java.lang.reflect.Method;
import java.util.List;

/**
 * <p>
 * Excel 导入导出基础对象类
 * </p>
 *
 * @author MC_Yang
 * @version V1.0
 **/
@Data
public class ExcelBaseEntity {
    /**
     * 名称
     */
    protected String name;
    /**
     * 组名称
     */
    protected String groupName;
    /**
     * 类型
     */
    private int type = BaseTypeConstants.STRING_TYPE;
    /**
     * 数据库格式
     */
    private String databaseFormat;
    /**
     * 导出日期格式
     */
    private String format;
    /**
     * 导出日期格式
     */
    private String[] replace;
    /**
     * 字典名称
     */
    private String dict;
    /**
     * 方法
     */
    private Method method;
    /**
     * method 需要的参数
     */
    private List<Object> methodParams;
    /**
     * 这个是不是超链接,如果是需要实现接口返回对象
     */
    private boolean hyperlink;
    /**
     * 固定的列
     */
    private Integer fixedIndex;
    /**
     * 时区
     */
    private String timezone;
    /**
     * 是否插入下拉
     */
    private boolean addressList;
    /**
     * 方法列表
     */
    private List<Method> methods;
    /**
     * methods 需要的参数
     */
    private List<List<Object>> methodsParams;

}
