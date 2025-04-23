package com.reid.smart.agency.im.socket;

import org.jetbrains.annotations.NotNull;
import org.springframework.web.socket.*;
import org.springframework.web.socket.handler.AbstractWebSocketHandler;

/**
 * <p>
 *
 * </p>
 *
 * @author MC_Yang
 * @version V1.0
 **/
public class WebSocketImHandler extends AbstractWebSocketHandler {
    @Override
    public void afterConnectionEstablished(@NotNull WebSocketSession session) throws Exception {
        System.out.print("【客户端】afterConnectionEstablished=====================\n");
    }

    @Override
    public void handleTextMessage(@NotNull WebSocketSession session, @NotNull TextMessage message) throws Exception {
        System.out.print("【客户端】handleTextMessage=====================\n");
    }

    @Override
    public void handleBinaryMessage(@NotNull WebSocketSession session, @NotNull BinaryMessage message) throws Exception {
        System.out.print("【客户端】handleBinaryMessage=====================\n");
    }

    @Override
    public void handlePongMessage(@NotNull WebSocketSession session, @NotNull PongMessage message) throws Exception {
        System.out.print("【客户端】handlePongMessage=====================\n");
    }

    @Override
    public void handleTransportError(@NotNull WebSocketSession session, @NotNull Throwable exception) throws Exception {
        System.out.print("【客户端】handleTransportError=====================\n");
    }

    @Override
    public void afterConnectionClosed(@NotNull WebSocketSession session, @NotNull CloseStatus status) throws Exception {
        System.out.print("【客户端】afterConnectionClosed=====================\n");
    }
}
