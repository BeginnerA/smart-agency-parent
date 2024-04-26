package tt.smart.agency.message.domain;

import lombok.Data;

import java.io.Serializable;

/**
 * <p>
 * 通用消息推送结果
 * </p>
 *
 * @author YangMC
 * @version V1.0
 **/
@Data
public class MessageSendResult implements Serializable {
    /**
     * 原始结果：指未经处理或加工的最初的结果
     */
    protected Object rawResult;
}