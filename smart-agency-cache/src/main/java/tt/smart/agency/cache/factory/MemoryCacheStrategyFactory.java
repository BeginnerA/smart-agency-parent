package tt.smart.agency.cache.factory;

import tt.smart.agency.cache.config.properties.MemoryCacheStrategyProperties;
import tt.smart.agency.cache.strategy.CacheStrategy;
import tt.smart.agency.cache.strategy.MemoryCacheStrategy;

/**
 * <p>
 * 内存缓存策略工厂
 * </p>
 *
 * @author YangMC
 * @version V1.0
 **/
@SuppressWarnings(value = {"rawtypes"})
public class MemoryCacheStrategyFactory extends BaseCacheStrategyFactory<MemoryCacheStrategyProperties> {

    private static final MemoryCacheStrategyFactory INSTANCE = new MemoryCacheStrategyFactory();
    private static MemoryCacheStrategy memoryCacheStrategy;

    private static final class ConfigHolder {
        private static final MemoryCacheStrategyProperties CONFIG = MemoryCacheStrategyProperties.builder().build();
    }

    /**
     * 得到建造者实例
     *
     * @return 建造者实例
     */
    public static MemoryCacheStrategyFactory instance() {
        return INSTANCE;
    }

    @Override
    public MemoryCacheStrategyProperties buildConfig() {
        return ConfigHolder.CONFIG;
    }

    @Override
    public CacheStrategy createStrategy(MemoryCacheStrategyProperties cacheStrategyProperties) {
        if (memoryCacheStrategy == null) {
            memoryCacheStrategy = new MemoryCacheStrategy<>();
        }
        return memoryCacheStrategy;
    }
}