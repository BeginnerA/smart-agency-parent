package tt.smart.agency.message.component.strategy;

import cn.hutool.extra.spring.SpringUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import tt.smart.agency.message.component.domain.Message;
import tt.smart.agency.message.component.listener.CustomPushStrategyListener;
import tt.smart.agency.message.domain.MessageSendResult;
import tt.smart.agency.message.enums.PlatformType;
import tt.smart.agency.message.exception.MessageException;

/**
 * <p>
 * 自定义消息推送策略监听器。<br>
 * 在内置消息推送策略不满足时，可以继承该类对消息推送策略自定义实现。
 * </p>
 *
 * @author YangMC
 * @version V1.0
 **/
@Slf4j
public class CustomPushStrategy extends AbstractMessagePushStrategy {

    @Override
    public MessageSendResult sendMessage(PlatformType platform, Message message) {
        try {
            CustomPushStrategyListener pushStrategyListener = SpringUtil.getBean(CustomPushStrategyListener.class);
            return pushStrategyListener.send(message);
        } catch (BeansException e) {
            throw new MessageException("在 spring 中未找到任何实现[CustomPushStrategyListener]接口的 bean", e);
        }
    }
}
