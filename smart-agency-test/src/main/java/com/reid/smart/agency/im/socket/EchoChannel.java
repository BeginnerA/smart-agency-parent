//package com.reid.smart.agency.im.socket;
//
//import java.io.IOException;
//import java.time.Instant;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//import jakarta.websocket.CloseReason;
//import jakarta.websocket.EndpointConfig;
//import jakarta.websocket.OnClose;
//import jakarta.websocket.OnError;
//import jakarta.websocket.OnMessage;
//import jakarta.websocket.OnOpen;
//import jakarta.websocket.Session;
//import jakarta.websocket.server.ServerEndpoint;
//import org.springframework.stereotype.Component;
//
///**
// * <p>
// * WebSocket 实现方式三
// * </p>
// *
// * @author MC_Yang
// * @version V1.0
// **/
//@Component
//@ServerEndpoint(value = "/websocket")
//public class EchoChannel {
//
//    private static final Logger LOGGER = LoggerFactory.getLogger(EchoChannel.class);
//
//    private Session session;
//
//    @OnMessage
//    public void onMessage(String message) throws IOException{
//        System.out.print("onMessage");
//    }
//
//    @OnOpen
//    public void onOpen(Session session, EndpointConfig endpointConfig){
//        System.out.print("onOpen");
//    }
//
//    @OnClose
//    public void onClose(CloseReason closeReason){
//        System.out.print("onClose");
//    }
//
//    @OnError
//    public void onError(Throwable throwable) throws IOException {
//        System.out.print("onError");
//    }
//
//}
