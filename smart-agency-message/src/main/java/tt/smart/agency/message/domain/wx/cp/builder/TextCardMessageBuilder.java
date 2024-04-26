package tt.smart.agency.message.domain.wx.cp.builder;

import tt.smart.agency.message.domain.wx.cp.WxCpMessage;
import tt.smart.agency.message.enums.wx.WxCpMessageType;

/**
 * <p>
 * 企业微信文本卡片消息构建器：
 * <a href="https://developer.work.weixin.qq.com/document/path/90236">详情参考</a>
 * </p>
 *
 * @author YangMC
 * @version V1.0
 **/
public class TextCardMessageBuilder extends BaseMessageBuilder<TextCardMessageBuilder> {

    /**
     * 标题，不超过128个字节，超过会自动截断（支持 ID 转译）
     */
    private String title;

    /**
     * 描述，不超过512个字节，超过会自动截断（支持 ID 转译）
     */
    private String description;

    /**
     * 点击后跳转的链接。最长2048字节，请确保包含了协议头(http/https)
     */
    private String url;

    /**
     * 按钮文字。 默认为“详情”， 不超过4个文字，超过自动截断
     */
    private String btnTxt;

    public TextCardMessageBuilder() {
        this.msgType = WxCpMessageType.TEXTCARD;
    }

    public TextCardMessageBuilder title(String title) {
        this.title = title;
        return this;
    }

    public TextCardMessageBuilder description(String description) {
        this.description = description;
        return this;
    }

    public TextCardMessageBuilder url(String url) {
        this.url = url;
        return this;
    }

    public TextCardMessageBuilder btnTxt(String btnTxt) {
        this.btnTxt = btnTxt;
        return this;
    }

    @Override
    public WxCpMessage build() {
        WxCpMessage msg = super.build();
        msg.setTitle(this.title);
        msg.setDescription(this.description);
        msg.setUrl(this.url);
        msg.setBtnTxt(this.btnTxt);
        return msg;
    }
}
