package tt.smart.agency.im.domain;

import java.util.HashSet;
import java.util.Set;

/**
 * <p>
 * 即时通讯主题
 * </p>
 *
 * @author YangMC
 * @version V1.0
 **/
public class ImTopic {

    /**
     * 主题标识。<br>
     * 注意：主题是唯一的
     */
    private final String topic;
    /**
     * 主题持有人
     */
    private final String holder;
    /**
     * 当前在线订阅会话列表
     */
    private final Set<String> sessionsTags;
    /**
     * 时间戳
     */
    private final long timestamp = System.currentTimeMillis();

    /**
     * 主题构造函数
     *
     * @param topic  主题
     * @param holder 持有者（会话）
     */
    public ImTopic(String topic, String holder) {
        this.topic = topic;
        this.holder = holder;
        this.sessionsTags = new HashSet<>();
        this.subscribe(holder);
    }

    /**
     * 得到主题唯一标签
     *
     * @return 主题唯一标签
     */
    public String getTopicTag() {
        return topic;
    }

    /**
     * 订阅
     *
     * @param sessions 会话
     */
    public void subscribe(String sessions) {
        this.sessionsTags.add(sessions);
    }

    /**
     * 取消订阅
     *
     * @param sessions 会话标识
     */
    public void unsubscribe(String sessions) {
        this.sessionsTags.remove(sessions);
    }

    /**
     * 获取当前订阅会话标签
     *
     * @return 订阅会话标签
     */
    public Set<String> getSubscribeSessionsTag() {
        return this.sessionsTags;
    }

    /**
     * 获取当前订阅会话数量
     *
     * @return 订阅会话数量
     */
    public int getSubscribeQuantity() {
        return this.sessionsTags.size();
    }

    /**
     * 是否存在订阅会话
     *
     * @return 是否存在订阅会话
     */
    public boolean unsubscribed() {
        return this.sessionsTags.isEmpty();
    }

    /**
     * 主题持有人是该会话
     *
     * @param sessions 会话
     * @return 主题持有人是否是该会话
     */
    public boolean isOwnRoom(String sessions) {
        return this.holder.equals(sessions);
    }

    /**
     * 用户已经存在房间
     *
     * @param sessions 会话
     * @return 用户是否已经存在房间
     */
    public boolean exist(String sessions) {
        return this.sessionsTags.contains(sessions);
    }

}
