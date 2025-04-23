package tt.smart.agency.im.service;

import tt.smart.agency.im.domain.ImTopic;

import java.util.Set;

/**
 * <p>
 * 即时通讯主题服务
 * </p>
 *
 * @author MC_Yang
 * @version V1.0
 **/
public interface ImTopicService {

    /**
     * 订阅。<br>
     * 如果订阅主题不存在则创建持有者为该会话标识的新主题
     *
     * @param topic 主题
     * @param tag   会话标识
     */
    void subscribe(String topic, String tag);

    /**
     * 订阅全部。<br>
     * 如果订阅主题不存在则创建持有者为该会话标识的新主题
     *
     * @param topics 主题列表
     * @param tag    会话标识
     */
    void subscribeAll(Set<String> topics, String tag);

    /**
     * 取消订阅
     *
     * @param topic 主题
     * @param tag   会话标识
     */
    void unsubscribe(String topic, String tag);

    /**
     * 取消全部订阅
     *
     * @param topics 主题
     * @param tag    会话标识
     */
    void unsubscribeAll(Set<String> topics, String tag);

    /**
     * 按标签获取订阅主题列表
     *
     * @param tag 标签
     * @return 订阅主题列表
     */
    Set<String> getSubscribeTopicList(String tag);

    /**
     * 得到指定的主题
     *
     * @param topic 主题标识
     * @return 主题
     */
    ImTopic getSocketTopic(String topic);

    /**
     * 销毁持有者主题
     *
     * @param tag 标签
     */
    void destroyTopic(String tag);

}
