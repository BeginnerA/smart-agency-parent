package tt.smart.agency.component.securityprotect.openapi.handler;

import tt.smart.agency.component.securityprotect.openapi.enums.AlgorithmType;
import tt.smart.agency.component.securityprotect.openapi.enums.EncodeType;

import java.io.InputStream;

/**
 * <p>
 * （加/密）密处理器
 * </p>
 *
 * @author MC_Yang
 * @version V1.0
 **/

public interface EncryptorHandler {

    /**
     * 获得当前算法
     */
    AlgorithmType algorithm();

    /**
     * 加密
     *
     * @param value      待加密对象
     * @param encodeType 加密后的编码格式
     * @return 加密后的对象
     */
    Object encrypt(Object value, EncodeType encodeType);

    /**
     * 解密
     *
     * @param value 待解密的输入流
     * @return 解密后的输入流
     */
    InputStream decrypt(InputStream value);

}
