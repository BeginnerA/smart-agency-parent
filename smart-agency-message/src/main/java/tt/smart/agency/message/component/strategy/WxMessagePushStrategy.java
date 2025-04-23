package tt.smart.agency.message.component.strategy;

import tt.smart.agency.message.api.wx.WxCpApplyMessage;
import tt.smart.agency.message.api.wx.WxMessageBlend;
import tt.smart.agency.message.api.wx.WxMpMessage;
import tt.smart.agency.message.component.domain.Message;
import tt.smart.agency.message.domain.MessageSendResult;
import tt.smart.agency.message.enums.PlatformType;
import tt.smart.agency.message.enums.wx.WxPlatformType;
import tt.smart.agency.message.factory.wx.WxMessageFactory;

/**
 * <p>
 * 微信消息推送策略
 * </p>
 *
 * @author MC_Yang
 * @version V1.0
 **/
public class WxMessagePushStrategy extends AbstractMessagePushStrategy {
    @Override
    public MessageSendResult sendMessage(PlatformType platform, Message message) {
        MessageSendResult result;
        WxMessageBlend<?, ?> wxMessageBlend = WxMessageFactory.createWxMessageBlend(platform);
        if (WxPlatformType.MP_TEMPLATE.equals(platform)) {
            WxMpMessage mpMessage = (WxMpMessage) wxMessageBlend;
            result = mpMessage.sendTemplateMessage(message.getContent());
        } else if (WxPlatformType.MP_SUBSCRIBE.equals(platform)) {
            WxMpMessage mpMessage = (WxMpMessage) wxMessageBlend;
            result = mpMessage.sendSubscribeMessage(message.getContent());
        } else {
            WxCpApplyMessage cpMessage = (WxCpApplyMessage) wxMessageBlend;
            result = cpMessage.sendMessage(message.getContent());
        }
        return result;
    }
}
