package tt.smart.agency.message.domain.sms;

import lombok.Data;
import lombok.EqualsAndHashCode;
import tt.smart.agency.message.domain.MessageSendResult;

/**
 * <p>
 * 短信响应结果
 * </p>
 *
 * @author YangMC
 * @version V1.0
 **/
@Data
@EqualsAndHashCode(callSuper = true)
public class SmsResponseResult extends MessageSendResult {
    /**
     * 是否成功
     */
    private boolean success;

    /**
     * 状态码
     */
    private String code;

    /**
     * 返回消息
     */
    private String message;

}
