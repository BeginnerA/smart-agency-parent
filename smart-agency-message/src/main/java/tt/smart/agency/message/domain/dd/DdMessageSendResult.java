package tt.smart.agency.message.domain.dd;

import com.alibaba.fastjson2.annotation.JSONField;
import lombok.Data;
import lombok.EqualsAndHashCode;
import tt.smart.agency.message.domain.MessageSendResult;

/**
 * <p>
 * 钉钉消息推送结果
 * </p>
 *
 * @author YangMC
 * @version V1.0
 **/
@Data
@EqualsAndHashCode(callSuper = true)
public class DdMessageSendResult extends MessageSendResult {

    /**
     * 请求 ID
     */
    @JSONField(name = "request_id")
    private String requestId;

    /**
     * 返回码
     */
    @JSONField(name = "errcode")
    private Integer errCode;

    /**
     * 对返回码的文本描述内容
     */
    @JSONField(name = "errmsg")
    private String errMsg;

    /**
     * 创建的异步推送任务 ID
     */
    @JSONField(name = "task_id")
    private String taskId;

}
