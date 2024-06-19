//package com.reid.smart.agency.im.socket;
//
//import org.jetbrains.annotations.NotNull;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.web.socket.config.annotation.EnableWebSocket;
//import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
//import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
//
///**
// * <p>
// * WebSocket 实现方式一
// * </p>
// *
// * @author YangMC
// * @version V1.0
// **/
//@Configuration
//@EnableWebSocket
//public class WebSocketConfig1 implements WebSocketConfigurer {
//
//    @Override
//    public void registerWebSocketHandlers(@NotNull WebSocketHandlerRegistry registry) {
//        // 注册自定义 WebSocket 处理器到指定路径上
//        registry.addHandler(new WebSocketImHandler(), "/websocket").setAllowedOriginPatterns("*");
//    }
//}
