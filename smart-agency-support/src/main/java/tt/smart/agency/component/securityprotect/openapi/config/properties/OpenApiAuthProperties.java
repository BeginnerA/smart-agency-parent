package tt.smart.agency.component.securityprotect.openapi.config.properties;

import cn.hutool.crypto.digest.DigestAlgorithm;
import tt.smart.agency.component.securityprotect.openapi.enums.AlgorithmType;
import tt.smart.agency.component.securityprotect.openapi.enums.EncodeType;
import tt.smart.agency.component.securityprotect.openapi.enums.StatusEnum;
import tt.smart.agency.component.securityprotect.openapi.handler.EncryptContext;
import tt.smart.agency.component.securityprotect.openapi.handler.UserContext;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.concurrent.TimeUnit;

/**
 * <p>
 * 开放 API 全局配置。<br>
 * 注意：该配置优先级大于注解
 * </p>
 *
 * @author MC_Yang
 * @version V1.0
 **/
@Data
@ConfigurationProperties(prefix = "agency.openapi")
public class OpenApiAuthProperties {

    /**
     * 签名算法
     */
    private DigestAlgorithm digestAlgorithm = DigestAlgorithm.MD5;

    /**
     * 请求是否验证
     */
    private StatusEnum requestAuth = StatusEnum.ENABLE;

    /**
     * 签名时是否需要加入参数
     */
    private StatusEnum signWithParam = StatusEnum.DISABLE;

    /**
     * 请求参数是否解密
     */
    private StatusEnum requestDecrypt = StatusEnum.DISABLE;

    /**
     * 返回值是否加密
     */
    private StatusEnum responseEncrypt = StatusEnum.DISABLE;

    /**
     * 请求超时时间单位
     */
    private TimeUnit timeUnit = TimeUnit.SECONDS;

    /**
     * 请求超时时间
     */
    private Long timeout = 5L;

    /**
     * 开放API（加/解）密属性配置
     */
    private EncryptorProperties encryptor;

    /**
     * 开放API用户配置
     */
    private UserProperties users;

    /**
     * 通过用户公钥获取用户上下文
     *
     * @param publicKey 用户公钥
     * @return 用户上下文
     */
    public UserContext getUserContext(String publicKey) {
        if (users != null) {
            return users.getUserContext(publicKey);
        }
        return null;
    }

    /**
     * 得到（加/解）密上下文
     *
     * @param publicKey 用户公钥
     * @return （加/解）密上下文
     */
    public EncryptContext encryptContext(String publicKey) {
        EncryptContext context = new EncryptContext();
        UserContext userContext = getUserContext(publicKey);
        context.setAlgorithm(encryptor != null ? encryptor.getAlgorithm() : AlgorithmType.BASE64);
        context.setEncode(encryptor != null ? encryptor.getEncode() : EncodeType.BASE64);
        context.setPrivateKey(encryptor != null ? encryptor.getPrivateKey() : userContext != null ? userContext.getPrivateKey() : null);
        context.setPublicKey(encryptor != null ? encryptor.getPublicKey() : userContext != null ? userContext.getPublicKey() : null);
        return context;
    }

}
