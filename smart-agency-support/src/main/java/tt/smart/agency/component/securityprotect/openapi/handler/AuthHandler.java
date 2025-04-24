package tt.smart.agency.component.securityprotect.openapi.handler;

import cn.hutool.crypto.digest.DigestAlgorithm;

import java.util.Map;
import java.util.SortedMap;

/**
 * <p>
 * 权限处理器 API
 * </p>
 *
 * @author MC_Yang
 * @version V1.0
 **/
public interface AuthHandler {

    /**
     * 默认签名算法
     */
    DigestAlgorithm DEFAULT_DIGEST_ALGORITHM = DigestAlgorithm.MD5;

    /**
     * 访问公钥参数名
     */
    String ACCESS_KEY_PARAM_NAME = "accessKey";

    /**
     * 访问私钥参数名
     */
    String SECRET_KEY_PARAM_NAME = "secretKey";

    /**
     * 访问时间戳参数名
     */
    String TIMESTAMP_PARAM_NAME = "timestamp";

    /**
     * 访问随机数参数名
     */
    String NONCE_PARAM_NAME = "nonce";

    /**
     * 访问签名参数名
     */
    String SIGN_PARAM_NAME = "sign";

    /**
     * 通过用户公钥获取私钥
     *
     * @param accessKey 用户公钥
     * @return 私钥
     */
    String getUserSecretKey(String accessKey);

    /**
     * 保存用户请求随机数
     *
     * @param accessKey 用户公钥
     * @param nonce     随机数
     * @return 结果
     */
    boolean saveUserNonce(String accessKey, String nonce);

    /**
     * 获取用户上次请求随机数
     *
     * @param accessKey 用户公钥
     * @return 结果
     */
    String getUserLastNonce(String accessKey);

    /**
     * 根据请求参数生成签名
     *
     * @param algorithm 签名算法
     * @param params    请求参数
     * @return 签名
     */
    String generateSign(DigestAlgorithm algorithm, SortedMap<String, Object> params);

    /**
     * 验证签名
     *
     * @param authenticationContext 认证参数
     * @param params                请求参数
     * @return 结果
     */
    boolean auth(AuthenticationContext authenticationContext, Map<String, Object> params);

}
