package tt.smart.agency.component.word.handler;

/**
 * <p>
 * 导入校验处理器
 * </p>
 *
 * @author YangMC
 * @version V1.0
 **/
public interface IExcelVerifyHandler<T> {

    /**
     * 导入校验方法
     *
     * @param obj T
     * @return 验证处理程序结果
     */
    VerifyHandlerResult verifyHandler(T obj);

}
