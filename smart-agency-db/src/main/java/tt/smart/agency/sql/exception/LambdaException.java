package tt.smart.agency.sql.exception;

/**
 * <p>
 * lambda 异常
 * </p>
 *
 * @author MC_Yang
 * @version V1.0
 **/
public class LambdaException extends RuntimeException {

    public LambdaException() {
    }

    public LambdaException(String message) {
        super(message);
    }

    public LambdaException(String message, Throwable cause) {
        super(message, cause);
    }

    public LambdaException(Throwable cause) {
        super(cause);
    }

    public LambdaException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

}
