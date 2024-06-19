package tt.smart.agency.im.interceptor;

import org.springframework.lang.Nullable;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.web.socket.adapter.standard.StandardWebSocketHandlerAdapter;

/**
 * <p>
 *
 * </p>
 *
 * @author YangMC
 * @version V1.0
 **/
public class WebSocketChannelInterceptor implements ChannelInterceptor {
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        System.out.println("preSend");
        return message;
    }

    public void postSend(Message<?> message, MessageChannel channel, boolean sent) {
        System.out.println("postSend");
    }

    public void afterSendCompletion(Message<?> message, MessageChannel channel, boolean sent, @Nullable Exception ex) {
        System.out.println("afterSendCompletion");
    }

    public boolean preReceive(MessageChannel channel) {
        System.out.println("preReceive");
        return true;
    }

    public Message<?> postReceive(Message<?> message, MessageChannel channel) {
        System.out.println("postReceive");
        return message;
    }

    public void afterReceiveCompletion(@Nullable Message<?> message, MessageChannel channel, @Nullable Exception ex) {
        System.out.println("afterReceiveCompletion");
    }
}
