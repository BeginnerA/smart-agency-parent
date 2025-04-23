package tt.smart.agency.message.component.strategy;

import tt.smart.agency.message.component.domain.Message;
import tt.smart.agency.message.domain.MessageSendResult;
import tt.smart.agency.message.enums.PlatformType;

/**
 * <p>
 * 消息推送策略
 * </p>
 *
 * @author MC_Yang
 * @version V1.0
 **/
public interface MessagePushStrategy {

    /**
     * 推送消息
     *
     * @param platform 消息推送平台
     * @param message  消息
     * @return 消息推送结果
     */
    MessageSendResult sendMessage(PlatformType platform, Message message);

}