package tt.smart.agency.component.word.domain.params;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * <p>
 * Excel 导出对象
 * </p>
 *
 * @author YangMC
 * @version V1.0
 **/
@Data
@EqualsAndHashCode(callSuper = true)
public class ExcelListEntity extends PoiBaseParams {

    /**
     * 数据源
     */
    private List<?> list;

    /**
     * 实体类对象
     */
    private Class<?> clazz;

    /**
     * 表头行数
     */
    private int headRows = 1;

}
