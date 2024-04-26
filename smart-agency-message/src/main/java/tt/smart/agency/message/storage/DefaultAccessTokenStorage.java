package tt.smart.agency.message.storage;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import tt.smart.agency.cache.strategy.CacheStrategy;
import tt.smart.agency.message.domain.AccessToken;

/**
 * <p>
 * 默认访问令牌（ACCESS_TOKEN）存储服务
 * </p>
 *
 * @author YangMC
 * @version V1.0
 **/
public class DefaultAccessTokenStorage extends AbstractAccessTokenStorageBlend {

    private final CacheStrategy<String, Object> cacheStrategy;

    public DefaultAccessTokenStorage(CacheStrategy<String, Object> cacheStrategy) {
        this.cacheStrategy = cacheStrategy;
    }

    @Override
    public String getAccessToken(String ident) {
        AccessToken accessToken = getWxAccessToken(getKey(ident));
        if (accessToken == null) {
            return null;
        }
        return accessToken.getAccessToken();
    }

    @Override
    public boolean isAccessTokenExpired(String ident) {
        AccessToken accessToken = getWxAccessToken(getKey(ident));
        if (accessToken != null) {
            return System.currentTimeMillis() > accessToken.getExpiresTime();
        }
        return true;
    }

    @Override
    public void expireAccessToken(String ident) {
        AccessToken accessToken = getWxAccessToken(getKey(ident));
        if (accessToken != null) {
            accessToken.setExpiresTime(0);
        }
    }

    @Override
    public synchronized void updateAccessToken(AccessToken accessToken) {
        String key = getKey(accessToken.getIdent());
        if (getWxAccessToken(key) != null) {
            cacheStrategy.remove(key);
        }
        cacheStrategy.put(key, accessToken);
    }

    /**
     * 获取{@link AccessToken}对象
     *
     * @param key key
     * @return {@link AccessToken}对象
     */
    protected AccessToken getWxAccessToken(String key) {
        return BeanUtil.toBean(cacheStrategy.get(key), AccessToken.class);
    }

    /**
     * 获取令牌
     *
     * @param ident 凭证（appId/corpId）
     * @return 令牌
     */
    protected String getKey(String ident) {
        String key = DEFAULT_MAP_KEY;
        if (StrUtil.isNotBlank(ident)) {
            key = DEFAULT_MAP_KEY + ident;
        }
        return key;
    }
}
