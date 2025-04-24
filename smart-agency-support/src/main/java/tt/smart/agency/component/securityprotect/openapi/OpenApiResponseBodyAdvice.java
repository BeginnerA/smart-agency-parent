package tt.smart.agency.component.securityprotect.openapi;

import cn.hutool.core.bean.BeanUtil;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;
import tt.smart.agency.component.securityprotect.openapi.annotation.OpenApiAuth;
import tt.smart.agency.component.securityprotect.openapi.config.properties.OpenApiAuthProperties;
import tt.smart.agency.component.securityprotect.openapi.enums.StatusEnum;
import tt.smart.agency.component.securityprotect.openapi.handler.AuthHandler;
import tt.smart.agency.component.securityprotect.openapi.handler.EncryptContext;
import tt.smart.agency.component.securityprotect.openapi.handler.EncryptorManager;
import tt.smart.agency.component.securityprotect.openapi.tools.OpenApiAuthTools;

import java.util.Map;
import java.util.Objects;

/**
 * <p>
 * 返回值加密处理
 * </p>
 *
 * @author MC_Yang
 * @version V1.0
 **/
@Slf4j
@ControllerAdvice
public class OpenApiResponseBodyAdvice implements ResponseBodyAdvice<Object> {

    /**
     * 标准常用结果数据键
     */
    private static final String[] RESULT_DATA_KEY = new String[]{"data", "result"};

    @Resource
    private OpenApiAuthProperties openApiAuthProperties;

    @Resource
    private EncryptorManager encryptorManager;

    @Override
    public boolean supports(@NonNull MethodParameter returnType,
                            @NonNull Class<? extends HttpMessageConverter<?>> converterType) {
        StatusEnum responseEncrypt = openApiAuthProperties.getResponseEncrypt();
        if (StatusEnum.ENABLE.equals(responseEncrypt)) {
            OpenApiAuth openApiAuth = OpenApiAuthTools.getAnnotation(returnType);
            if (openApiAuth != null) {
                return openApiAuth.responseEncrypt();
            }
        }
        return false;
    }

    @Override
    public Object beforeBodyWrite(Object body, @NonNull MethodParameter returnType,
                                  @NonNull MediaType selectedContentType,
                                  @NonNull Class<? extends HttpMessageConverter<?>> selectedConverterType,
                                  @NonNull ServerHttpRequest request, @NonNull ServerHttpResponse response) {
        if (body != null) {
            for (String key : RESULT_DATA_KEY) {
                Object resultData;
                if (body instanceof Map) {
                    resultData = ((Map<?, ?>) body).get(key);
                } else {
                    resultData = BeanUtil.getFieldValue(body, key);
                }
                if (resultData != null) {
                    HttpHeaders headers = request.getHeaders();
                    String publicKey = Objects.requireNonNull(headers.get(AuthHandler.ACCESS_KEY_PARAM_NAME)).getFirst();
                    EncryptContext encryptContext = openApiAuthProperties.encryptContext(publicKey);
                    Object encrypt = encryptorManager.encrypt(resultData, encryptContext);
                    BeanUtil.setFieldValue(body, key, encrypt);
                    return body;
                }
            }
        }
        return body;
    }

}
