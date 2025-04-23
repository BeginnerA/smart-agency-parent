package tt.smart.agency.component.word.handler;

import java.util.List;

/**
 * <p>
 * 导出数据服务
 * </p>
 *
 * @author MC_Yang
 * @version V1.0
 **/
public interface IExcelExportServer {
    /**
     * 查询数据
     *
     * @param queryParams 查询条件
     * @param page        当前页数（从1开始）
     * @return 结果列表
     */
    List<Object> selectListForExcelExport(Object queryParams, int page);

}
