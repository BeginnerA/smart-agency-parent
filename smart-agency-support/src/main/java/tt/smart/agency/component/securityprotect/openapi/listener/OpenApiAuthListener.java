package tt.smart.agency.component.securityprotect.openapi.listener;

import tt.smart.agency.component.securityprotect.openapi.handler.AuthenticationContext;

import java.util.Map;

/**
 * <p>
 * 开放 API 认证监听器
 * </p>
 *
 * @author MC_Yang
 * @version V1.0
 **/
public abstract class OpenApiAuthListener {

    /**
     * 用户访问密钥映射<br>
     * 开放 API 认证会被调用（注意：如果访问用户在配置文件('openapi.users.secret-key-map')中找到，则该方法不会被调用）
     *
     * @return 用户访问密钥映射<用户访问公钥, 用户访问私钥>
     */
    protected abstract Map<String, String> userSecretKeyMap();

    /**
     * 增强开放 API 安全认证<br>
     * 注意：基本身份认证通过后该方法才会被触发
     *
     * @param authenticationContext 身份验证上下文
     * @return 是否通过
     */
    protected abstract boolean enhanceAuthSecure(AuthenticationContext authenticationContext);

}
