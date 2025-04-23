package tt.smart.agency.cache.config.properties;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;

/**
 * <p>
 * Redis 缓存策略配置属性
 * </p>
 *
 * @author MC_Yang
 * @version V1.0
 **/
@Data
@Slf4j
@SuperBuilder
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class RedisCacheStrategyProperties extends BaseCacheStrategyProperties {

    /**
     * 链接工厂
     */
    private LettuceConnectionFactory lettuceConnectionFactory;

    @Override
    public boolean checkStartCondition() {
        if (lettuceConnectionFactory == null) {
            return false;
        }
        try {
            // 尝试获取一个 Redis 连接
            lettuceConnectionFactory.getConnection().getNativeConnection();
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}