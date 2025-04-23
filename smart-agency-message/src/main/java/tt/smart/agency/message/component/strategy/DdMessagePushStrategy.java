package tt.smart.agency.message.component.strategy;

import tt.smart.agency.message.api.dd.DdApplyMessage;
import tt.smart.agency.message.component.domain.Message;
import tt.smart.agency.message.domain.MessageSendResult;
import tt.smart.agency.message.enums.PlatformType;
import tt.smart.agency.message.factory.dd.DdMessageFactory;

/**
 * <p>
 * 钉钉消息推送策略
 * </p>
 *
 * @author MC_Yang
 * @version V1.0
 **/
public class DdMessagePushStrategy extends AbstractMessagePushStrategy {
    @Override
    public MessageSendResult sendMessage(PlatformType platform, Message message) {
        DdApplyMessage ddApplyMessage = (DdApplyMessage) DdMessageFactory.createDdMessageBlend(platform);
        return ddApplyMessage.sendMessage(message.getContent());
    }
}
