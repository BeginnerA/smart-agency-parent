package tt.smart.agency.message.service;

import cn.hutool.core.util.StrUtil;
import cn.hutool.http.Method;
import tt.smart.agency.message.domain.AccessToken;
import tt.smart.agency.message.domain.MessageSendBlend;
import tt.smart.agency.message.exception.MessageTokenException;
import tt.smart.agency.message.http.HttpMessageRequest;
import tt.smart.agency.message.storage.AccessTokenStorageBlend;
import tt.smart.agency.message.storage.AccessTokenStorageFactory;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * <p>
 * 抽象的通用访问令牌（ACCESS_TOKEN）服务
 * </p>
 *
 * @param <C> 消息配置对象
 * @author MC_Yang
 * @version V1.0
 **/
public abstract class AbstractAccessTokenBlend<C> extends HttpMessageRequest implements AccessTokenBlend {

    protected AccessTokenStorageBlend accessTokenStorageBlend = AccessTokenStorageFactory.initAccessTokenStorageBlend();
    protected Lock accessTokenLock = new ReentrantLock();

    @Override
    public String getAccessToken(boolean forceRefresh, String ident) {
        String accessToken = accessTokenStorageBlend.getAccessToken(ident);
        if (forceRefresh || StrUtil.isBlank(accessToken)) {
            accessToken = getAccessToken(ident);
        }
        return accessToken;
    }

    @Override
    public Lock getAccessTokenLock() {
        return accessTokenLock;
    }

    @Override
    public boolean isAccessTokenExpired(String ident) {
        return accessTokenStorageBlend.isAccessTokenExpired(ident);
    }

    @Override
    public void expireAccessToken(String ident) {
        accessTokenStorageBlend.expireAccessToken(ident);
    }

    @Override
    public void updateAccessToken(AccessToken accessToken) {
        accessTokenStorageBlend.updateAccessToken(accessToken);
    }

    /**
     * 获取访问令牌（ACCESS_TOKEN）
     *
     * @param url   URL
     * @param ident 凭证（唯一的）
     * @return 访问令牌
     */
    protected String getAccessToken(String url, String ident) {
        if (notAccessTokenExpired(ident)) {
            return getAccessToken(false, ident);
        }
        Lock lock = getAccessTokenLock();
        boolean locked = false;
        try {
            do {
                locked = lock.tryLock(100, TimeUnit.MILLISECONDS);
                if (notAccessTokenExpired(ident)) {
                    return getAccessToken(false, ident);
                }
            } while (!locked);
            String result = super.execute(MessageSendBlend.builder().method(Method.GET).url(url).build());
            return extractAccessToken(result, ident);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            if (locked) {
                lock.unlock();
            }
        }
    }

    /**
     * 提取访问令牌
     *
     * @param resultContent 微信请求结果内容
     * @param ident         凭证（appId/corpId）
     * @return 访问令牌
     */
    private String extractAccessToken(String resultContent, String ident) {
        AccessToken accessToken = AccessToken.fromJson(resultContent);
        if (accessToken.getErrCode() != 0) {
            String errMessage = "获取访问令牌失败：" + resultContent;
            throw new MessageTokenException(String.valueOf(accessToken.getErrCode()), errMessage);
        }
        accessToken.setIdent(ident);
        updateAccessToken(accessToken);
        return accessToken.getAccessToken();
    }

    /**
     * 获取配置文件
     *
     * @return 配置文件
     */
    public abstract C getConfig();

    /**
     * 获取访问令牌（ACCESS_TOKEN）
     *
     * @param ident 凭证（唯一的）
     * @return 访问令牌
     */
    public abstract String getAccessToken(String ident);

}
