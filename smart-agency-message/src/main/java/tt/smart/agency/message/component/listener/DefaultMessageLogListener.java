package tt.smart.agency.message.component.listener;

import tt.smart.agency.message.component.domain.Message;
import tt.smart.agency.message.component.strategy.MessagePushPlatformEnum;
import tt.smart.agency.message.domain.MessageSendResult;

/**
 * <p>
 * 默认消息日志
 * </p>
 *
 * @author YangMC
 * @version V1.0
 **/
public class DefaultMessageLogListener implements MessageLogListener {
    @Override
    public void log(MessagePushPlatformEnum platform, Message message, MessageSendResult messageSendResult) {
        System.out.println("执行默认消息日志:\n-----推送平台->" + platform.getDescription() + "\n-----推送消息->" + message + "\n-----消息推送结果->" + messageSendResult + "\n");
    }
}