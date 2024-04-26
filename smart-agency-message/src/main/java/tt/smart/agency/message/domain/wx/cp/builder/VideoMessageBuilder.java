package tt.smart.agency.message.domain.wx.cp.builder;

import tt.smart.agency.message.domain.wx.cp.WxCpMessage;
import tt.smart.agency.message.enums.wx.WxCpMessageType;

/**
 * <p>
 * 企业微信视频消息构建器：
 * <a href="https://developer.work.weixin.qq.com/document/path/90236">详情参考</a>
 * </p>
 *
 * @author YangMC
 * @version V1.0
 **/
public class VideoMessageBuilder extends BaseMessageBuilder<VideoMessageBuilder> {

    /**
     * 图片媒体文件 ID，可以调用上传临时素材接口获取
     */
    private String mediaId;

    /**
     * 标题，不超过128个字节，超过会自动截断（支持 ID 转译）
     */
    private String title;

    /**
     * 描述，不超过512个字节，超过会自动截断（支持 ID 转译）
     */
    private String description;

    public VideoMessageBuilder() {
        this.msgType = WxCpMessageType.IMAGE;
    }

    public VideoMessageBuilder mediaId(String mediaId) {
        this.mediaId = mediaId;
        return this;
    }

    public VideoMessageBuilder title(String title) {
        this.title = title;
        return this;
    }

    public VideoMessageBuilder description(String description) {
        this.description = description;
        return this;
    }

    @Override
    public WxCpMessage build() {
        WxCpMessage msg = super.build();
        msg.setMediaId(this.mediaId);
        msg.setTitle(this.title);
        msg.setDescription(this.description);
        return msg;
    }
}
