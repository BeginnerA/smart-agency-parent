package tt.smart.agency.component.securityprotect.openapi;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.util.StrUtil;
import tt.smart.agency.component.securityprotect.openapi.annotation.OpenApiAuth;
import tt.smart.agency.component.securityprotect.openapi.config.properties.OpenApiAuthProperties;
import tt.smart.agency.component.securityprotect.openapi.enums.StatusEnum;
import tt.smart.agency.component.securityprotect.openapi.exception.OpenApiException;
import tt.smart.agency.component.securityprotect.openapi.handler.AuthHandler;
import tt.smart.agency.component.securityprotect.openapi.handler.AuthenticationContext;
import tt.smart.agency.component.securityprotect.openapi.listener.OpenApiAuthListenerManager;
import tt.smart.agency.component.securityprotect.openapi.tools.OpenApiAuthTools;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

/**
 * <p>
 * 开放 API 认证拦截器
 * </p>
 *
 * @author MC_Yang
 * @version V1.0
 **/
@Slf4j
@ControllerAdvice
@RequiredArgsConstructor
public class OpenApiAuthInterceptor implements HandlerInterceptor {

    private final OpenApiAuthProperties openApiAuthProperties;
    private final AuthHandler authHandler;

    @Override
    public boolean preHandle(@NonNull HttpServletRequest request,
                             @NonNull HttpServletResponse response,
                             @NonNull Object handler) {
        StatusEnum requestAuth = openApiAuthProperties.getRequestAuth();
        if (StatusEnum.ENABLE.equals(requestAuth)) {
            if (handler instanceof HandlerMethod handlerMethod) {
                OpenApiAuth openApiAuth = OpenApiAuthTools.getAnnotation(handlerMethod);
                if (openApiAuth != null && openApiAuth.requestAuth()) {
                    requestAuth(request, response, handlerMethod);
                }
            }
        }
        return true;
    }

    private void requestAuth(HttpServletRequest request, HttpServletResponse response, HandlerMethod handlerMethod) {
        AuthenticationContext authenticationContext = assertAuthenticationParam(request, response);
        if (StatusEnum.ENABLE.equals(openApiAuthProperties.getSignWithParam())) {
            OpenApiAuth openApiAuth = OpenApiAuthTools.getAnnotation(handlerMethod);
            if (!openApiAuth.signWithParam()) {
                try {
                    authenticationContext.setOpenApiAuthConfig(openApiAuthProperties);
                    boolean auth = authHandler.auth(authenticationContext, null);
                    if (auth) {
                        boolean enhance = OpenApiAuthListenerManager.executeEnhanceAuthSecure(authenticationContext);
                        if (!enhance) {
                            throw new IllegalArgumentException("自定义认证未通过");
                        }
                    }
                } catch (Exception e) {
                    throw new OpenApiException("开放API存在非法请求：" + e.getMessage(), e);
                }
            } else {
                // 如果请求参数需要加入签名在此不做认证处理，由 OpenApiRequestBodyAdvice 统一处理
                if (!OpenApiAuthTools.containsRequestBodyAnnotation(handlerMethod)) {
                    log.error("OpenApiAuth 解密 Controller 方法参数必须是 RequestBody 注释参数");
                    throw new OpenApiException("开放 API 存在非法请求参数");
                }
            }
        }
    }

    /**
     * 断言认证参数
     *
     * @param request  HttpServletRequest
     * @param response HttpServletResponse
     * @return 认证参数
     */
    private AuthenticationContext assertAuthenticationParam(HttpServletRequest request, HttpServletResponse response) {
        AuthenticationContext authenticationContext = new AuthenticationContext();
        String temp = "开放 API 请求失败：【{}】是必须的";
        String accessKey = request.getHeader(AuthHandler.ACCESS_KEY_PARAM_NAME);
        if (StrUtil.isBlank(accessKey)) {
            throw new OpenApiException(StrUtil.format(temp, AuthHandler.ACCESS_KEY_PARAM_NAME));
        }
        authenticationContext.setAccessKey(accessKey);

        String timestamp = request.getHeader(AuthHandler.TIMESTAMP_PARAM_NAME);
        if (StrUtil.isBlank(timestamp)) {
            throw new OpenApiException(StrUtil.format(temp, AuthHandler.TIMESTAMP_PARAM_NAME));
        }
        authenticationContext.setTimestamp(Convert.toLong(timestamp));

        String nonce = request.getHeader(AuthHandler.NONCE_PARAM_NAME);
        if (StrUtil.isBlank(nonce)) {
            throw new OpenApiException(StrUtil.format(temp, AuthHandler.NONCE_PARAM_NAME));
        }
        authenticationContext.setNonce(nonce);

        String sign = request.getHeader(AuthHandler.SIGN_PARAM_NAME);
        if (StrUtil.isBlank(sign)) {
            throw new OpenApiException(StrUtil.format(temp, AuthHandler.SIGN_PARAM_NAME));
        }
        authenticationContext.setSign(sign);

        String secretKey = authHandler.getUserSecretKey(authenticationContext.getAccessKey());
        if (StrUtil.isBlank(secretKey)) {
            throw new OpenApiException(StrUtil.format("开放 API 请求认证失败：不是合法的【{}】", AuthHandler.ACCESS_KEY_PARAM_NAME));
        }
        authenticationContext.setSecretKey(secretKey);
        return authenticationContext;
    }

    @Override
    public void postHandle(@NonNull HttpServletRequest request,
                           @NonNull HttpServletResponse response,
                           @NonNull Object handler,
                           @Nullable ModelAndView modelAndView) throws Exception {
        HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
    }

    @Override
    public void afterCompletion(@NonNull HttpServletRequest request,
                                @NonNull HttpServletResponse response,
                                @NonNull Object handler,
                                @Nullable Exception ex) throws Exception {
        HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
    }

}
