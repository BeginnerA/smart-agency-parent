package tt.smart.agency.im.exception;

/**
 * <p>
 * 即时通讯异常
 * </p>
 *
 * @author YangMC
 * @version V1.0
 **/
public class ImException extends RuntimeException {
    public String code;
    public String message;
    public Throwable cause;

    public ImException(String message) {
        super(message);
        this.message = message;
    }

    public ImException(String code, String message) {
        super("[" + code + "] " + message);
        this.message = message;
        this.code = code;
    }

    public ImException(String message, Throwable cause) {
        super(message, cause);
        this.message = message;
        this.cause = cause;
    }

    public ImException(String code, String message, Throwable cause) {
        super("[" + code + "] " + message, cause);
        this.message = message;
        this.code = code;
        this.cause = cause;
    }

}
