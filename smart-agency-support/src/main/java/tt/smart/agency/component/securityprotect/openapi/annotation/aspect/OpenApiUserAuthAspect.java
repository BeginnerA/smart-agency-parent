package tt.smart.agency.component.securityprotect.openapi.annotation.aspect;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.util.StrUtil;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.DefaultParameterNameDiscoverer;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.Expression;
import org.springframework.expression.spel.SpelEvaluationException;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import tt.smart.agency.component.securityprotect.openapi.annotation.OpenApiUserAuth;
import tt.smart.agency.component.securityprotect.openapi.config.properties.OpenApiAuthProperties;
import tt.smart.agency.component.securityprotect.openapi.enums.StatusEnum;
import tt.smart.agency.component.securityprotect.openapi.exception.OpenApiException;
import tt.smart.agency.component.securityprotect.openapi.handler.AuthHandler;
import tt.smart.agency.component.securityprotect.openapi.handler.AuthenticationContext;
import tt.smart.agency.component.securityprotect.openapi.listener.OpenApiAuthListenerManager;

/**
 * <p>
 * 开放 API 用户认证切面
 * </p>
 *
 * @author MC_Yang
 * @version V1.0
 **/
@Aspect
@RequiredArgsConstructor
public class OpenApiUserAuthAspect {

    private final SpelExpressionParser EXPRESSION_PARSER = new SpelExpressionParser();
    private final DefaultParameterNameDiscoverer PARAMETER_NAME_DISCOVERER = new DefaultParameterNameDiscoverer();

    private final OpenApiAuthProperties openApiAuthProperties;
    private final AuthHandler authHandler;

    @Pointcut("@annotation(tt.smart.agency.component.securityprotect.openapi.annotation.OpenApiUserAuth)")
    public void pointCut() {
    }

    @SneakyThrows
    @Around(value = "pointCut()")
    public Object round(ProceedingJoinPoint joinPoint) {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        StatusEnum requestAuth = openApiAuthProperties.getRequestAuth();
        OpenApiUserAuth openApiUserAuth = signature.getMethod().getAnnotation(OpenApiUserAuth.class);
        if (openApiUserAuth != null && StatusEnum.ENABLE.equals(requestAuth)) {
            String accessKey = getPublicKey(openApiUserAuth.publicKey(), joinPoint, attributes);
            if (accessKey != null) {
                String secretKey = authHandler.getUserSecretKey(accessKey);
                Boolean privateKeyCorrect = null;
                if (openApiUserAuth.verifyPrivateKey() && secretKey != null) {
                    privateKeyCorrect = secretKey.equals(getPrivateKey(openApiUserAuth.privateKey(), joinPoint, attributes));
                }
                if (StrUtil.isBlank(secretKey) || (privateKeyCorrect != null && !privateKeyCorrect)) {
                    throw new OpenApiException("开放 API 请求认证失败：认证用户不存在或密钥错误");
                }
                AuthenticationContext authenticationContext = new AuthenticationContext();
                boolean enhance = OpenApiAuthListenerManager.executeEnhanceAuthSecure(authenticationContext);
                if (!enhance) {
                    throw new OpenApiException("开放 API 请求认证失败：自定义认证未通过");
                }
            } else {
                throw new OpenApiException("开放 API 存在非法请求");
            }
        }
        return joinPoint.proceed();
    }

    /**
     * 获取访问公钥
     *
     * @param publicKey  publicKey
     * @param joinPoint  ProceedingJoinPoint
     * @param attributes ServletRequestAttributes
     * @return 访问公钥
     */
    private String getPublicKey(String publicKey, ProceedingJoinPoint joinPoint, ServletRequestAttributes attributes) {
        if (StrUtil.isBlank(publicKey)) {
            return attributes.getRequest().getHeader(AuthHandler.ACCESS_KEY_PARAM_NAME);
        } else {
            return Convert.toStr(generateKeyBySpEl(publicKey, joinPoint));
        }
    }

    /**
     * 获取访问私钥
     *
     * @param privateKey privateKey
     * @param joinPoint  ProceedingJoinPoint
     * @param attributes ServletRequestAttributes
     * @return 访问公钥
     */
    private String getPrivateKey(String privateKey, ProceedingJoinPoint joinPoint, ServletRequestAttributes attributes) {
        if (StrUtil.isBlank(privateKey)) {
            return attributes.getRequest().getHeader(AuthHandler.SECRET_KEY_PARAM_NAME);
        } else {
            return Convert.toStr(generateKeyBySpEl(privateKey, joinPoint));
        }
    }

    /**
     * 解析自定义参数
     *
     * @param key SpEl 表达式
     * @param pjp ProceedingJoinPoint
     * @return 结果
     */
    private Object generateKeyBySpEl(String key, ProceedingJoinPoint pjp) {
        if (key == null || key.isEmpty()) {
            return null;
        }
        EvaluationContext context = new StandardEvaluationContext();
        MethodSignature methodSignature = (MethodSignature) pjp.getSignature();
        Object[] args = pjp.getArgs();
        String[] paramNames = PARAMETER_NAME_DISCOVERER.getParameterNames(methodSignature.getMethod());
        assert paramNames != null;
        for (int i = 0; i < args.length; i++) {
            context.setVariable(paramNames[i], args[i]);
        }
        Expression expression = EXPRESSION_PARSER.parseExpression(key);
        try {
            return expression.getValue(context);
        } catch (SpelEvaluationException e) {
            return key;
        }
    }

}
