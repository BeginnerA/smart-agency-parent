package tt.smart.agency.component.securityprotect.openapi.handler.encryptor;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.SmUtil;
import cn.hutool.crypto.symmetric.SM4;
import tt.smart.agency.component.securityprotect.openapi.enums.AlgorithmType;
import tt.smart.agency.component.securityprotect.openapi.enums.EncodeType;
import tt.smart.agency.component.securityprotect.openapi.handler.AbstractEncryptorHandler;
import tt.smart.agency.component.securityprotect.openapi.handler.EncryptContext;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

/**
 * <p>
 * SM4 算法实现
 * </p>
 *
 * @author MC_Yang
 * @version V1.0
 */
public class Sm4EncryptorHandler extends AbstractEncryptorHandler {

    public Sm4EncryptorHandler(EncryptContext context) {
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
            throw new IllegalArgumentException("SM4 需要传入秘钥信息");
        }
        // SM4 算法的秘钥要求是16位长度
        int sm4PasswordLength = 16;
        if (sm4PasswordLength != password.length()) {
            throw new IllegalArgumentException("SM4 秘钥长度要求为16位");
        }
        return password;
    }

    @Override
    public AlgorithmType algorithm() {
        return AlgorithmType.SM4;
    }

    @Override
    public Object encrypt(Object value, EncodeType encodeType) {
        SM4 sm4 = SmUtil.sm4(assertKey().getBytes(StandardCharsets.UTF_8));
        if (encodeType == EncodeType.HEX) {
            return sm4.encryptHex(Convert.toStr(value), StandardCharsets.UTF_8);
        } else {
            return sm4.encryptBase64(Convert.toStr(value), StandardCharsets.UTF_8);
        }
    }

    @Override
    public InputStream decrypt(InputStream value) {
        byte[] decryptByte = SmUtil.sm4(assertKey().getBytes(StandardCharsets.UTF_8)).decrypt(value);
        return new ByteArrayInputStream(decryptByte);
    }
}
