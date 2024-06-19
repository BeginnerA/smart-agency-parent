//package com.reid.smart.agency.im.socket;
//
//import jakarta.websocket.Endpoint;
//import jakarta.websocket.server.ServerApplicationConfig;
//import jakarta.websocket.server.ServerEndpointConfig;
//
//import java.util.HashSet;
//import java.util.Set;
//
///**
// * <p>
// * WebSocket 实现方式四
// * </p>
// *
// * @author YangMC
// * @version V1.0
// **/
//public class WebSocketConfig4 implements ServerApplicationConfig {
//
//    @Override
//    public Set<ServerEndpointConfig> getEndpointConfigs(Set<Class<? extends Endpoint>> set) {
//        Set<ServerEndpointConfig> result = new HashSet<>();
//        if (set.contains(EchoChannel2.class)) {
//            result.add(ServerEndpointConfig.Builder.create(EchoChannel2.class, "/websocket").build());
//        }
//        return result;
//    }
//
//    @Override
//    public Set<Class<?>> getAnnotatedEndpointClasses(Set<Class<?>> set) {
//        return set;
//    }
//}
