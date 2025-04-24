package tt.smart.agency.component.securityprotect.openapi.handler;

import cn.hutool.core.util.ReflectUtil;
import tt.smart.agency.component.securityprotect.openapi.enums.AlgorithmType;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.InputStream;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * <p>
 * （加/解）密管理类
 * </p>
 *
 * @author MC_Yang
 * @version V1.0
 **/
@Slf4j
@NoArgsConstructor
public class EncryptorManager {

    /**
     * 缓存（加/解）密处理程序
     */
    Map<AlgorithmType, EncryptorHandler> encryptorMap = new ConcurrentHashMap<>();

    /**
     * 注册加密执行者到缓存
     *
     * @param encryptContext 加密执行者需要的相关配置参数
     */
    public EncryptorHandler registerEncryptor(EncryptContext encryptContext) {
        if (encryptorMap.containsKey(encryptContext.getAlgorithm())) {
            return encryptorMap.get(encryptContext.getAlgorithm());
        }
        EncryptorHandler encryptor = ReflectUtil.newInstance(encryptContext.getAlgorithm().getClazz(), encryptContext);
        encryptorMap.put(encryptContext.getAlgorithm(), encryptor);
        return encryptor;
    }

    /**
     * 根据配置进行加密
     *
     * @param value          待加密的值
     * @param encryptContext 加密相关的配置信息
     */
    public Object encrypt(Object value, EncryptContext encryptContext) {
        EncryptorHandler encryptor = registerEncryptor(encryptContext);
        return encryptor.encrypt(value, encryptContext.getEncode());
    }

    /**
     * 根据配置进行解密
     *
     * @param value          待解密的值
     * @param encryptContext 加密相关的配置信息
     */
    public InputStream decrypt(InputStream value, EncryptContext encryptContext) {
        EncryptorHandler encryptor = registerEncryptor(encryptContext);
        return encryptor.decrypt(value);
    }


}
