package com.reid.smart.agency.im.socket;

import org.jetbrains.annotations.NotNull;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

/**
 * <p>
 * WebSocket 实现方式二
 * </p>
 *
 * @author MC_Yang
 * @version V1.0
 **/
@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig2 implements WebSocketMessageBrokerConfigurer {

    @Override
    public void registerStompEndpoints(@NotNull StompEndpointRegistry stompEndpointRegistry) {
        stompEndpointRegistry.addEndpoint("/websocket").setAllowedOriginPatterns("*");
    }

    @Override
    public void configureClientInboundChannel(@NotNull ChannelRegistration registration) {

    }

    @Override
    public void configureClientOutboundChannel(@NotNull ChannelRegistration registration) {
    }
}
