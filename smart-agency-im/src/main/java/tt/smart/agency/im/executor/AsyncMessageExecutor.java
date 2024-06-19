package tt.smart.agency.im.executor;

/**
 * <p>
 * 异步消息执行
 * </p>
 *
 * @author YangMC
 * @version V1.0
 **/
public interface AsyncMessageExecutor {

    /**
     * 向标识会话发送消息
     *
     * @param text 消息文本
     */
    void executeMessage(String text);
}
