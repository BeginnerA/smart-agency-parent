package tt.smart.agency.im.listener;

import org.springframework.context.ApplicationListener;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.web.socket.messaging.SessionConnectedEvent;

/**
 * <p>
 *
 * </p>
 *
 * @author YangMC
 * @version V1.0
 **/
public class ConnectedEventListener implements ApplicationListener<SessionConnectedEvent> {
    @Override
    public void onApplicationEvent(SessionConnectedEvent sessionConnectedEvent) {
        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(sessionConnectedEvent.getMessage());
        System.out.println("【ConnectEventListener链接事件监听器】---->"+headerAccessor.getMessageType());
    }
}
