package tt.smart.agency.component.securityprotect.openapi.enums;

import tt.smart.agency.component.securityprotect.openapi.handler.AbstractEncryptorHandler;
import tt.smart.agency.component.securityprotect.openapi.handler.encryptor.*;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * <p>
 * （加/解）密算法名称
 * </p>
 *
 * @author MC_Yang
 * @version V1.0
 */
@Getter
@AllArgsConstructor
public enum AlgorithmType {

    /**
     * base64
     */
    BASE64(Base64EncryptorHandler.class),

    /**
     * aes
     */
    AES(AesEncryptorHandler.class),

    /**
     * rsa
     */
    RSA(RsaEncryptorHandler.class),

    /**
     * sm2
     */
    SM2(Sm2EncryptorHandler.class),

    /**
     * sm4
     */
    SM4(Sm4EncryptorHandler.class);

    private final Class<? extends AbstractEncryptorHandler> clazz;
}
