package tt.smart.agency.message.component.listener;

import tt.smart.agency.message.component.domain.Message;
import tt.smart.agency.message.component.strategy.MessagePushPlatformEnum;
import tt.smart.agency.message.domain.MessageSendResult;

/**
 * <p>
 * 消息日志接口
 * </p>
 *
 * @author YangMC
 * @version V1.0
 **/
public interface MessageLogListener {

    /**
     * 消息日志。<br>
     * 在消息推送后会触发该方法
     *
     * @param platform          消息推送平台
     * @param message           推送消息
     * @param messageSendResult 消息推送结果
     */
    void log(MessagePushPlatformEnum platform, Message message, MessageSendResult messageSendResult);
}