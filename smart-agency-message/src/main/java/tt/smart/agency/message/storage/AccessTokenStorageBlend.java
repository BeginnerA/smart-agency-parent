package tt.smart.agency.message.storage;

import tt.smart.agency.message.domain.AccessToken;

/**
 * <p>
 * 通用访问令牌（ACCESS_TOKEN）存储
 * </p>
 *
 * @author MC_Yang
 * @version V1.0
 **/
public interface AccessTokenStorageBlend {

    /**
     * 获取访问令牌（ACCESS_TOKEN）
     *
     * @param ident 凭证（唯一的）
     * @return 访问令牌
     */
    String getAccessToken(String ident);

    /**
     * 访问令牌（ACCESS_TOKEN）是否过期
     *
     * @param ident 凭证（唯一的）
     * @return 是否
     */
    boolean isAccessTokenExpired(String ident);

    /**
     * 强制将令牌（ACCESS_TOKEN）过期掉
     *
     * @param ident 凭证（唯一的）
     */
    void expireAccessToken(String ident);

    /**
     * 应该是线程安全的
     *
     * @param accessToken 要更新的{@link AccessToken}对象
     */
    void updateAccessToken(AccessToken accessToken);

}
