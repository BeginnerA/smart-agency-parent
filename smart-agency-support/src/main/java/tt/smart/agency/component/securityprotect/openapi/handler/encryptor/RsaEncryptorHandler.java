package tt.smart.agency.component.securityprotect.openapi.handler.encryptor;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.crypto.asymmetric.KeyType;
import cn.hutool.crypto.asymmetric.RSA;
import tt.smart.agency.component.securityprotect.openapi.enums.AlgorithmType;
import tt.smart.agency.component.securityprotect.openapi.enums.EncodeType;
import tt.smart.agency.component.securityprotect.openapi.handler.AbstractEncryptorHandler;
import tt.smart.agency.component.securityprotect.openapi.handler.EncryptContext;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

/**
 * <p>
 * RSA 算法实现
 * </p>
 *
 * @author MC_Yang
 * @version V1.0
 */
public class RsaEncryptorHandler extends AbstractEncryptorHandler {

    public RsaEncryptorHandler(EncryptContext context) {
        super(context);
        String privateKey = context.getPrivateKey();
        String publicKey = context.getPublicKey();
        if (StrUtil.isAllNotEmpty(privateKey, publicKey)) {
            throw new IllegalArgumentException("RSA 公私钥均需要提供，公钥加密，私钥解密");
        }
    }

    /**
     * 断言公钥
     *
     * @return 公钥
     */
    private String assertKey() {
        String publicKey = context.getPublicKey();
        if (StrUtil.isBlank(publicKey)) {
            throw new IllegalArgumentException("RSA 需要传入公钥进行加密");
        }
        return publicKey;
    }

    @Override
    public AlgorithmType algorithm() {
        return AlgorithmType.RSA;
    }

    @Override
    public Object encrypt(Object value, EncodeType encodeType) {
        RSA rsa = SecureUtil.rsa(null, assertKey());
        if (encodeType == EncodeType.HEX) {
            return rsa.encryptHex(Convert.toStr(value), StandardCharsets.UTF_8, KeyType.PublicKey);
        } else {
            return rsa.encryptBase64(Convert.toStr(value), StandardCharsets.UTF_8, KeyType.PublicKey);
        }
    }

    @Override
    public InputStream decrypt(InputStream value) {
        RSA rsa = SecureUtil.rsa(assertKey(), null);
        byte[] decrypt = rsa.decrypt(value, KeyType.PrivateKey);
        return new ByteArrayInputStream(decrypt);
    }

}
