package tt.smart.agency.component.securityprotect.openapi.annotation;

import tt.smart.agency.component.securityprotect.openapi.config.OpenApiAuthAutoConfig;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * <p>
 * 全局启用注解
 * </p>
 *
 * @author MC_Yang
 * @version V1.0
 **/
@Inherited
@Documented
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Import({OpenApiAuthAutoConfig.class})
public @interface EnableOpenApiAuth {
}
