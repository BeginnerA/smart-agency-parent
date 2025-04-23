package tt.smart.agency.message.domain.dd.builder;

import tt.smart.agency.message.domain.dd.DdMessage;
import tt.smart.agency.message.enums.dd.DdMessageType;

/**
 * <p>
 * 企业微信文本卡片消息构建器：
 * <a href="https://developer.work.weixin.qq.com/document/path/90236">详情参考</a>
 * </p>
 *
 * @author MC_Yang
 * @version V1.0
 **/
public class ActionCardMessageBuilder extends BaseMessageBuilder<ActionCardMessageBuilder> {

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

    public ActionCardMessageBuilder() {
        this.msgType = DdMessageType.ACTION_CARD;
    }

    public ActionCardMessageBuilder title(String title) {
        this.title = title;
        return this;
    }

    public ActionCardMessageBuilder description(String description) {
        this.description = description;
        return this;
    }

    public ActionCardMessageBuilder url(String url) {
        this.url = url;
        return this;
    }

    public ActionCardMessageBuilder btnTxt(String btnTxt) {
        this.btnTxt = btnTxt;
        return this;
    }

    @Override
    public DdMessage build() {
        DdMessage msg = super.build();
        return msg;
    }
}
