package tt.smart.agency.im.service;

import tt.smart.agency.im.domain.ImTopic;

import java.util.List;
import java.util.Set;

/**
 * <p>
 * 即时通讯客户端服务
 * </p>
 *
 * @author YangMC
 * @version V1.0
 **/
public interface ImClientService {

    /**
     * 获取在线的用户集合
     *
     * @return 用户 Set 集合
     */
    Set<String> getOnlineUsers();

    /**
     * 获取在线的用户数量
     *
     * @return 在线用户数
     */
    int getOnlineCount();

    /**
     * 检查用户是否在线
     *
     * @param userId 用户标识
     * @return 结果
     */
    boolean checkUserIsOnline(String userId);

    /**
     * 得到主题
     *
     * @param topic 主题
     * @return 主题
     */
    ImTopic getTopic(String topic);

    /**
     * 得到所有主题
     *
     * @return 主题
     */
    List<ImTopic> getAllTopic();

    /**
     * 得到持有人所有主题
     *
     * @param holder 主题持有人
     * @return 主题
     */
    List<ImTopic> getHolderTopicList(String holder);

    /**
     * 得到持有人主题
     *
     * @param holder 主题持有人
     * @param topic  主题
     * @return 主题
     */
    ImTopic getHolderTopic(String holder, String topic);

    /**
     * 得到主题用户标签
     *
     * @param topic 主题
     * @return 用户标签
     */
    Set<String> getTopicUserTags(String topic);

    /**
     * 得到主题用户标签
     *
     * @param topic         主题
     * @param containHolder 是否包含主题持有人
     * @return 用户标签
     */
    Set<String> getTopicUserTags(String topic, boolean containHolder);

    /**
     * 是用户自己的主题
     *
     * @param holder 主题持有人
     * @param topic  主题
     * @return 是否是用户自己的主题
     */
    boolean isOwnTopic(String holder, String topic);

    /**
     * 存在该主题
     *
     * @param topic 主题
     * @return 是否存在该主题
     */
    boolean existTopic(String topic);

}
