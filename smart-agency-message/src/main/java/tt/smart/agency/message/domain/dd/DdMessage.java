package tt.smart.agency.message.domain.dd;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson2.JSONObject;
import lombok.Data;
import lombok.EqualsAndHashCode;
import tt.smart.agency.message.domain.dd.builder.ActionCardMessageBuilder;
import tt.smart.agency.message.domain.dd.builder.TextMessageBuilder;

/**
 * <p>
 * 钉钉消息：
 * <a href="https://open.dingtalk.com/document/isvapp/send-job-notification">详情参考</a>
 * </p>
 *
 * @author MC_Yang
 * @version V1.0
 **/
@Data
@EqualsAndHashCode(callSuper = true)
public class DdMessage extends DdBaseMessage {

    // ======================================== 文本信息 ========================================
    /**
     * 消息内容，最长不超过2048个字节，超过将截断（支持 ID 转译）
     */
    private String content;

    // ======================================== 卡片信息 ========================================


    /**
     * 获取文本消息构建器
     *
     * @return 文本消息构建器
     */
    public static TextMessageBuilder textMsg() {
        return new TextMessageBuilder();
    }

    /**
     * 获取卡片消息构建器
     *
     * @return 卡片消息构建器
     */
    public static ActionCardMessageBuilder actionCard() {
        return new ActionCardMessageBuilder();
    }

    public String toJson() {
        JSONObject messageJson = new JSONObject();
        if (StrUtil.isNotBlank(this.getAgentId())) {
            messageJson.put("agent_id", this.getAgentId());
        }

        if (StrUtil.isNotBlank(this.getUseridList())) {
            messageJson.put("userid_list", this.getUseridList());
        }

        if (StrUtil.isNotBlank(this.getDeptIdList())) {
            messageJson.put("dept_id_list", this.getDeptIdList());
        }

        if (this.getToAllUser()) {
            messageJson.put("to_all_user", true);
        }

        this.handleMsgType(messageJson);

        return messageJson.toString();
    }

    /**
     * 按照类型组装消息
     *
     * @param messageJson 消息
     */
    private void handleMsgType(JSONObject messageJson) {
        JSONObject msg = new JSONObject();
        switch (this.getMsgType()) {
            case TEXT: {
                JSONObject text = new JSONObject();
                text.put("content", this.getContent());
                msg.put("text", text);
                break;
            }
            case ACTION_CARD: {
                JSONObject text = new JSONObject();
                text.put("btn_json_list", "");
                text.put("single_url", "");
                text.put("btn_orientation", "");
                text.put("single_title", "");
                text.put("markdown", "");
                text.put("title", "");
                messageJson.put("action_card", text);
                break;
            }
            case MARKDOWN: {
                JSONObject text = new JSONObject();
                text.put("text", "");
                text.put("title", "");
                messageJson.put("markdown", text);
                break;
            }
            case IMAGE: {
                JSONObject text = new JSONObject();
                text.put("media_id", "");
                messageJson.put("image", text);
                break;
            }
            case FILE: {
                JSONObject text = new JSONObject();
                text.put("media_id", "");
                messageJson.put("file", text);
                break;
            }
            case VOICE: {
                JSONObject text = new JSONObject();
                text.put("duration", "");
                text.put("media_id", "");
                messageJson.put("voice", text);
                break;
            }
            case LINK: {
                JSONObject text = new JSONObject();
                text.put("picUrl", "");
                text.put("messageUrl", "");
                text.put("text", "");
                text.put("title", "");
                messageJson.put("link", text);
                break;
            }
            case OA: {
                JSONObject text = new JSONObject();
                text.put("head", "");
                text.put("pc_message_url", "");
                text.put("status_bar", "");
                text.put("body", "");
                text.put("message_url", "");
                messageJson.put("oa", text);
                break;
            }
            default: {
            }
        }
        msg.put("msgtype", this.getMsgType().getType());
        messageJson.put("msg", msg);
    }
}
