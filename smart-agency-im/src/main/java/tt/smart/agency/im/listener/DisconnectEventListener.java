package tt.smart.agency.im.listener;

import org.springframework.context.ApplicationListener;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;
import org.springframework.web.socket.messaging.SessionSubscribeEvent;

import java.util.Objects;

/**
 * <p>
 *
 * </p>
 *
 * @author YangMC
 * @version V1.0
 **/
public class DisconnectEventListener implements ApplicationListener<SessionDisconnectEvent> {

    @Override
    public void onApplicationEvent(SessionDisconnectEvent sessionDisconnectEvent) {
        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(sessionDisconnectEvent.getMessage());
        System.out.println("【DisconnectEventListener销毁事件监听器: SessionID】---->"+ Objects.requireNonNull(headerAccessor.
                getSessionAttributes()).get("sessionID"));
    }
}
