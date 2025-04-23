package tt.smart.agency.message.service.dd;

import com.alibaba.fastjson2.JSONObject;
import tt.smart.agency.message.api.dd.DdApplyMessage;
import tt.smart.agency.message.config.properties.dd.DdProperties;
import tt.smart.agency.message.domain.MessageSendBlend;
import tt.smart.agency.message.domain.dd.DdMessage;
import tt.smart.agency.message.domain.dd.DdMessageSendResult;
import tt.smart.agency.message.enums.dd.DdApiUrl;

/**
 * <p>
 * 钉钉消息推送服务实现
 * </p>
 *
 * @author MC_Yang
 * @version V1.0
 **/
public class DdApplyMessageImpl extends AbstractDdMessage<DdMessageSendResult> implements DdApplyMessage {

    public DdApplyMessageImpl(DdProperties ddMessageConfig) {
        super.ddMessageConfig = ddMessageConfig;
    }

    @Override
    public DdMessageSendResult sendMessage(DdMessage message) {
        String url = DdApiUrl.Message.MESSAGE_TEMPLATE_SEND.getUrl();
        String accessToken = super.getAccessToken(message.getAgentId() == null ? super.getConfig().getAgentId() : message.getAgentId());
        return super.sendMessage(MessageSendBlend.builder().url(url).accessToken(accessToken).messageContent(message.toJson()).build());
    }

    @Override
    public DdMessageSendResult sendMessage(String message) {
        String url = DdApiUrl.Message.MESSAGE_TEMPLATE_SEND.getUrl();
        String accessToken = super.getAccessToken(String.valueOf(super.getConfig().getAgentId()));
        return super.sendMessage(MessageSendBlend.builder().url(url).accessToken(accessToken).messageContent(message).build());
    }

    @Override
    protected DdMessageSendResult handleResponseResult(JSONObject resultBody) {
        return null;
    }

}
