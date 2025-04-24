package tt.smart.agency.component.securityprotect.openapi.config.properties;

import tt.smart.agency.component.securityprotect.openapi.handler.UserContext;
import lombok.Data;

import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

/**
 * <p>
 * 开放 API 用户配置
 * </p>
 *
 * @author MC_Yang
 * @version V1.0
 **/
@Data
public class UserProperties {

    /**
     * 用户密钥
     */
    private Map<String, String> secretKeyMap;

    /**
     * 通过用户公钥获取用户上下文
     *
     * @param publicKey 用户公钥
     * @return 用户上下文
     */
    public UserContext getUserContext(String publicKey) {
        if (secretKeyMap == null) {
            return null;
        }
        AtomicReference<UserContext> userContext = new AtomicReference<>();
        secretKeyMap.forEach((k, v) -> {
            if (publicKey.equals(k)) {
                userContext.set(new UserContext());
                userContext.get().setPublicKey(k);
                userContext.get().setPrivateKey(v);
            }
        });
        return userContext.get();
    }

}
