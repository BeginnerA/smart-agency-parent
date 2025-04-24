package tt.smart.agency.component.securityprotect.openapi.annotation;

import java.lang.annotation.*;

/**
 * <p>
 * 开放 API 用户基础认证注解（仅认证访问用户是否存在，复杂场景认证请使用{@link OpenApiAuth}）
 * </p>
 *
 * @author MC_Yang
 * @version V1.0
 **/
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.TYPE})
public @interface OpenApiUserAuth {

    /**
     * 是否需要验证访问用户私钥
     */
    boolean verifyPrivateKey() default true;

    /**
     * 访问用户公钥（默认从请求头获取“accessKey”作为用户公钥）。<br>
     * 支持普通参数和 SpEl 表达式动态参数。<br>
     */
    String publicKey() default "";

    /**
     * 访问用户私钥（默认从请求头获取“secretKey”作为用户私钥）。<br>
     * 支持普通参数和 SpEl 表达式动态参数。<br>
     */
    String privateKey() default "";

}
