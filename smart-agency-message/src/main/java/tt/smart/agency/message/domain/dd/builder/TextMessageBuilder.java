package tt.smart.agency.message.domain.dd.builder;

import tt.smart.agency.message.domain.dd.DdMessage;
import tt.smart.agency.message.enums.dd.DdMessageType;

/**
 * <p>
 * 企业微信文本消息构建器：
 * <a href="https://developer.work.weixin.qq.com/document/path/90236">详情参考</a>
 * </p>
 *
 * @author MC_Yang
 * @version V1.0
 **/
public final class TextMessageBuilder extends BaseMessageBuilder<TextMessageBuilder> {

    /**
     * 消息内容，最长不超过2048个字节，超过将截断（支持 ID 转译）
     */
    private String content;

    public TextMessageBuilder() {
        this.msgType = DdMessageType.TEXT;
    }

    public TextMessageBuilder content(String content) {
        this.content = content;
        return this;
    }

    @Override
    public DdMessage build() {
        DdMessage msg = super.build();
        msg.setContent(this.content);
        return msg;
    }
}
