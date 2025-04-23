//package com.reid.smart.agency.im.socket;
//
//import jakarta.websocket.*;
//
///**
// * <p>
// * WebSocket 实现方式三
// * </p>
// *
// * @author MC_Yang
// * @version V1.0
// **/
//public class EchoChannel2 extends Endpoint {
//    private Session session;
//
//    private static class ChatMessageHandler implements MessageHandler.Partial<String> {
//        private Session session;
//
//        private ChatMessageHandler(Session session) {
//            this.session = session;
//        }
//
//        @Override
//        public void onMessage(String message, boolean last) {
//            String msg = String.format("%s %s %s", session.getId(), "said:", message);
//        }
//    }
//
//    ;
//
//    @Override
//    public void onOpen(Session session, EndpointConfig config) {
//        this.session = session;
//        this.session.addMessageHandler(new ChatMessageHandler(session));
//        String message = String.format("%s %s", session.getId(), "has joined.");
//    }
//
//    @Override
//    public void onClose(Session session, CloseReason closeReason) {
//        String message = String.format("%s %s", session.getId(), "has disconnected.");
//    }
//
//    @Override
//    public void onError(Session session, Throwable throwable) {
//    }
//
//}
