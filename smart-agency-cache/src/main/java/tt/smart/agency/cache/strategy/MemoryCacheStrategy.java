package tt.smart.agency.cache.strategy;

import cn.hutool.cache.CacheUtil;
import cn.hutool.cache.impl.TimedCache;

/**
 * <p>
 * 内存缓存策略
 * </p>
 *
 * @param <K> 键类型
 * @param <V> 值类型
 * @author MC_Yang
 * @version V1.0
 **/
public class MemoryCacheStrategy<K, V> extends AbstractCacheStrategy<K, V> {

    /**
     * 定时缓存，对被缓存的对象定义一个过期时间，当对象超过过期时间会被清理。此缓存没有容量限制，对象只有在过期后才会被移除。
     */
    protected final TimedCache<K, V> TIMED_CACHE = CacheUtil.newTimedCache(4);

    @Override
    public void put(K key, V value) {
        TIMED_CACHE.put(key, value);
    }

    @Override
    public void put(K key, V value, long timeout) {
        TIMED_CACHE.put(key, value, timeout);
    }

    @Override
    public V get(K key) {
        return TIMED_CACHE.get(key);
    }

    @Override
    public V get(K key, boolean isUpdateLastAccess) {
        return TIMED_CACHE.get(key, isUpdateLastAccess);
    }

    @Override
    public void remove(K key) {
        TIMED_CACHE.remove(key);
    }

    @Override
    public boolean containsKey(K key) {
        return TIMED_CACHE.containsKey(key);
    }

}