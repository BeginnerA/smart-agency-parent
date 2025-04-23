package tt.smart.agency.cache.exception;

/**
 * <p>
 * 缓存策略异常
 * </p>
 *
 * @author MC_Yang
 * @version V1.0
 **/
public class CacheStrategyException extends RuntimeException {
    public String code;
    public String message;
    public Throwable cause;

    public CacheStrategyException(String message) {
        super(message);
        this.message = message;
    }

    public CacheStrategyException(String code, String message) {
        super("[" + code + "] " + message);
        this.message = message;
        this.code = code;
    }

    public CacheStrategyException(String message, Throwable cause) {
        super(message, cause);
        this.message = message;
        this.cause = cause;
    }

    public CacheStrategyException(String code, String message, Throwable cause) {
        super("[" + code + "] " + message, cause);
        this.message = message;
        this.code = code;
        this.cause = cause;
    }

}
