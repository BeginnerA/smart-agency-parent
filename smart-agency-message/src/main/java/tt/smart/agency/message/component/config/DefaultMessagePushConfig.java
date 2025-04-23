package tt.smart.agency.message.component.config;

import tt.smart.agency.message.component.domain.MessageTemplateConfig;

import java.util.List;

/**
 * <p>
 * 默认的消息推送配置实现
 * </p>
 *
 * @author MC_Yang
 * @version V1.0
 **/
public class DefaultMessagePushConfig implements MessagePushConfig {
    @Override
    public List<MessageTemplateConfig> getMessageTemplateConfig(String notificationStrategyClassName) {
        return List.of();
    }
}