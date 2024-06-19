package tt.smart.agency.cache.strategy;

import java.io.Serializable;

/**
 * <p>
 * 高速缓存策略
 * </p>
 *
 * @param <K> 键类型
 * @param <V> 值类型
 * @author YangMC
 * @version V1.0
 **/
public interface CacheStrategy<K, V> extends Serializable {

    /**
     * 将对象加入到缓存，使用默认失效时长
     *
     * @param key   键
     * @param value 缓存的对象
     */
    void put(K key, V value);

    /**
     * 将对象加入到缓存，使用指定失效时长
     *
     * @param key     键
     * @param value   缓存的对象
     * @param timeout 失效时长（秒）
     */
    void put(K key, V value, long timeout);

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
    default V get(K key) {
        return get(key, true);
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
    V get(K key, boolean isUpdateLastAccess);

    /**
     * 从缓存中移除对象
     *
     * @param key 键
     */
    void remove(K key);

    /**
     * 是否包含 key
     *
     * @param key KEY
     * @return 是否包含key
     */
    boolean containsKey(K key);

}