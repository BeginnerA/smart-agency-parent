package tt.smart.agency.message.storage;

/**
 * <p>
 * 抽象的通用访问令牌（ACCESS_TOKEN）存储接口服务
 * </p>
 *
 * @author MC_Yang
 * @version V1.0
 **/
public abstract class AbstractAccessTokenStorageBlend implements AccessTokenStorageBlend {
    /**
     * 访问令牌缓存 key
     */
    protected static final String DEFAULT_MAP_KEY = "ACCESS_TOKEN:";
}
