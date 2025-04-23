package tt.smart.agency.message.enums.wx;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * <p>
 * 企业微信消息类型
 * </p>
 *
 * @author MC_Yang
 * @version V1.0
 **/
@Getter
@AllArgsConstructor
public enum WxCpMessageType {

    /**
     * 文本消息
     */
    TEXT("text"),
    /**
     * 图片消息
     */
    IMAGE("image"),
    /**
     * 语音消息
     */
    VOICE("voice"),
    /**
     * 视频消息
     */
    VIDEO("video"),
    /**
     * 音乐消息
     */
    MUSIC("music"),
    /**
     * 图文消息（点击跳转到外链）
     */
    NEWS("news"),
    /**
     * 图文消息（点击跳转到图文消息页面）
     */
    MPNEWS("mpnews"),
    /**
     * markdown 消息。
     * （目前仅支持 markdown 语法的子集，微工作台（原企业号）不支持展示 markdown 消息）
     */
    MARKDOWN("markdown"),
    /**
     * 推送文件（PC专用）
     */
    FILE("file"),
    /**
     * 文本卡片消息（PC专用）
     */
    TEXTCARD("textcard"),
    /**
     * 卡券消息
     */
    WXCARD("wxcard"),
    /**
     * 转发到客服的消息
     */
    TRANSFER_CUSTOMER_SERVICE("transfer_customer_service"),
    /**
     * 小程序卡片(要求小程序与公众号已关联)
     */
    MINIPROGRAMPAGE("miniprogrampage"),
    /**
     * 任务卡片消息
     */
    TASKCARD("taskcard"),
    /**
     * 菜单消息
     */
    MSGMENU("msgmenu"),
    /**
     * 小程序通知消息
     */
    MINIPROGRAM_NOTICE("miniprogram_notice"),
    /**
     * 模板卡片消息
     */
    TEMPLATE_CARD("template_card"),
    /**
     * 推送图文消息（点击跳转到图文消息页面）使用通过 “发布” 系列接口得到的 article_id (草稿箱功能上线后不再支持客服接口中带 media_id 的 mpnews 类型的图文消息)
     */
    MP_NEWS_ARTICLE("mpnewsarticle");

    /**
     * 类型
     */
    private final String type;

}
