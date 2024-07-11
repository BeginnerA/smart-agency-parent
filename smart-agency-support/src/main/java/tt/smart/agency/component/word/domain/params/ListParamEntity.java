package tt.smart.agency.component.word.domain.params;

import lombok.Data;

import java.io.Serializable;

/**
 * <p>
 * Excel 对象导出结构
 * </p>
 *
 * @author YangMC
 * @version V1.0
 **/
@Data
public class ListParamEntity implements Serializable {
    /**
     * 唯一值，在遍历中重复使用
     */
    public static final String SINGLE = "single";
    /**
     * 属于数组类型
     */
    public static final String LIST = "list";
    /**
     * 属性名称
     */
    private String name;
    /**
     * 目标
     */
    private String target;
    /**
     * 当是唯一值的时候直接求出值
     */
    private Object value;
    /**
     * 数据类型，SINGLE || LIST
     */
    private String type;

}
