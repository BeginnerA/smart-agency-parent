package tt.smart.agency.message.domain.wx.cp;

import lombok.Data;
import lombok.EqualsAndHashCode;
import tt.smart.agency.message.domain.wx.WxBaseMessage;
import tt.smart.agency.message.enums.wx.WxCpMessageType;

/**
 * <p>
 * 企业微信消息基础类：
 * <a href="https://developer.work.weixin.qq.com/document/path/90236">详情参考</a>
 * </p>
 *
 * @author MC_Yang
 * @version V1.0
 **/
@Data
@EqualsAndHashCode(callSuper = true)
public class WxCpBaseMessage extends WxBaseMessage {

    /**
     * 消息类型
     * 文本消息: text
     * 图片消息: image
     * 语音消息: voice
     * 视频消息: video
     * 文件消息: file
     * 文本卡片消息: textcard
     * 图文消息: news
     * 图文消息: mpnews
     * markdown消息: markdown
     * 模板卡片消息: template_card
     */
    protected WxCpMessageType msgType;

    /**
     * 企业应用的 ID，整型。企业内部开发，可在应用的设置页面查看；第三方服务商，可通过接口<获取企业授权信息>获取该参数值
     */
    protected String agentId;

    /**
     * 指定接收消息的成员，成员 ID 列表（多个接收者用‘|’分隔，最多支持1000个）。<br>
     * 特殊情况：指定为"@all"，则向该企业应用的全部成员推送
     */
    protected String toUser;

    /**
     * 指定接收消息的部门，部门 ID 列表，多个接收者用‘|’分隔，最多支持100个。<br>
     * 当 toUser 为"@all"时忽略本参数
     */
    protected String toParty;

    /**
     * 指定接收消息的标签，标签 ID 列表，多个接收者用‘|’分隔，最多支持100个。<br>
     * 当 toUser 为"@all"时忽略本参数
     */
    protected String toTag;

    /**
     * 表示是否是保密消息，0表示可对外分享，1表示不能分享且内容显示水印，默认为0
     */
    protected Boolean safe = false;

    /**
     * 表示是否开启重复消息检查，0表示否，1表示是，默认0
     */
    protected Boolean enableDuplicateCheck = false;

    /**
     * 表示是否重复消息检查的时间间隔，默认1800s，最大不超过4小时
     */
    protected Integer duplicateCheckInterval = 1800;

}
