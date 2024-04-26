package tt.smart.agency.message.domain.wx.cp.builder;

import tt.smart.agency.message.domain.wx.cp.WxCpBaseMessage;
import tt.smart.agency.message.domain.wx.cp.WxCpMessage;

/**
 * <p>
 * 企业微信消息基础构建器：
 * <a href="https://developer.work.weixin.qq.com/document/path/90236">详情参考</a>
 * </p>
 *
 * @author YangMC
 * @version V1.0
 **/
@SuppressWarnings("unchecked")
public class BaseMessageBuilder<T> extends WxCpBaseMessage {

    /**
     * 企业应用的 ID，整型。企业内部开发，可在应用的设置页面查看；第三方服务商，可通过接口<获取企业授权信息>获取该参数值
     *
     * @param agentId 企业应用的 ID
     * @return 构建器
     */
    public T agentId(String agentId) {
        this.agentId = agentId;
        return (T) this;
    }

    /**
     * 指定接收消息的成员，成员 ID 列表（多个接收者用‘|’分隔，最多支持1000个）。 特殊情况：指定为"@all"，则向该企业应用的全部成员推送
     *
     * @param toUser 指定接收消息的成员
     * @return 构建器
     */
    public T toUser(String toUser) {
        this.toUser = toUser;
        return (T) this;
    }

    /**
     * 指定接收消息的部门，部门 ID 列表，多个接收者用‘|’分隔，最多支持100个。 当 toUser 为"@all"时忽略本参数
     *
     * @param toParty 指定接收消息的部门
     * @return 构建器
     */
    public T toParty(String toParty) {
        this.toParty = toParty;
        return (T) this;
    }

    /**
     * 指定接收消息的标签，标签 ID 列表，多个接收者用‘|’分隔，最多支持100个。 当 toUser 为"@all"时忽略本参数
     *
     * @param toTag 指定接收消息的标签
     * @return 构建器
     */
    public T toTag(String toTag) {
        this.toTag = toTag;
        return (T) this;
    }

    /**
     * 表示是否是保密消息，0表示可对外分享，1表示不能分享且内容显示水印，默认为0
     *
     * @param safe 是否是保密消息
     * @return 构建器
     */
    public T safe(Boolean safe) {
        this.safe = safe;
        return (T) this;
    }

    /**
     * 表示是否开启重复消息检查，0表示否，1表示是，默认0
     *
     * @param enableDuplicateCheck 开启重复消息检查
     * @return 构建器
     */
    public T enableDuplicateCheck(Boolean enableDuplicateCheck) {
        this.enableDuplicateCheck = enableDuplicateCheck;
        return (T) this;
    }

    /**
     * 表示是否重复消息检查的时间间隔，默认1800s，最大不超过4小时
     *
     * @param duplicateCheckInterval 重复消息检查的时间间隔
     * @return 构建器
     */
    public T duplicateCheckInterval(int duplicateCheckInterval) {
        this.duplicateCheckInterval = duplicateCheckInterval;
        return (T) this;
    }

    public WxCpMessage build() {
        WxCpMessage m = new WxCpMessage();
        m.setAgentId(this.agentId);
        m.setMsgType(this.msgType);
        m.setToUser(this.toUser);
        m.setToParty(this.toParty);
        m.setToTag(this.toTag);
        m.setSafe(this.safe);
        m.setEnableDuplicateCheck(this.enableDuplicateCheck);
        m.setDuplicateCheckInterval(this.duplicateCheckInterval);
        return m;
    }
}
