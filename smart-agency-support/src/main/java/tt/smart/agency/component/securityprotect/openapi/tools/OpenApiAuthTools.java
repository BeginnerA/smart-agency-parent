package tt.smart.agency.component.securityprotect.openapi.tools;

import tt.smart.agency.component.securityprotect.openapi.annotation.OpenApiAuth;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.core.MethodParameter;
import org.springframework.core.annotation.AnnotatedMethod;
import org.springframework.web.bind.annotation.RequestBody;

import java.lang.annotation.Annotation;
import java.util.Objects;

/**
 * <p>
 * 开放 API 认证工具
 * </p>
 *
 * @author MC_Yang
 * @version V1.0
 **/
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class OpenApiAuthTools {

    /**
     * 获取注解
     *
     * @param parameter 方法参数
     * @return 注解
     */
    public static OpenApiAuth getAnnotation(MethodParameter parameter) {
        return getAnnotation(parameter, OpenApiAuth.class);
    }

    /**
     * 获取注解
     *
     * @param method 带注释的方法
     * @return 注解
     */
    public static OpenApiAuth getAnnotation(AnnotatedMethod method) {
        return getAnnotation(method, OpenApiAuth.class);
    }

    /**
     * 包含请求正文注释
     *
     * @param parameter 方法参数
     * @return 结果
     */
    public static boolean containsRequestBodyAnnotation(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(RequestBody.class);
    }

    /**
     * 包含请求正文注释
     *
     * @param method 带注释的方法
     * @return 结果
     */
    public static boolean containsRequestBodyAnnotation(AnnotatedMethod method) {
        MethodParameter[] parameters = method.getMethodParameters();
        for (MethodParameter parameter : parameters) {
            if (containsRequestBodyAnnotation(parameter)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 获取注解
     *
     * @param method 带注释的方法
     * @param clazz  注解类
     * @return 注解
     */
    public static <T extends Annotation> T getAnnotation(AnnotatedMethod method, Class<T> clazz) {
        // 确保 method 和 clazz 都不为 null
        Objects.requireNonNull(method, "AnnotatedMethod 不能为空");
        Objects.requireNonNull(clazz, "注释类不能为空");
        return method.getMethod().getAnnotation(clazz);
    }

    /**
     * 获取注解
     *
     * @param parameter 方法参数
     * @param clazz     注解类
     * @return 注解
     */
    public static <T extends Annotation> T getAnnotation(MethodParameter parameter, Class<T> clazz) {
        // 确保 parameter 和 clazz 都不为 null
        Objects.requireNonNull(parameter, "MethodParameter 不能为空");
        Objects.requireNonNull(clazz, "注释类不能为空");

        // 尝试从方法上获取注解
        T annotation = Objects.requireNonNull(parameter.getMethod()).getAnnotation(clazz);
        if (annotation == null) {
            // 如果方法上没有找到，尝试从包含该方法的类上获取注解
            annotation = parameter.getContainingClass().getAnnotation(clazz);
        }
        return annotation;
    }

}
