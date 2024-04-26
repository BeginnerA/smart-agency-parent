package tt.smart.agency.message.component.config;

import tt.smart.agency.message.component.domain.MessageTemplateConfig;

import java.util.List;

/**
 * <p>
 * 消息推送配置接口
 * </p>
 *
 * @author YangMC
 * @version V1.0
 **/
public interface MessagePushConfig {

    /**
     * 获取消息模板配置消息列表
     *
     * @param notificationStrategyClassName 通知策略类名称，可以通过推送策略类名称判断需要推送的消息
     * @return 消息模板配置消息列表
     */
    List<MessageTemplateConfig> getMessageTemplateConfig(String notificationStrategyClassName);

}