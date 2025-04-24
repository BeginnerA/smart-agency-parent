package tt.smart.agency.component.securityprotect.openapi.handler;

import tt.smart.agency.component.securityprotect.openapi.enums.AlgorithmType;
import tt.smart.agency.component.securityprotect.openapi.enums.EncodeType;
import lombok.Data;

/**
 * <p>
 * （加/解）密上下文
 * </p>
 *
 * @author MC_Yang
 * @version V1.0
 */
@Data
public class EncryptContext {

    /**
     * 默认算法
     */
    private AlgorithmType algorithm;

    /**
     * 公钥
     */
    private String publicKey;

    /**
     * 私钥
     */
    private String privateKey;

    /**
     * 编码方式，base64/hex
     */
    private EncodeType encode;

}
