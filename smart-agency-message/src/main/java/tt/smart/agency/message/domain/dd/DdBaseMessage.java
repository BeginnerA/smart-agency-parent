package tt.smart.agency.message.domain.dd;

import tt.smart.agency.message.enums.dd.DdMessageType;
import lombok.Data;

import java.io.Serializable;

/**
 * <p>
 * 钉钉消息基础类：
 * <a href="https://open.dingtalk.com/document/isvapp/send-job-notification">详情参考</a>
 * </p>
 *
 * @author YangMC
 * @version V1.0
 **/
@Data
public class DdBaseMessage implements Serializable {

    /**
     * 消息类型
     * 文本消息: text
     * 图片消息: image
     * 语音消息: voice
     * 文件消息: file
     * 链接消息：link
     * OA消息：OA消息
     * markdown消息: markdown
     * 卡片消息: action_card
     */
    protected DdMessageType msgType;

    /**
     * 推送消息时使用的微应用的 AgentID。
     * 企业内部应用可在开发者后台的应用详情页面查看。
     * 第三方企业应用可调用获取企业授权信息接口获取。
     */
    protected String agentId;

    /**
     * 接收者的 userId 列表，最大用户列表长度100（多个参数请用","分隔）
     */
    protected String useridList;

    /**
     * 接收者的部门 ID 列表，最大列表长度20。接收者是部门ID时，包括子部门下的所有用户（多个参数请用","分隔）
     */
    protected String deptIdList;

    /**
     * 是否推送给企业全部用户
     */
    protected Boolean toAllUser = false;

}
