package tt.smart.agency.im.session;

/**
 * <p>
 * 抽象的会话存储
 * </p>
 *
 * @author YangMC
 * @version V1.0
 **/
public abstract class AbstractImClientStorage implements ImAccessStorage, ImTopicStorage {

    /**
     * 默认缓存 key 过期时间（秒）
     */
    protected static final long EXPIRATION_TIME = 60 * 60 * 24;
    /**
     * 缓存会话 key 前缀
     */
    protected final String ACCESS_KEY = "im:access:";
    /**
     * 缓存主题 key 前缀
     */
    protected final String TOPIC_KEY = "im:topic:";

    @Override
    public void userOnline(String userId) {

    }

    @Override
    public void userOffline(String userId) {

    }

    @Override
    public void refreshExpirationTime(String userId) {

    }

    /**
     * 获取缓存会话 key
     *
     * @param userId 用户
     * @return 缓存会话 key
     */
    protected String getAccessKey(String userId) {
        return ACCESS_KEY + userId;
    }

    /**
     * 得到缓存主题 key
     *
     * @param topic 主题
     * @return 缓存主题 key
     */
    protected String getTopicKey(String topic) {
        return TOPIC_KEY + topic;
    }

}
