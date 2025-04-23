package tt.smart.agency.im.handler;

import org.jetbrains.annotations.NotNull;
import org.springframework.web.socket.*;
import org.springframework.web.socket.handler.AbstractWebSocketHandler;

/**
 * <p>
 * Spring WebSocket 即时通讯客户端处理器
 * </p>
 *
 * @author MC_Yang
 * @version V1.0
 **/
public class SpringWebSocketImClientHandler extends AbstractWebSocketHandler {

    @Override
    public void afterConnectionEstablished(@NotNull WebSocketSession session) throws Exception {
        System.out.println("afterConnectionEstablished=====================");
        super.afterConnectionEstablished(session);
    }

    @Override
    public void handleTextMessage(@NotNull WebSocketSession session, @NotNull TextMessage message) throws Exception {
        System.out.println("handleTextMessage=====================");
        super.handleTextMessage(session, message);
    }

    @Override
    public void handleBinaryMessage(@NotNull WebSocketSession session, @NotNull BinaryMessage message) throws Exception {
        System.out.println("handleBinaryMessage=====================");
        super.handleBinaryMessage(session, message);
    }

    @Override
    public void handlePongMessage(@NotNull WebSocketSession session, @NotNull PongMessage message) throws Exception {
        System.out.println("handlePongMessage=====================");
        super.handlePongMessage(session, message);
    }

    @Override
    public void handleTransportError(@NotNull WebSocketSession session, @NotNull Throwable exception) throws Exception {
        System.out.println("handleTransportError=====================");
        super.handleTransportError(session, exception);
    }

    @Override
    public void afterConnectionClosed(@NotNull WebSocketSession session, @NotNull CloseStatus status) throws Exception {
        System.out.println("afterConnectionClosed=====================");
        super.afterConnectionClosed(session, status);
    }

}
