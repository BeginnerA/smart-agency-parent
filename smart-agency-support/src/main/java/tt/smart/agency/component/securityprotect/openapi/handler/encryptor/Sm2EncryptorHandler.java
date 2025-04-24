package tt.smart.agency.component.securityprotect.openapi.handler.encryptor;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.SmUtil;
import cn.hutool.crypto.asymmetric.KeyType;
import cn.hutool.crypto.asymmetric.SM2;
import tt.smart.agency.component.securityprotect.openapi.enums.AlgorithmType;
import tt.smart.agency.component.securityprotect.openapi.enums.EncodeType;
import tt.smart.agency.component.securityprotect.openapi.handler.AbstractEncryptorHandler;
import tt.smart.agency.component.securityprotect.openapi.handler.EncryptContext;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

/**
 * <p>
 * SM2 算法实现
 * </p>
 *
 * @author MC_Yang
 * @version V1.0
 */
public class Sm2EncryptorHandler extends AbstractEncryptorHandler {

    public Sm2EncryptorHandler(EncryptContext context) {
        super(context);
        String privateKey = context.getPrivateKey();
        String publicKey = context.getPublicKey();
        if (StrUtil.isAllNotEmpty(privateKey, publicKey)) {
            throw new IllegalArgumentException("SM2 公私钥均需要提供，公钥加密，私钥解密");
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
            throw new IllegalArgumentException("SM2 需要传入公钥进行加密");
        }
        return publicKey;
    }

    @Override
    public AlgorithmType algorithm() {
        return AlgorithmType.SM2;
    }

    @Override
    public Object encrypt(Object value, EncodeType encodeType) {
        SM2 sm2 = SmUtil.sm2(null, assertKey());
        if (encodeType == EncodeType.HEX) {
            return sm2.encryptHex(Convert.toStr(value), StandardCharsets.UTF_8, KeyType.PublicKey);
        } else {
            return sm2.encryptBase64(Convert.toStr(value), StandardCharsets.UTF_8, KeyType.PublicKey);
        }
    }

    @Override
    public InputStream decrypt(InputStream value) {
        byte[] decrypt = SmUtil.sm2(assertKey(), null).decrypt(value, KeyType.PrivateKey);
        return new ByteArrayInputStream(decrypt);
    }

}
