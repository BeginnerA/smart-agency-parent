package tt.smart.agency.component.securityprotect.openapi.annotation;

import java.lang.annotation.*;

/**
 * <p>
 * 开放 API 认证注解
 * </p>
 *
 * @author MC_Yang
 * @version V1.0
 **/
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.TYPE})
public @interface OpenApiAuth {

    /**
     * 是否请求身份验证
     */
    boolean requestAuth() default true;

    /**
     * 签名时是否包含参数
     */
    boolean signWithParam() default true;

    /**
     * 是否请求解密
     */
    boolean requestDecrypt() default true;

    /**
     * 是否响应加密
     */
    boolean responseEncrypt() default true;

}
