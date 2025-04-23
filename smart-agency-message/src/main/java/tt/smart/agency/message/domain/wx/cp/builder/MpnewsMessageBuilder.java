package tt.smart.agency.message.domain.wx.cp.builder;

import tt.smart.agency.message.domain.wx.cp.WxCpMessage;
import tt.smart.agency.message.enums.wx.WxCpMessageType;

import java.util.List;

/**
 * <p>
 * 企业微信图文消息构建器：
 * <a href="https://developer.work.weixin.qq.com/document/path/90236">详情参考</a>
 * </p>
 *
 * @author MC_Yang
 * @version V1.0
 **/
public class MpnewsMessageBuilder extends BaseMessageBuilder<MpnewsMessageBuilder> {
    /**
     * 图文消息，一个图文消息支持1到8条图文
     */
    private List<MpnewsArticle> articles;

    public MpnewsMessageBuilder() {
        this.msgType = WxCpMessageType.MPNEWS;
    }

    public MpnewsMessageBuilder articles(List<MpnewsArticle> articles) {
        this.articles = articles;
        return this;
    }

    @Override
    public WxCpMessage build() {
        WxCpMessage msg = super.build();
        msg.setMpArticles(this.articles);
        return msg;
    }
}
