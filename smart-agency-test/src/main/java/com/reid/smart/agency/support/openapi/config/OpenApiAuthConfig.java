package com.reid.smart.agency.support.openapi.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import tt.smart.agency.component.securityprotect.openapi.handler.AuthenticationContext;
import tt.smart.agency.component.securityprotect.openapi.listener.OpenApiAuthListener;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 开放 API 授权配置
 * </p>
 *
 * @author MC_Yang
 * @version V1.0
 **/
@Configuration
@RequiredArgsConstructor
public class OpenApiAuthConfig extends OpenApiAuthListener {

    @Override
    public Map<String, String> userSecretKeyMap() {
        // TODO 开放 API 用户访问密钥
        Map<String, String> userSecretKeyMap = new HashMap<>();
        userSecretKeyMap.put("admin", "admin");
        userSecretKeyMap.put("test", "test");
        System.out.println("来自任意配置的用户访问密钥");
        return userSecretKeyMap;
    }

    @Override
    public boolean enhanceAuthSecure(AuthenticationContext authenticationContext) {
        // TODO 开放 API 认证通过后需要登录在此实现
        System.out.println("用户增强了开放接口访问鉴权");
        return true;
    }
}
