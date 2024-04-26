package tt.smart.agency.cache.factory;

import tt.smart.agency.cache.config.properties.RedisCacheStrategyProperties;
import tt.smart.agency.cache.strategy.CacheStrategy;
import tt.smart.agency.cache.strategy.RedisCacheStrategy;

/**
 * <p>
 * Redis 缓存策略工厂
 * </p>
 *
 * @author YangMC
 * @version V1.0
 **/
@SuppressWarnings(value = {"rawtypes"})
public class RedisCacheStrategyFactory extends BaseCacheStrategyFactory<RedisCacheStrategyProperties> {

    private static final RedisCacheStrategyFactory INSTANCE = new RedisCacheStrategyFactory();
    private static RedisCacheStrategy redisCacheStrategy;

    private static final class ConfigHolder {
        private static final RedisCacheStrategyProperties CONFIG = RedisCacheStrategyProperties.builder().build();
    }

    /**
     * 得到建造者实例
     *
     * @return 建造者实例
     */
    public static RedisCacheStrategyFactory instance() {
        return INSTANCE;
    }

    @Override
    public RedisCacheStrategyProperties buildConfig() {
        return ConfigHolder.CONFIG;
    }

    @Override
    public CacheStrategy createStrategy(RedisCacheStrategyProperties cacheStrategyProperties) {
        if (redisCacheStrategy == null) {
            redisCacheStrategy = new RedisCacheStrategy<>(cacheStrategyProperties.getLettuceConnectionFactory());
        }
        return redisCacheStrategy;
    }
}