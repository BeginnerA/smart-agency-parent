package tt.smart.agency.component.securityprotect.openapi.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import tt.smart.agency.component.securityprotect.openapi.OpenApiRequestBodyAdvice;
import tt.smart.agency.component.securityprotect.openapi.OpenApiResponseBodyAdvice;
import tt.smart.agency.component.securityprotect.openapi.annotation.aspect.OpenApiUserAuthAspect;
import tt.smart.agency.component.securityprotect.openapi.config.properties.OpenApiAuthProperties;
import tt.smart.agency.component.securityprotect.openapi.handler.AuthHandler;
import tt.smart.agency.component.securityprotect.openapi.handler.DefaultAuthHandler;
import tt.smart.agency.component.securityprotect.openapi.handler.EncryptorManager;

/**
 * <p>
 * 开放 API 配置
 * </p>
 *
 * @author MC_Yang
 * @version V1.0
 **/
@EnableConfigurationProperties({OpenApiAuthProperties.class})
public class OpenApiAuthAutoConfig {

    @Bean
    @ConditionalOnMissingBean(OpenApiRequestBodyAdvice.class)
    public OpenApiRequestBodyAdvice openApiRequestBodyAdvice() {
        return new OpenApiRequestBodyAdvice();
    }

    @Bean
    @ConditionalOnMissingBean(OpenApiResponseBodyAdvice.class)
    public OpenApiResponseBodyAdvice openApiResponseBodyAdvice() {
        return new OpenApiResponseBodyAdvice();
    }

    @Bean
    @ConditionalOnMissingBean(AuthHandler.class)
    public AuthHandler authHandler() {
        return new DefaultAuthHandler();
    }

    @Bean
    @ConditionalOnMissingBean(EncryptorManager.class)
    public EncryptorManager encryptorManager() {
        return new EncryptorManager();
    }

    @Bean
    @ConditionalOnMissingBean(OpenApiUserAuthAspect.class)
    public OpenApiUserAuthAspect openApiUserAuthAspect(OpenApiAuthProperties openApiAuthProperties, AuthHandler authHandler) {
        return new OpenApiUserAuthAspect(openApiAuthProperties, authHandler);
    }

}
