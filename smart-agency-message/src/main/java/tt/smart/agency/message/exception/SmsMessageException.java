package tt.smart.agency.message.exception;

/**
 * <p>
 * 短信异常
 * </p>
 *
 * @author YangMC
 * @version V1.0
 **/
public class SmsMessageException extends MessageException {
    public String requestId;

    public SmsMessageException(String message) {
        super(message);
    }

    public SmsMessageException(String code, String message) {
        super(code, message);
    }

    public SmsMessageException(String message, Throwable cause) {
        super(message, cause);
    }

    public SmsMessageException(String code, String message, Throwable cause) {
        super(code, message, cause);
    }

    public SmsMessageException(String code, String message, String requestId, Throwable cause) {
        super(code, message, cause);
        this.message = message;
        this.code = code;
        this.requestId = requestId;
        this.cause = cause;
    }
}
