package tt.smart.agency.component.securityprotect.openapi.handler;

import tt.smart.agency.component.securityprotect.openapi.config.properties.OpenApiAuthProperties;
import lombok.Data;

/**
 * <p>
 * 验证参数
 * </p>
 *
 * @author MC_Yang
 * @version V1.0
 **/
@Data
public class AuthenticationContext {

    /**
     * 开放API配置项
     */
    private OpenApiAuthProperties openApiAuthConfig;

    /**
     * 用户访问公钥
     */
    private String accessKey;

    /**
     * 用户访问私钥
     */
    private String secretKey;

    /**
     * 当前时间毫秒级时间戳
     */
    private Long timestamp;

    /**
     * 随机字符串，每次请求不重复
     */
    private String nonce;

    /**
     * 签名
     */
    private String sign;

}
