package tt.smart.agency.message.exception;

/**
 * <p>
 * 微信异常
 * </p>
 *
 * @author YangMC
 * @version V1.0
 **/
public class WxMessageException extends MessageException {
    public String requestId;

    public WxMessageException(String message) {
        super(message);
    }

    public WxMessageException(String code, String message) {
        super(code, message);
    }

    public WxMessageException(String message, Throwable cause) {
        super(message, cause);
    }

    public WxMessageException(String code, String message, Throwable cause) {
        super(code, message, cause);
    }

    public WxMessageException(String code, String message, String requestId, Throwable cause) {
        super(code, message, cause);
        this.message = message;
        this.code = code;
        this.requestId = requestId;
        this.cause = cause;
    }
}
