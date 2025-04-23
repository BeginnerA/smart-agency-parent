package tt.smart.agency.message.exception;

/**
 * <p>
 * 消息推送异常
 * </p>
 *
 * @author MC_Yang
 * @version V1.0
 **/
public class MessageException extends RuntimeException {
    public String code;
    public String message;
    public Throwable cause;

    public MessageException(String message) {
        super(message);
        this.message = message;
    }

    public MessageException(String code, String message) {
        super("[" + code + "] " + message);
        this.message = message;
        this.code = code;
    }

    public MessageException(String message, Throwable cause) {
        super(message, cause);
        this.message = message;
        this.cause = cause;
    }

    public MessageException(String code, String message, Throwable cause) {
        super("[" + code + "] " + message, cause);
        this.message = message;
        this.code = code;
        this.cause = cause;
    }

}
