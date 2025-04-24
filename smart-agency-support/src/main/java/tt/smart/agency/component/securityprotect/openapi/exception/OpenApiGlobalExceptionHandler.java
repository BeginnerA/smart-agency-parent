package tt.smart.agency.component.securityprotect.openapi.exception;

import com.alibaba.fastjson2.JSON;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.SneakyThrows;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * <p>
 * 开放 API 异常全局拦截器
 * </p>
 *
 * @author MC_Yang
 * @version V1.0
 **/
@ControllerAdvice
public class OpenApiGlobalExceptionHandler {

    @SneakyThrows
    @ExceptionHandler(OpenApiException.class)
    public void handleRuntimeException(OpenApiException ex, HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> data = new LinkedHashMap<>();
        data.put("timestamp", new Date());
        data.put("code", ex.getCode());
        data.put("msg", ex.getMessage());
        data.put("path", request.getRequestURI());
        response.setContentType("application/json;charset=UTF-8");
        response.setStatus(ex.getCode());
        response.getWriter().write(JSON.toJSONString(data));
        response.getWriter().flush();
    }

}
