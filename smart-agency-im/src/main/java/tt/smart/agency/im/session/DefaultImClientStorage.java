package tt.smart.agency.im.session;

import cn.hutool.core.util.StrUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import tt.smart.agency.cache.strategy.CacheStrategy;
import tt.smart.agency.im.domain.ImTopic;
import tt.smart.agency.im.exception.ImException;

/**
 * <p>
 * 默认的会话存储
 * </p>
 *
 * @author YangMC
 * @version V1.0
 **/
public class DefaultImClientStorage extends AbstractImClientStorage {

    private final CacheStrategy<String, Object> cacheStrategy;

    public DefaultImClientStorage(CacheStrategy<String, Object> cacheStrategy) {
        this.cacheStrategy = cacheStrategy;
    }

    @Override
    public void saveUserAccess(String userId, String session) {
        cacheStrategy.put(super.getAccessKey(userId), session, EXPIRATION_TIME);
    }

    @Override
    public String getUserAccess(String userId) {
        return StrUtil.toStringOrNull(cacheStrategy.get(super.getAccessKey(userId)));
    }

    @Override
    public void removeUserAccess(String userId) {
        cacheStrategy.remove(super.getAccessKey(userId));
    }

    /**
     * 通过 key 获取主题
     *
     * @param key key
     * @return 主题
     */
    private ImTopic getTopicByKey(String key) {
        try {
            String roomJson = StrUtil.toStringOrNull(cacheStrategy.get(key));
            if (roomJson == null) {
                return null;
            }
            return new ObjectMapper().readValue(roomJson, ImTopic.class);
        } catch (Exception e) {
            throw new ImException("从缓存中获取主题失败", e);
        }
    }

    @Override
    public ImTopic getTopic(String topic) {
        return getTopicByKey(super.getTopicKey(topic));
    }

    /**
     * 刷新主题
     *
     * @param topic 主题
     */
    private void refreshTopic(ImTopic topic) {
        try {
            String topicTag = topic.getTopicTag();
            String roomJson = new ObjectMapper().writeValueAsString(topic);
            destroyTopic(topicTag);
            cacheStrategy.put(super.getTopicKey(topicTag), roomJson, EXPIRATION_TIME);
        } catch (Exception e) {
            throw new ImException("缓存刷入[" + topic + "]主题失败", e);
        }
    }

    @Override
    public void createTopic(String userId, String topic) {
        if (!existTopic(topic)) {
            ImTopic imTopic = new ImTopic(topic, userId);
            refreshTopic(imTopic);
        }
    }

    @Override
    public void subscribeTopic(String userId, String topic) {
        String userAccess = getUserAccess(userId);
        ImTopic imTopic = getTopic(topic);
        if (imTopic != null && userAccess != null) {
            imTopic.subscribe(userAccess);
            refreshTopic(imTopic);
        } else {
            throw new ImException("订阅主题失败：缓存中不存在该主题或该用户会话");
        }
    }

    @Override
    public void unsubscribeTopic(String userId, String topic) {
        String userAccess = getUserAccess(userId);
        ImTopic imTopic = getTopic(topic);
        if (imTopic != null && userAccess != null) {
            if (imTopic.isOwnRoom(userAccess)) {
                destroyTopic(topic);
            } else {
                if (imTopic.exist(userAccess)) {
                    imTopic.unsubscribe(userAccess);
                    refreshTopic(imTopic);
                }
            }
        } else {
            throw new ImException("订阅主题失败：缓存中不存在该主题或该用户会话");
        }
    }

    @Override
    public void destroyTopic(String topic) {
        cacheStrategy.remove(super.getTopicKey(topic));
    }

    @Override
    public void destroyTopic(String userId, String topic) {
        String userAccess = getUserAccess(userId);
        ImTopic imTopic = getTopic(topic);
        if (imTopic != null && userAccess != null) {
            if (imTopic.isOwnRoom(userAccess)) {
                destroyTopic(topic);
            }
        } else {
            throw new ImException("销毁主题失败：缓存中不存在该主题或该用户会话");
        }
    }

    @Override
    public boolean existTopic(String topic) {
        return cacheStrategy.containsKey(super.getTopicKey(topic));
    }
}
