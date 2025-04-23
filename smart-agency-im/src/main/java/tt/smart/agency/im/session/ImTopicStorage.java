package tt.smart.agency.im.session;

import tt.smart.agency.im.domain.ImTopic;

/**
 * <p>
 * 即时通讯主题存储
 * </p>
 *
 * @author MC_Yang
 * @version V1.0
 **/
public interface ImTopicStorage {

    /**
     * 得到主题
     *
     * @param topic 主题
     * @return 主题
     */
    ImTopic getTopic(String topic);

    /**
     * 用户创建主题
     *
     * @param userId 主题持有人
     * @param topic  主题
     */
    void createTopic(String userId, String topic);

    /**
     * 订阅主题
     *
     * @param userId 用户
     * @param topic  主题
     */
    void subscribeTopic(String userId, String topic);

    /**
     * 取消订阅主题。<br>
     * 注意：如果离开的主题持有人是该用户，则直接销毁该主题
     *
     * @param userId 用户
     * @param topic  主题
     */
    void unsubscribeTopic(String userId, String topic);

    /**
     * 销毁主题
     *
     * @param topic 主题
     */
    void destroyTopic(String topic);

    /**
     * 销毁主题持有人是指定用户的主题
     *
     * @param userId 主题持有人
     * @param topic  主题
     */
    void destroyTopic(String userId, String topic);

    /**
     * 存在该主题
     *
     * @param topic 主题
     * @return 是否存在该主题
     */
    boolean existTopic(String topic);

}