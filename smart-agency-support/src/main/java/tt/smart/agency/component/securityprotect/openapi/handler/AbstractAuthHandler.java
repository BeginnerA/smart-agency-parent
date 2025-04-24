package tt.smart.agency.component.securityprotect.openapi.handler;

import cn.hutool.crypto.SignUtil;
import cn.hutool.crypto.digest.DigestAlgorithm;

import java.util.HashMap;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.concurrent.TimeUnit;

/**
 * <p>
 * 处理器抽象类
 * </p>
 *
 * @author MC_Yang
 * @version V1.0
 **/
public abstract class AbstractAuthHandler implements AuthHandler {

    @Override
    public String generateSign(DigestAlgorithm algorithm, SortedMap<String, Object> params) {
        return SignUtil.signParams(algorithm != null ? algorithm : DEFAULT_DIGEST_ALGORITHM, params);
    }

    @Override
    public boolean auth(AuthenticationContext authenticationContext, Map<String, Object> params) {
        // 验证时间
        TimeUnit timeUnit = authenticationContext.getOpenApiAuthConfig().getTimeUnit();
        long timeout = authenticationContext.getOpenApiAuthConfig().getTimeout();
        validTime(authenticationContext.getTimestamp(), timeUnit, timeout);
        // 验证随机字符串
        validNonce(authenticationContext.getNonce(), getUserLastNonce(authenticationContext.getAccessKey()));

        // 验证签名
        if (params == null || params.isEmpty()) {
            params = new HashMap<>();
        }
        params.put(ACCESS_KEY_PARAM_NAME, authenticationContext.getAccessKey());
        params.put(SECRET_KEY_PARAM_NAME, authenticationContext.getSecretKey());
        params.put(TIMESTAMP_PARAM_NAME, authenticationContext.getTimestamp());
        params.put(NONCE_PARAM_NAME, authenticationContext.getNonce());
        String sign = generateSign(authenticationContext.getOpenApiAuthConfig().getDigestAlgorithm(), new TreeMap<>(params));
        if (!authenticationContext.getSign().equalsIgnoreCase(sign)) {
            throw new IllegalArgumentException("签名验证失败");
        }
        return saveUserNonce(authenticationContext.getAccessKey(), authenticationContext.getNonce());
    }

    /**
     * 验证时间戳
     *
     * @param orgTimestamp 请求参数中时间戳
     * @param timeUnit     请求超时时间单位
     * @param timeout      请求超时时间
     */
    private static void validTime(long orgTimestamp, TimeUnit timeUnit, long timeout) {
        timeout = timeUnit.toMillis(timeout);
        long now = System.currentTimeMillis();
        if ((now - orgTimestamp) > timeout) {
            throw new IllegalArgumentException("时间戳超时");
        }
    }

    /**
     * 验证随机数
     *
     * @param orgNonce  请求参数中随机数
     * @param lastNonce 上次请求随机数
     */
    private static void validNonce(String orgNonce, String lastNonce) {
        if (orgNonce.equals(lastNonce)) {
            throw new IllegalArgumentException("随机数重复");
        }
    }
}
