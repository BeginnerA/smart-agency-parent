package tt.smart.agency.component.securityprotect.openapi.config;

import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import tt.smart.agency.component.securityprotect.openapi.OpenApiAuthInterceptor;
import tt.smart.agency.component.securityprotect.openapi.config.properties.OpenApiAuthProperties;
import tt.smart.agency.component.securityprotect.openapi.handler.AuthHandler;

/**
 * <p>
 * 打开 API 授权 WEB MVC 配置
 * </p>
 *
 * @author MC_Yang
 * @version V1.0
 **/
@RequiredArgsConstructor
public class OpenApiAuthWebMvcConfig implements WebMvcConfigurer {

    private final OpenApiAuthProperties openApiAuthProperties;
    private final AuthHandler authHandler;

    @Override
    public void addInterceptors(@NonNull InterceptorRegistry registry) {
        registry.addInterceptor(new OpenApiAuthInterceptor(openApiAuthProperties, authHandler)).order(-1);
    }
}
