package tt.smart.agency.component.securityprotect.openapi.config.properties;

import tt.smart.agency.component.securityprotect.openapi.enums.AlgorithmType;
import tt.smart.agency.component.securityprotect.openapi.enums.EncodeType;
import lombok.Data;

/**
 * <p>
 * （加/解）密属性配置
 * </p>
 *
 * @author MC_Yang
 * @version V1.0
 */
@Data
public class EncryptorProperties {

    /**
     * 默认算法
     */
    private AlgorithmType algorithm = AlgorithmType.BASE64;

    /**
     * 公钥（默认使用用户公钥）
     */
    private String publicKey;

    /**
     * 私钥（默认使用用户私钥）
     */
    private String privateKey;

    /**
     * 编码方式，base64/hex
     */
    private EncodeType encode = EncodeType.BASE64;

}
