package tt.smart.agency.message.service;

import tt.smart.agency.message.domain.MessageSendBlend;

/**
 * <p>
 * 通用消息接口
 * </p>
 *
 * @param <R> 消息推送返回结果对象
 * @author YangMC
 * @version V1.0
 **/
public interface MessageBlend<R> {

    /**
     * 推送消息
     *
     * @param sendBlend 通用消息推送对象
     * @return 结果
     */
    R sendMessage(MessageSendBlend sendBlend);
}