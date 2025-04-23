package tt.smart.agency.message.domain.dd.builder;

import tt.smart.agency.message.domain.dd.DdBaseMessage;
import tt.smart.agency.message.domain.dd.DdMessage;

/**
 * <p>
 * 钉钉消息基础构建器：
 * <a href="https://developer.work.weixin.qq.com/document/path/90236">详情参考</a>
 * </p>
 *
 * @author MC_Yang
 * @version V1.0
 **/
@SuppressWarnings("unchecked")
public class BaseMessageBuilder<T> extends DdBaseMessage {

    /**
     * 推送消息时使用的微应用的 AgentID。<br>
     * 企业内部应用可在开发者后台的应用详情页面查看。<br>
     * 第三方企业应用可调用获取企业授权信息接口获取。<br>
     *
     * @param agentId 推送消息时使用的微应用的 AgentID
     * @return 消息构建器
     */
    public T agentId(String agentId) {
        this.agentId = agentId;
        return (T) this;
    }

    /**
     * 接收者的 userId 列表，最大用户列表长度100（多个参数请用","分隔）
     *
     * @param useridList 接收者的 userId 列表
     * @return 消息构建器
     */
    public T useridList(String useridList) {
        this.useridList = useridList;
        return (T) this;
    }

    /**
     * 接收者的部门 ID 列表，最大列表长度20。接收者是部门ID时，包括子部门下的所有用户（多个参数请用","分隔）
     *
     * @param deptIdList 接收者的部门 ID 列表
     * @return 消息构建器
     */
    public T deptIdList(String deptIdList) {
        this.deptIdList = deptIdList;
        return (T) this;
    }

    /**
     * 是否推送给企业全部用户
     *
     * @param toAllUser 是否推送给企业全部用户
     * @return 消息构建器
     */
    public T toAllUser(Boolean toAllUser) {
        this.toAllUser = toAllUser;
        return (T) this;
    }

    public DdMessage build() {
        DdMessage m = new DdMessage();
        m.setAgentId(this.agentId);
        m.setMsgType(this.msgType);
        m.setUseridList(this.useridList);
        m.setDeptIdList(this.deptIdList);
        m.setToAllUser(this.toAllUser);
        return m;
    }
}
