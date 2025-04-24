package tt.smart.agency.component.securityprotect.openapi.handler;

import tt.smart.agency.component.securityprotect.openapi.config.properties.OpenApiAuthProperties;
import tt.smart.agency.component.securityprotect.openapi.listener.OpenApiAuthListenerManager;
import jakarta.annotation.Resource;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * <p>
 * 默认权限处理器实现
 * </p>
 *
 * @author MC_Yang
 * @version V1.0
 **/
public class DefaultAuthHandler extends AbstractAuthHandler {

    /**
     * 存储调用方随机数，生产中可放入 Redis，并设置过期时间
     */
    private static final Map<String, String> NONCEMAP = new ConcurrentHashMap<>();

    @Resource
    private OpenApiAuthProperties openApiAuthProperties;

    @Override
    public String getUserSecretKey(String accessKey) {
        UserContext userContext = openApiAuthProperties.getUserContext(accessKey);
        if (userContext == null) {
            Map<String, String> stringStringMap = OpenApiAuthListenerManager.executeUserSecretKeyMap();
            if (stringStringMap != null && stringStringMap.containsKey(accessKey)) {
                userContext = new UserContext();
                userContext.setPublicKey(accessKey);
                userContext.setPrivateKey(stringStringMap.get(accessKey));
            }
        }

        return userContext != null ? userContext.getPrivateKey() : null;
    }

    @Override
    public boolean saveUserNonce(String accessKey, String nonce) {
        NONCEMAP.put(accessKey, nonce);
        return true;
    }

    @Override
    public String getUserLastNonce(String accessKey) {
        return NONCEMAP.get(accessKey);
    }

}
