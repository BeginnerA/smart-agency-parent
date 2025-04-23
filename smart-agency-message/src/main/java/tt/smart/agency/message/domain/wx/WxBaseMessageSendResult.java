package tt.smart.agency.message.domain.wx;

import com.alibaba.fastjson2.annotation.JSONField;
import lombok.Data;
import lombok.EqualsAndHashCode;
import tt.smart.agency.message.domain.MessageSendResult;

/**
 * <p>
 * 微信基本消息推送结果
 * </p>
 *
 * @author MC_Yang
 * @version V1.0
 **/
@Data
@EqualsAndHashCode(callSuper = true)
public class WxBaseMessageSendResult extends MessageSendResult {

    /**
     * 信息 ID
     */
    @JSONField(name = "msgid")
    protected String msgId;

    /**
     * 返回码
     */
    @JSONField(name = "errcode")
    protected Integer errCode;

    /**
     * 对返回码的文本描述内容
     */
    @JSONField(name = "errmsg")
    protected String errMsg;

}