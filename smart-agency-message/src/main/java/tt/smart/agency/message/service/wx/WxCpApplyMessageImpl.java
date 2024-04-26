package tt.smart.agency.message.service.wx;

import com.alibaba.fastjson2.JSONObject;
import tt.smart.agency.message.api.wx.WxCpApplyMessage;
import tt.smart.agency.message.config.properties.wx.WxCpProperties;
import tt.smart.agency.message.domain.MessageSendBlend;
import tt.smart.agency.message.domain.wx.cp.WxCpMessage;
import tt.smart.agency.message.domain.wx.cp.WxCpMessageSendResult;
import tt.smart.agency.message.enums.wx.WxApiUrl;

/**
 * <p>
 * 企业微信消息服务
 * </p>
 *
 * @author YangMC
 * @version V1.0
 **/
public class WxCpApplyMessageImpl extends AbstractWxCpMessage<WxCpMessageSendResult> implements WxCpApplyMessage {

    public WxCpApplyMessageImpl(WxCpProperties wxCpConfig) {
        super.wxCpConfig = wxCpConfig;
    }

    @Override
    public WxCpMessageSendResult sendMessage(WxCpMessage message) {
        String url = WxApiUrl.Message.MESSAGE_CP_APPLY_SEND.getUrl();
        String accessToken = super.getAccessToken(message.getAgentId() == null ? super.getConfig().getAgentId() : message.getAgentId());
        return super.sendMessage(MessageSendBlend.builder().url(url).accessToken(accessToken).messageContent(message.toJson()).build());
    }

    @Override
    public WxCpMessageSendResult sendMessage(String message) {
        String url = WxApiUrl.Message.MESSAGE_CP_APPLY_SEND.getUrl();
        String accessToken = super.getAccessToken(super.getConfig().getAgentId());
        return super.sendMessage(MessageSendBlend.builder().url(url).accessToken(accessToken).messageContent(message).build());
    }

    @Override
    protected WxCpMessageSendResult handleResponseResult(JSONObject resultBody) {
        return null;
    }

}