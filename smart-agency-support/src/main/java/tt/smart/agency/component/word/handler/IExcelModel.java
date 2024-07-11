package tt.smart.agency.component.word.handler;

/**
 * <p>
 * Excel 标记类
 * </p>
 *
 * @author YangMC
 * @version V1.0
 **/
public interface IExcelModel {

    /**
     * 获取错误数据
     *
     * @return 错误消息
     */
    String getErrorMsg();

    /**
     * 设置错误信息
     *
     * @param errorMsg 错误消息
     */
    void setErrorMsg(String errorMsg);

}
