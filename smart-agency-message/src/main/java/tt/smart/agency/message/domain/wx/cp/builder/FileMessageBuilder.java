package tt.smart.agency.message.domain.wx.cp.builder;

import tt.smart.agency.message.domain.wx.cp.WxCpMessage;
import tt.smart.agency.message.enums.wx.WxCpMessageType;

/**
 * <p>
 * 企业微信文件消息构建器：
 * <a href="https://developer.work.weixin.qq.com/document/path/90236">详情参考</a>
 * </p>
 *
 * @author YangMC
 * @version V1.0
 **/
public class FileMessageBuilder extends BaseMessageBuilder<FileMessageBuilder> {

    /**
     * 文件 ID，可以调用上传临时素材接口获取
     */
    private String mediaId;

    public FileMessageBuilder() {
        this.msgType = WxCpMessageType.IMAGE;
    }

    public FileMessageBuilder mediaId(String mediaId) {
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
