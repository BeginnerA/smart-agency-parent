package tt.smart.agency.im.client;

import java.util.Set;

/**
 * <p>
 * 即时通讯客户端
 * </p>
 *
 * @author MC_Yang
 * @version V1.0
 **/
public interface ImClient extends ImTopicClient {

    /**
     * 发送消息
     *
     * @param text 消息文本
     */
    void sendMessage(String text);

    /**
     * 发送消息
     *
     * @param text         消息文本
     * @param sessionUsers 会话用户
     * @param toFilter     是过滤还是指定会话用户（<br>
     *                     true：过滤{@code sessionUsers}中指定的会话用户；<br>
     *                     false：过滤{@code sessionUsers}中指定以外的会话用户；）
     */
    void sendMessage(String text, Set<String> sessionUsers, boolean toFilter);

    /**
     * 向指定会话用户发送消息
     *
     * @param text         消息文本
     * @param sessionUsers 会话用户
     */
    default void sendMessage(String text, Set<String> sessionUsers) {
        sendMessage(text, sessionUsers, false);
    }

    /**
     * 向其他会话用户发送消息，过滤{@code filterSessionUsers}中指定的会话用户<br>
     *
     * @param text               消息文本
     * @param filterSessionUsers 需要过滤会话用户
     */
    default void sendTextToOther(String text, Set<String> filterSessionUsers) {
        sendMessage(text, filterSessionUsers, true);
    }

}
