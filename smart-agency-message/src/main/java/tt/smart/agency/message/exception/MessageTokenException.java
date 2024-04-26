package tt.smart.agency.message.exception;

/**
 * <p>
 * 消息访问令牌异常
 * </p>
 *
 * @author YangMC
 * @version V1.0
 **/
public class MessageTokenException extends MessageException {
    public String requestId;

    public MessageTokenException(String message) {
        super(message);
    }

    public MessageTokenException(String code, String message) {
        super(code, message);
    }

    public MessageTokenException(String message, Throwable cause) {
        super(message, cause);
    }

    public MessageTokenException(String code, String message, Throwable cause) {
        super(code, message, cause);
    }

    public MessageTokenException(String code, String message, String requestId, Throwable cause) {
        super(code, message, cause);
        this.message = message;
        this.code = code;
        this.requestId = requestId;
        this.cause = cause;
    }
}