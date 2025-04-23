package tt.smart.agency.message.api.dd;

import tt.smart.agency.message.domain.MessageSendResult;
import tt.smart.agency.message.domain.dd.DdBaseMessage;

/**
 * <p>
 * 通用钉钉消息服务
 * </p>
 *
 * @author MC_Yang
 * @version V1.0
 **/
public interface DdMessageBlend<R extends MessageSendResult, M extends DdBaseMessage> {

    /**
     * 钉钉通用消息推送
     *
     * @param message 消息
     * @return 结果
     */
    R sendMessage(M message);
}