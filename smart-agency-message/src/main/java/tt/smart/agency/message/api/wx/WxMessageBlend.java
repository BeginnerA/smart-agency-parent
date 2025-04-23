package tt.smart.agency.message.api.wx;

import tt.smart.agency.message.domain.MessageSendResult;
import tt.smart.agency.message.domain.wx.WxBaseMessage;

/**
 * <p>
 * 通用微信服务
 * </p>
 *
 * @author MC_Yang
 * @version V1.0
 **/
public interface WxMessageBlend<R extends MessageSendResult, M extends WxBaseMessage> {

    /**
     * 微信通用消息推送
     *
     * @param message 消息
     * @return 结果
     */
    R sendMessage(M message);

}