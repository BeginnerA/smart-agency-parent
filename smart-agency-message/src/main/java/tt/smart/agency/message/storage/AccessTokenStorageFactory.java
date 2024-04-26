package tt.smart.agency.message.storage;

import cn.hutool.extra.spring.SpringUtil;
import tt.smart.agency.cache.strategy.CacheStrategy;

/**
 * <p>
 * 访问令牌（ACCESS_TOKEN）存储服务工厂
 * </p>
 *
 * @author YangMC
 * @version V1.0
 **/
public class AccessTokenStorageFactory {

    private static volatile AccessTokenStorageBlend accessTokenStorage;

    /**
     * 初始化访问令牌（ACCESS_TOKEN）存储服务
     *
     * @return 访问令牌（ACCESS_TOKEN）存储服务
     */
    @SuppressWarnings(value = {"unchecked", "rawtypes"})
    public static AccessTokenStorageBlend initAccessTokenStorageBlend() {
        if (accessTokenStorage == null) {
            synchronized (AccessTokenStorageBlend.class) {
                if (accessTokenStorage == null) {
                    CacheStrategy cacheStrategy = SpringUtil.getBean(CacheStrategy.class);
                    accessTokenStorage = new DefaultAccessTokenStorage(cacheStrategy);
                }
            }
        }
        return accessTokenStorage;
    }

}
