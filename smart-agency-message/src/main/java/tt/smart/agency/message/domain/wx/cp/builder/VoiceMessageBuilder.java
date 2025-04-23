package tt.smart.agency.message.domain.wx.cp.builder;

import tt.smart.agency.message.domain.wx.cp.WxCpMessage;
import tt.smart.agency.message.enums.wx.WxCpMessageType;

/**
 * <p>
 * 企业微信语音消息构建器：
 * <a href="https://developer.work.weixin.qq.com/document/path/90236">详情参考</a>
 * </p>
 *
 * @author MC_Yang
 * @version V1.0
 **/
public class VoiceMessageBuilder extends BaseMessageBuilder<VoiceMessageBuilder> {

    /**
     * 语音文件 ID，可以调用上传临时素材接口获取
     */
    private String mediaId;

    public VoiceMessageBuilder() {
        this.msgType = WxCpMessageType.VOICE;
    }

    public VoiceMessageBuilder mediaId(String mediaId) {
        this.mediaId = mediaId;
        return this;
    }

    @Override
    public WxCpMessage build() {
        WxCpMessage msg = super.build();
        msg.setMediaId(this.mediaId);
        return msg;
    }
}
