package tt.smart.agency.component.word.domain.params;

import lombok.Data;
import tt.smart.agency.component.word.handler.ICommentHandler;
import tt.smart.agency.component.word.handler.IExcelDataHandler;
import tt.smart.agency.component.word.handler.IExcelDictHandler;
import tt.smart.agency.component.word.handler.II18nHandler;

import java.io.Serializable;

/**
 * <p>
 * 基础参数
 * </p>
 *
 * @author YangMC
 * @version V1.0
 **/
@Data
public class PoiBaseParams implements Serializable {

    /**
     * 数据处理接口
     */
    private IExcelDataHandler<?> dataHandler;

    /**
     * 字段处理类
     */
    private IExcelDictHandler dictHandler;

    /**
     * 国际化处理类
     */
    private II18nHandler i18nHandler;

    /**
     * 批注处理类
     */
    private ICommentHandler commentHandler;

}
