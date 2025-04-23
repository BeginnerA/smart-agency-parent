package tt.smart.agency.message.service.wx;

import com.alibaba.fastjson2.JSONObject;
import tt.smart.agency.message.api.wx.WxMpMessage;
import tt.smart.agency.message.config.properties.wx.WxMpProperties;
import tt.smart.agency.message.domain.MessageSendBlend;
import tt.smart.agency.message.domain.wx.mp.WxMpBaseMessage;
import tt.smart.agency.message.domain.wx.mp.WxMpMessageSendResult;
import tt.smart.agency.message.domain.wx.mp.template.WxMpSubscribeMessage;
import tt.smart.agency.message.domain.wx.mp.template.WxMpTemplateMessage;
import tt.smart.agency.message.enums.wx.WxApiUrl;

/**
 * <p>
 * 微信公众号消息服务
 * </p>
 *
 * @author MC_Yang
 * @version V1.0
 **/
public class WxMpMessageImpl extends AbstractWxMpMessage<WxMpMessageSendResult> implements WxMpMessage {

    public WxMpMessageImpl(WxMpProperties wxConfig) {
        super.wxConfig = wxConfig;
    }

    @Override
    public WxMpMessageSendResult sendMessage(WxMpBaseMessage message) {
        WxMpMessageSendResult result = null;
        if (message instanceof WxMpTemplateMessage) {
            result = sendTemplateMessage((WxMpTemplateMessage) message);
        } else if (message instanceof WxMpSubscribeMessage) {
            result = sendSubscribeMessage((WxMpSubscribeMessage) message);
        }
        return result;
    }

    @Override
    public WxMpMessageSendResult sendTemplateMessage(WxMpTemplateMessage templateMessage) {
        String url = WxApiUrl.Message.MESSAGE_TEMPLATE_SEND.getUrl();
        String appId = templateMessage.getAppId() == null ? super.getConfig().getAppId() : templateMessage.getAppId();
        return send(url, appId, templateMessage.toJson());
    }

    @Override
    public WxMpMessageSendResult sendSubscribeMessage(WxMpSubscribeMessage subscribeMessage) {
        String url = WxApiUrl.Message.MESSAGE_TEMPLATE_SUBSCRIBE_SEND.getUrl();
        String appId = subscribeMessage.getAppId() == null ? super.getConfig().getAppId() : subscribeMessage.getAppId();
        return send(url, appId, subscribeMessage.toJson());
    }

    @Override
    public WxMpMessageSendResult sendTemplateMessage(String message) {
        String url = WxApiUrl.Message.MESSAGE_TEMPLATE_SEND.getUrl();
        return send(url, super.getConfig().getAppId(), message);
    }

    @Override
    public WxMpMessageSendResult sendSubscribeMessage(String message) {
        String url = WxApiUrl.Message.MESSAGE_TEMPLATE_SUBSCRIBE_SEND.getUrl();
        return send(url, super.getConfig().getAppId(), message);
    }

    /**
     * 推送消息
     *
     * @param url     推送地址
     * @param appId   公众号 appId
     * @param message 消息
     * @return 结果
     */
    private WxMpMessageSendResult send(String url, String appId, String message) {
        String accessToken = super.getAccessToken(appId);
        return super.sendMessage(MessageSendBlend.builder().url(url).accessToken(accessToken).messageContent(message).build());
    }

    @Override
    protected WxMpMessageSendResult handleResponseResult(JSONObject resultBody) {
        return null;
    }
}