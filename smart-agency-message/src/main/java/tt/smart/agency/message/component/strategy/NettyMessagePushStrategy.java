package tt.smart.agency.message.component.strategy;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tt.smart.agency.message.component.domain.Message;
import tt.smart.agency.message.domain.MessageSendResult;
import tt.smart.agency.message.enums.PlatformType;

/**
 * <p>
 * Netty 网络消息推送策略
 * </p>
 *
 * @author YangMC
 * @version V1.0
 **/
@Service
@RequiredArgsConstructor
public class NettyMessagePushStrategy extends AbstractMessagePushStrategy {

    @Override
    public MessageSendResult sendMessage(PlatformType platform, Message message) {
        System.out.print("Netty 待实现");
        return null;
    }
}