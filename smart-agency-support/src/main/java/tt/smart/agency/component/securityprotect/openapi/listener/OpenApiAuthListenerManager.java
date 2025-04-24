package tt.smart.agency.component.securityprotect.openapi.listener;

import cn.hutool.extra.spring.SpringUtil;
import tt.smart.agency.component.securityprotect.openapi.handler.AuthenticationContext;
import org.springframework.beans.BeansException;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * 开放 API 认证监听器管理器
 * </p>
 *
 * @author MC_Yang
 * @version V1.0
 **/
public class OpenApiAuthListenerManager {

    /**
     * 执行用户访问密钥映射监听器
     *
     * @return 用户访问密钥
     */
    public static Map<String, String> executeUserSecretKeyMap() {
        Map<String, String> userSecretKeyMap = new HashMap<>();
        try {
            OpenApiAuthListener apiAuthListener = SpringUtil.getBean(OpenApiAuthListener.class);
            userSecretKeyMap = apiAuthListener.userSecretKeyMap();
        } catch (BeansException ignored) {
        }
        return userSecretKeyMap;
    }

    /**
     * 执行增强开放 API 安全认证监听器
     *
     * @param authenticationContext 用户安全访问身份验证上下文
     * @return 是否通过
     */
    public static boolean executeEnhanceAuthSecure(AuthenticationContext authenticationContext) {
        try {
            OpenApiAuthListener apiAuthListener = SpringUtil.getBean(OpenApiAuthListener.class);
            return apiAuthListener.enhanceAuthSecure(authenticationContext);
        } catch (BeansException ignored) {
        }
        return true;
    }

}
