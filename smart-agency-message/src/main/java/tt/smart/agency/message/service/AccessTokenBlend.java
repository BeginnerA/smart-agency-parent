package tt.smart.agency.message.service;

import tt.smart.agency.message.domain.AccessToken;

import java.util.concurrent.locks.Lock;

/**
 * <p>
 * 通用访问令牌（ACCESS_TOKEN）接口
 * </p>
 *
 * @author MC_Yang
 * @version V1.0
 **/
public interface AccessTokenBlend {

    /**
     * 获取访问令牌（ACCESS_TOKEN）
     *
     * @param forceRefresh 是否强制刷新
     * @param ident        凭证（唯一的）
     * @return 访问令牌
     */
    String getAccessToken(boolean forceRefresh, String ident);

    /**
     * 获取访问令牌（ACCESS_TOKEN）锁
     *
     * @return 访问令牌锁
     */
    Lock getAccessTokenLock();

    /**
     * 访问令牌（ACCESS_TOKEN）是否过期
     *
     * @param ident 凭证（唯一的）
     * @return 是否
     */
    boolean isAccessTokenExpired(String ident);

    /**
     * 访问令牌未过期
     *
     * @param ident 凭证（唯一的）
     * @return 是否
     */
    default boolean notAccessTokenExpired(String ident) {
        return !this.isAccessTokenExpired(ident);
    }

    /**
     * 强制将令牌（ACCESS_TOKEN）过期掉
     *
     * @param ident 凭证（唯一的）
     */
    void expireAccessToken(String ident);

    /**
     * 更新访问令牌
     *
     * @param accessToken 要更新的{@link AccessToken}对象
     */
    void updateAccessToken(AccessToken accessToken);

}
