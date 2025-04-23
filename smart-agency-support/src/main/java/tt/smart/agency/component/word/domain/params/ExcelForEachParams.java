package tt.smart.agency.component.word.domain.params;

import lombok.Data;
import org.apache.poi.ss.usermodel.CellStyle;

import java.io.Serializable;
import java.util.Stack;

/**
 * <p>
 * 模板 for each 的参数
 * </p>
 *
 * @author MC_Yang
 * @version V1.0
 **/
@Data
public class ExcelForEachParams implements Serializable {

    /**
     * key
     */
    private String name;
    /**
     * key
     */
    private Stack<String> tempName;
    /**
     * 模板的 cellStyle
     */
    private CellStyle cellStyle;
    /**
     * 行高
     */
    private short height;
    /**
     * 列宽(仅横向遍历支持)
     */
    private int width;
    /**
     * 常量值
     */
    private String constValue;
    /**
     * 列合并
     */
    private int colspan = 1;
    /**
     * 行合并
     */
    private int rowspan = 1;
    /**
     * 行合并
     */
    private boolean collectCell;
    /**
     * 需要统计
     */
    private boolean needSum;
    /**
     * 是否需要创建新行
     */
    private boolean isCreate;
    /**
     * 是嵌套迭代器
     */
    private boolean isNestIterator;

}
