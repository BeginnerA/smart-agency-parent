package tt.smart.agency.message.exception;

/**
 * <p>
 * 钉钉异常
 * </p>
 *
 * @author YangMC
 * @version V1.0
 **/
public class DdMessageException extends MessageException {
    public String requestId;

    public DdMessageException(String message) {
        super(message);
    }

    public DdMessageException(String code, String message) {
        super(code, message);
    }

    public DdMessageException(String message, Throwable cause) {
        super(message, cause);
    }

    public DdMessageException(String code, String message, Throwable cause) {
        super(code, message, cause);
    }

    public DdMessageException(String code, String message, String requestId, Throwable cause) {
        super(code, message, cause);
        this.message = message;
        this.code = code;
        this.requestId = requestId;
        this.cause = cause;
    }
}
