package tt.smart.agency.cache.exception;

/**
 * <p>
 * 缓存策略异常
 * </p>
 *
 * @author YangMC
 * @version V1.0
 **/
public class CacheStrategyException extends RuntimeException {
    public String code;
    public String message;
    public String requestId;

    public CacheStrategyException(String message) {
        super(message);
        this.message = message;
    }

    public CacheStrategyException(String code, String message) {
        super("[" + code + "] " + message);
        this.message = message;
        this.code = code;
    }

    public CacheStrategyException(String code, String message, String requestId) {
        super("[" + code + "] " + message);
        this.message = message;
        this.code = code;
        this.code = requestId;
    }
}
