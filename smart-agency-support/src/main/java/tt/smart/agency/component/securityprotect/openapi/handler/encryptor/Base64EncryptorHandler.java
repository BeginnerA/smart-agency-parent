package tt.smart.agency.component.securityprotect.openapi.handler.encryptor;

import cn.hutool.core.codec.Base64;
import cn.hutool.core.convert.Convert;
import cn.hutool.core.io.IoUtil;
import tt.smart.agency.component.securityprotect.openapi.enums.AlgorithmType;
import tt.smart.agency.component.securityprotect.openapi.enums.EncodeType;
import tt.smart.agency.component.securityprotect.openapi.handler.AbstractEncryptorHandler;
import tt.smart.agency.component.securityprotect.openapi.handler.EncryptContext;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

/**
 * <p>
 * Base64 算法实现
 * </p>
 *
 * @author MC_Yang
 * @version V1.0
 */
public class Base64EncryptorHandler extends AbstractEncryptorHandler {

    public Base64EncryptorHandler(EncryptContext context) {
        super(context);
    }

    @Override
    public AlgorithmType algorithm() {
        return AlgorithmType.BASE64;
    }

    @Override
    public Object encrypt(Object value, EncodeType encodeType) {
        return Base64.encode(Convert.toStr(value), StandardCharsets.UTF_8);
    }

    @Override
    public InputStream decrypt(InputStream value) {
        byte[] bytes = IoUtil.readBytes(value);
        byte[] decode = Base64.decode(bytes);
        return new ByteArrayInputStream(decode);
    }

}
