package tt.smart.agency.component.securityprotect.openapi.handler.encryptor;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.crypto.symmetric.AES;
import tt.smart.agency.component.securityprotect.openapi.enums.AlgorithmType;
import tt.smart.agency.component.securityprotect.openapi.enums.EncodeType;
import tt.smart.agency.component.securityprotect.openapi.handler.AbstractEncryptorHandler;
import tt.smart.agency.component.securityprotect.openapi.handler.EncryptContext;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

/**
 * <p>
 * AES 算法实现
 * </p>
 *
 * @author MC_Yang
 * @version V1.0
 */
public class AesEncryptorHandler extends AbstractEncryptorHandler {

    public AesEncryptorHandler(EncryptContext context) {
        super(context);
    }

    /**
     * 断言密钥
     *
     * @return 密钥
     */
    private String assertKey() {
        String password = context.getPrivateKey();
        if (StrUtil.isBlank(password)) {
            throw new IllegalArgumentException("AES 需要传入秘钥信息");
        }
        // AES 算法的秘钥要求是16位、24位、32位
        int[] array = {16, 24, 32};
        if (!ArrayUtil.contains(array, password.length())) {
            throw new IllegalArgumentException("AES 秘钥长度要求为16位、24位、32位");
        }
        return password;
    }

    @Override
    public AlgorithmType algorithm() {
        return AlgorithmType.AES;
    }

    @Override
    public Object encrypt(Object value, EncodeType encodeType) {
        AES aes = SecureUtil.aes(assertKey().getBytes(StandardCharsets.UTF_8));
        if (encodeType == EncodeType.HEX) {
            return aes.encryptHex(Convert.toStr(value), StandardCharsets.UTF_8);
        } else {
            return aes.encryptBase64(Convert.toStr(value), StandardCharsets.UTF_8);
        }
    }

    @Override
    public InputStream decrypt(InputStream value) {
        byte[] decrypt = SecureUtil.aes(assertKey().getBytes(StandardCharsets.UTF_8)).decrypt(value);
        return new ByteArrayInputStream(decrypt);
    }


}
