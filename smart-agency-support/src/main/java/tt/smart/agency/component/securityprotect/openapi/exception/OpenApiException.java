package tt.smart.agency.component.securityprotect.openapi.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.io.Serial;

/**
 * <p>
 * 开放 API 异常
 * </p>
 *
 * @author MC_Yang
 * @version V1.0
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class OpenApiException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 错误码
     */
    private Integer code = HttpStatus.BAD_REQUEST.value();

    public OpenApiException(String message) {
        super(message);
    }

    public OpenApiException(Integer code, String message) {
        super(message);
        this.code = code;
    }

    public OpenApiException(String message, Throwable cause) {
        super(message, cause);
    }

    public OpenApiException(Integer code, String message, Throwable cause) {
        super(message, cause);
        this.code = code;
    }

}
