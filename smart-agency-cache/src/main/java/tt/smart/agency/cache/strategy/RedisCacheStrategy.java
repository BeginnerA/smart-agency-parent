package tt.smart.agency.cache.strategy;

import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;

import java.util.concurrent.TimeUnit;

/**
 * <p>
 * Redis 缓存策略
 * </p>
 *
 * @author YangMC
 * @version V1.0
 **/
public class RedisCacheStrategy<K, V> extends AbstractCacheStrategy<K, V> {

    private final RedisTemplate<K, V> redisTemplate;

    public RedisCacheStrategy(LettuceConnectionFactory lettuceConnectionFactory) {
        this.redisTemplate = createRedisTemplate(lettuceConnectionFactory);
    }

    /**
     * 创建 RedisTemplate
     *
     * @param lettuceConnectionFactory 链接工厂
     * @return RedisTemplate
     */
    private RedisTemplate<K, V> createRedisTemplate(LettuceConnectionFactory lettuceConnectionFactory) {
        RedisTemplate<K, V> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(lettuceConnectionFactory);
        // key 采用 String 的序列化方式
        redisTemplate.setKeySerializer(RedisSerializer.string());
        // hash 的 key 也采用 String 的序列化方式
        redisTemplate.setHashKeySerializer(RedisSerializer.string());
        // value 序列化方式采用 jackson
        redisTemplate.setValueSerializer(RedisSerializer.json());
        // hash 的 value 序列化方式采用 jackson
        redisTemplate.setHashValueSerializer(RedisSerializer.json());
        redisTemplate.afterPropertiesSet();
        return redisTemplate;
    }

    @Override
    public void put(K key, V value) {
        redisTemplate.opsForValue().set(key, value);
    }

    @Override
    public void put(K key, V value, long timeout) {
        redisTemplate.opsForValue().set(key, value, timeout, TimeUnit.SECONDS);
    }

    @Override
    public V get(K key, boolean isUpdateLastAccess) {
        return redisTemplate.opsForValue().get(key);
    }

    @Override
    public void remove(K key) {
        redisTemplate.delete(key);
    }

    @Override
    public boolean containsKey(K key) {
        return Boolean.TRUE.equals(redisTemplate.hasKey(key));
    }
}