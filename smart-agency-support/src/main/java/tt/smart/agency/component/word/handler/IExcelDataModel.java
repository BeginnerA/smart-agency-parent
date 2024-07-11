package tt.smart.agency.component.word.handler;

/**
 * <p>
 * Excel 本身数据文件
 * </p>
 *
 * @author YangMC
 * @version V1.0
 **/
public interface IExcelDataModel {

    /**
     * 获取行号
     *
     * @return 行号
     */
    Integer getRowNum();

    /**
     * 设置行号
     *
     * @param rowNum 行号
     */
    void setRowNum(Integer rowNum);

}
