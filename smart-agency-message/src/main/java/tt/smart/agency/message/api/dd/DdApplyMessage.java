package tt.smart.agency.message.api.dd;

import tt.smart.agency.message.domain.dd.DdMessage;
import tt.smart.agency.message.domain.dd.DdMessageSendResult;

/**
 * <p>
 * 钉钉应用消息服务：
 * <a href="https://open.dingtalk.com/document/isvapp/send-job-notification">详情参考</a>
 * </p>
 *
 * @author MC_Yang
 * @version V1.0
 **/
public interface DdApplyMessage extends DdMessageBlend<DdMessageSendResult, DdMessage> {

    /**
     * 钉钉通用消息推送
     *
     * @param message 消息
     * @return 结果
     */
    DdMessageSendResult sendMessage(String message);

}