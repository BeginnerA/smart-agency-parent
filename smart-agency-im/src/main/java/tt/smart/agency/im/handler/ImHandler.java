package tt.smart.agency.im.handler;

import tt.smart.agency.im.domain.ImMessage;
import tt.smart.agency.im.session.ImSession;

/**
 * <p>
 * 即时通讯处理器
 * </p>
 *
 * @author YangMC
 * @version V1.0
 **/
public interface ImHandler {

    /**
     * 连接建立后
     *
     * @param session 会话
     */
    void afterConnectionEstablished(ImSession session);

    /**
     * 处理消息
     *
     * @param session 会话
     * @param message 消息
     */
    void handleMessage(ImSession session, ImMessage message);

    /**
     * 处理传输错误
     *
     * @param session 会话
     */
    void handleTransportError(ImSession session);

    /**
     * 连接关闭后
     *
     * @param session 会话
     */
    void afterConnectionClosed(ImSession session);

}
