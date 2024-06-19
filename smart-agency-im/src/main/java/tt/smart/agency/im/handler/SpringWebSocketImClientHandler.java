package tt.smart.agency.im.handler;

import org.jetbrains.annotations.NotNull;
import org.springframework.web.socket.*;
import org.springframework.web.socket.handler.AbstractWebSocketHandler;

/**
 * <p>
 * Spring WebSocket 即时通讯客户端处理器
 * </p>
 *
 * @author YangMC
 * @version V1.0
 **/
public class SpringWebSocketImClientHandler extends AbstractWebSocketHandler {

    @Override
    public void afterConnectionEstablished(@NotNull WebSocketSession session) throws Exception {
        System.out.print("afterConnectionEstablished=====================\n");
        super.afterConnectionEstablished(session);
    }

    @Override
    public void handleTextMessage(@NotNull WebSocketSession session, @NotNull TextMessage message) throws Exception {
        System.out.print("handleTextMessage=====================\n");
        super.handleTextMessage(session, message);
    }

    @Override
    public void handleBinaryMessage(@NotNull WebSocketSession session, @NotNull BinaryMessage message) throws Exception {
        System.out.print("handleBinaryMessage=====================\n");
        super.handleBinaryMessage(session, message);
    }

    @Override
    public void handlePongMessage(@NotNull WebSocketSession session, @NotNull PongMessage message) throws Exception {
        System.out.print("handlePongMessage=====================\n");
        super.handlePongMessage(session, message);
    }

    @Override
    public void handleTransportError(@NotNull WebSocketSession session, @NotNull Throwable exception) throws Exception {
        System.out.print("handleTransportError=====================\n");
        super.handleTransportError(session, exception);
    }

    @Override
    public void afterConnectionClosed(@NotNull WebSocketSession session, @NotNull CloseStatus status) throws Exception {
        System.out.print("afterConnectionClosed=====================\n");
        super.afterConnectionClosed(session, status);
    }

}
