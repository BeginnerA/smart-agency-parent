package tt.smart.agency.message.component.listener;

import tt.smart.agency.message.component.domain.Message;
import tt.smart.agency.message.domain.MessageSendResult;

/**
 * <p>
 * 自定义消息推送策略监听器。<br>
 * 在内置消息推送策略不满足时，可以继承该类对消息推送策略自定义实现。
 * </p>
 *
 * @author YangMC
 * @version V1.0
 **/
public interface CustomPushStrategyListener {

    /**
     * 推送消息
     *
     * @param message 消息
     * @return 结果
     */
    MessageSendResult send(Message message);

}
