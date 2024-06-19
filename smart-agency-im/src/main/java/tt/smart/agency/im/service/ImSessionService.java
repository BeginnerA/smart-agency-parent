package tt.smart.agency.im.service;

import java.util.Set;

/**
 * <p>
 * 即时通讯会话服务
 * </p>
 *
 * @author YangMC
 * @version V1.0
 **/
public interface ImSessionService {

    /**
     * 向标识会话发送消息
     *
     * @param text 消息文本
     */
    void sendMessage(String text);

    /**
     * 向标识会话发送消息
     *
     * @param text 消息文本
     * @param tags 会话标识
     */
    void sendMessage(String text, Set<String> tags);

    /**
     * 向其他会话发送消息，过滤{@code filterTag}中标识的会话。<br>
     * 如果{@code filterTag}为空则向全部会话发送消息
     *
     * @param text       消息文本
     * @param filterTags 需要过滤会话标识
     */
    void sendTextToOther(String text, Set<String> filterTags);

}
