package tt.smart.agency.im.client;

import tt.smart.agency.im.domain.ImTopic;

import java.util.Set;

/**
 * <p>
 * 即时通讯主题客户端
 * </p>
 *
 * @author YangMC
 * @version V1.0
 **/
public interface ImTopicClient {

    /**
     * 订阅会话用户指定主题。<br>
     * 注意：如果订阅主题不存在则创建持有者为该会话用户的新主题
     *
     * @param topic        主题唯一标识
     * @param sessionUsers 会话用户
     */
    void subscribe(String topic, String sessionUsers);

    /**
     * 订阅会话用户指定全部主题。<br>
     * 注意：如果订阅主题不存在则创建持有者为该会话用户的新主题
     *
     * @param topics       主题唯一标识
     * @param sessionUsers 会话用户
     */
    void subscribeAll(Set<String> topics, String sessionUsers);

    /**
     * 取消订阅会话用户指定主题
     *
     * @param topic        主题唯一标识
     * @param sessionUsers 会话用户
     */
    void unsubscribe(String topic, String sessionUsers);

    /**
     * 取消订阅会话用户指定全部主题。<br>
     * 注意：如果取消订阅的主题持有人是该会话用户，则直接销毁该主题
     *
     * @param topics       主题唯一标识
     * @param sessionUsers 会话用户
     */
    void unsubscribeAll(Set<String> topics, String sessionUsers);

    /**
     * 得到指定的主题
     *
     * @param topic 主题唯一标识
     * @return 主题
     */
    ImTopic getSocketTopic(String topic);

    /**
     * 按会话用户获取订阅主题唯一标识列表
     *
     * @param sessionUsers 会话用户
     * @return 主题唯一标识列表
     */
    Set<String> getSubscribeTopicIdentityList(String sessionUsers);

    /**
     * 按会话用户获取订阅主题列表
     *
     * @param sessionUsers 会话用户
     * @return 主题列表
     */
    Set<ImTopic> getSubscribeTopicList(String sessionUsers);

    /**
     * 销毁主题持有者是该会话用户的全部主题
     *
     * @param sessionUsers 会话用户
     */
    void destroyTopic(String sessionUsers);

}
