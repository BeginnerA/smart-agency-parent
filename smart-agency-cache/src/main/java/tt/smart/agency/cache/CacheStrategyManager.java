package tt.smart.agency.cache;

import tt.smart.agency.cache.strategy.CacheStrategy;

/**
 * <p>
 * 缓存管理器
 * </p>
 *
 * @author MC_Yang
 * @version V1.0
 **/
@SuppressWarnings({"unchecked", "rawtypes"})
public class CacheStrategyManager {

    private static CacheStrategy cacheStrategy;

    /**
     * 设置缓存策略
     *
     * @param cacheStrategy 缓存策略
     */
    public static void setCacheStrategy(CacheStrategy cacheStrategy) {
        CacheStrategyManager.cacheStrategy = cacheStrategy;
    }

    /**
     * 将对象加入到缓存，使用默认失效时长
     *
     * @param key   键
     * @param value 缓存的对象
     */
    public static void put(String key, Object value) {
        cacheStrategy.put(key, value);
    }

    /**
     * 将对象加入到缓存，使用指定失效时长
     *
     * @param key     键
     * @param value   缓存的对象
     * @param timeout 失效时长，单位毫秒
     */
    public static void put(String key, Object value, long timeout) {
        cacheStrategy.put(key, value, timeout);
    }

    /**
     * 从缓存中获得对象，当对象不在缓存中或已经过期返回{@code null}
     * <p>
     * 调用此方法时，会检查上次调用时间，如果与当前时间差值大于超时时间返回{@code null}，否则返回值。
     * <p>
     * 每次调用此方法会刷新最后访问时间，也就是说会重新计算超时时间。
     *
     * @param key 键
     * @return 键对应的对象
     */
    public static Object get(String key) {
        return cacheStrategy.get(key);
    }

    /**
     * 从缓存中获得对象，当对象不在缓存中或已经过期返回{@code null}
     * <p>
     * 调用此方法时，会检查上次调用时间，如果与当前时间差值大于超时时间返回{@code null}，否则返回值。
     * <p>
     * 每次调用此方法会可选是否刷新最后访问时间，{@code true}表示会重新计算超时时间。
     *
     * @param key                键
     * @param isUpdateLastAccess 是否更新最后访问时间，即重新计算超时时间。
     * @return 键对应的对象
     */
    public static Object get(String key, boolean isUpdateLastAccess) {
        return cacheStrategy.get(key, isUpdateLastAccess);
    }

    /**
     * 从缓存中移除对象
     *
     * @param key 键
     */
    public static void remove(String key) {
        cacheStrategy.remove(key);
    }

    /**
     * 是否包含 key
     *
     * @param key KEY
     * @return 是否包含key
     */
    public static boolean containsKey(String key) {
        return cacheStrategy.containsKey(key);
    }
}