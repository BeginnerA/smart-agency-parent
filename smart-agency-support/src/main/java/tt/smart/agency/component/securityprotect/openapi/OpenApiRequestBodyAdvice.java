package tt.smart.agency.component.securityprotect.openapi;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.io.IoUtil;
import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.alibaba.fastjson2.TypeReference;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.RequestBodyAdviceAdapter;
import tt.smart.agency.component.securityprotect.openapi.annotation.OpenApiAuth;
import tt.smart.agency.component.securityprotect.openapi.config.properties.OpenApiAuthProperties;
import tt.smart.agency.component.securityprotect.openapi.enums.StatusEnum;
import tt.smart.agency.component.securityprotect.openapi.exception.OpenApiException;
import tt.smart.agency.component.securityprotect.openapi.handler.AuthHandler;
import tt.smart.agency.component.securityprotect.openapi.handler.AuthenticationContext;
import tt.smart.agency.component.securityprotect.openapi.handler.EncryptContext;
import tt.smart.agency.component.securityprotect.openapi.handler.EncryptorManager;
import tt.smart.agency.component.securityprotect.openapi.listener.OpenApiAuthListenerManager;
import tt.smart.agency.component.securityprotect.openapi.tools.OpenApiAuthTools;

import java.io.InputStream;
import java.lang.reflect.Type;
import java.util.Objects;

/**
 * <p>
 * 请求参数解密处理
 * 仅对使用了 @RquestBody 注解的参数生效
 * </p>
 *
 * @author MC_Yang
 * @version V1.0
 **/
@Slf4j
@ControllerAdvice
public class OpenApiRequestBodyAdvice extends RequestBodyAdviceAdapter {

    @Resource
    private OpenApiAuthProperties openApiAuthProperties;

    @Resource
    private AuthHandler authHandler;

    @Resource
    private EncryptorManager encryptorManager;

    @Override
    public boolean supports(@NonNull MethodParameter methodParameter,
                            @NonNull Type type,
                            @NonNull Class<? extends HttpMessageConverter<?>> aClass) {
        return true;
    }

    @NonNull
    @Override
    public HttpInputMessage beforeBodyRead(@NonNull HttpInputMessage httpInputMessage,
                                           @NonNull MethodParameter methodParameter,
                                           @NonNull Type type,
                                           @NonNull Class<? extends HttpMessageConverter<?>> aClass) {
        httpInputMessage = requestDecrypt(httpInputMessage, methodParameter);
        // 如果请求参数需要加入签名在此做认证处理
        httpInputMessage = requestAuth(httpInputMessage, methodParameter);
        return httpInputMessage;
    }

    private HttpInputMessage requestDecrypt(HttpInputMessage httpInputMessage, MethodParameter methodParameter) {
        StatusEnum requestDecrypt = openApiAuthProperties.getRequestDecrypt();
        if (StatusEnum.ENABLE.equals(requestDecrypt)) {
            OpenApiAuth openApiAuth = OpenApiAuthTools.getAnnotation(methodParameter);
            if (openApiAuth != null && openApiAuth.requestDecrypt()) {
                try {
                    HttpHeaders headers = httpInputMessage.getHeaders();
                    String publicKey = Objects.requireNonNull(headers.get(AuthHandler.ACCESS_KEY_PARAM_NAME)).getFirst();
                    EncryptContext encryptContext = openApiAuthProperties.encryptContext(publicKey);
                    InputStream decrypt = encryptorManager.decrypt(httpInputMessage.getBody(), encryptContext);
                    return new OpenApiHttpInputMessage(httpInputMessage, decrypt);
                } catch (Exception e) {
                    log.error("请求参数解密失败：检查参数加密算法是否和配置一致");
                    throw new OpenApiException("开放 API 存在非法请求", e);
                }
            }
        }
        return httpInputMessage;
    }

    private HttpInputMessage requestAuth(HttpInputMessage httpInputMessage, MethodParameter methodParameter) {
        StatusEnum requestAuth = openApiAuthProperties.getRequestAuth();
        if (StatusEnum.ENABLE.equals(requestAuth)) {
            OpenApiAuth openApiAuth = OpenApiAuthTools.getAnnotation(methodParameter);
            if (openApiAuth != null && openApiAuth.requestAuth() && openApiAuth.signWithParam()) {
                try {
                    InputStream body = httpInputMessage.getBody();
                    String read = IoUtil.readUtf8(body);
                    JSONObject jsonObject = JSON.parseObject(read);

                    AuthenticationContext authenticationContext = getAuthenticationParam(httpInputMessage.getHeaders());
                    boolean auth = authHandler.auth(authenticationContext, jsonObject.to(new TypeReference<>() {}));
                    if (auth) {
                        boolean enhance = OpenApiAuthListenerManager.executeEnhanceAuthSecure(authenticationContext);
                        if (!enhance) {
                            throw new IllegalArgumentException("自定义认证未通过");
                        }
                    }

                    jsonObject.remove(AuthHandler.ACCESS_KEY_PARAM_NAME);
                    jsonObject.remove(AuthHandler.TIMESTAMP_PARAM_NAME);
                    jsonObject.remove(AuthHandler.NONCE_PARAM_NAME);
                    jsonObject.remove(AuthHandler.SIGN_PARAM_NAME);
                    return new OpenApiHttpInputMessage(httpInputMessage, IoUtil.toUtf8Stream(jsonObject.toJSONString()));
                } catch (Exception e) {
                    throw new OpenApiException("开放 API 存在非法请求：" + e.getMessage(), e);
                }
            }
        }
        return httpInputMessage;
    }

    /**
     * 获取认证参数
     *
     * @param headers 请求头
     * @return 认证参数
     */
    private AuthenticationContext getAuthenticationParam(HttpHeaders headers) {
        AuthenticationContext authenticationContext = new AuthenticationContext();
        authenticationContext.setAccessKey(Objects.requireNonNull(headers.get(AuthHandler.ACCESS_KEY_PARAM_NAME)).getFirst());
        authenticationContext.setTimestamp(Convert.toLong(Objects.requireNonNull(headers.get(AuthHandler.TIMESTAMP_PARAM_NAME)).getFirst()));
        authenticationContext.setNonce(Objects.requireNonNull(headers.get(AuthHandler.NONCE_PARAM_NAME)).getFirst());
        authenticationContext.setSign(Objects.requireNonNull(headers.get(AuthHandler.SIGN_PARAM_NAME)).getFirst());
        authenticationContext.setSecretKey(authHandler.getUserSecretKey(authenticationContext.getAccessKey()));
        authenticationContext.setOpenApiAuthConfig(openApiAuthProperties);
        return authenticationContext;
    }

}

