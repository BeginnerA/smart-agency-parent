package tt.smart.agency.sql.exception;

/**
 * <p>
 * SQL 构造器异常
 * </p>
 *
 * @author MC_Yang
 * @version V1.0
 **/
public class SqlBuilderException extends RuntimeException {

    public SqlBuilderException() {
    }

    public SqlBuilderException(String message) {
        super(message);
    }

    public SqlBuilderException(String message, Throwable cause) {
        super(message, cause);
    }

    public SqlBuilderException(Throwable cause) {
        super(cause);
    }

    public SqlBuilderException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

}
