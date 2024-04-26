package tt.smart.agency.message.component.strategy;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tt.smart.agency.message.component.domain.Message;
import tt.smart.agency.message.domain.MessageSendResult;
import tt.smart.agency.message.enums.PlatformType;
import tt.smart.agency.message.factory.sms.SmsMessageFactory;

import java.util.Set;

/**
 * <p>
 * 短信消息推送策略
 * </p>
 *
 * @author YangMC
 * @version V1.0
 **/
@Service
@RequiredArgsConstructor
public class SmsMessagePushStrategy extends AbstractMessagePushStrategy {

    @Override
    public MessageSendResult sendMessage(PlatformType platform, Message message) {
        // TODO 短信推送策略暂不支持消息模板参数
        MessageSendResult result = null;
        Set<String> addresseeSet = message.getAddresseeSet();
        for (String phone : addresseeSet) {
            result = SmsMessageFactory.createSmsBlend(platform).sendMessage(phone, message.getContent());
        }
        return result;
    }
}