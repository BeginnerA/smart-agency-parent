package tt.smart.agency.cache.factory;

import tt.smart.agency.cache.enums.CacheType;
import tt.smart.agency.cache.exception.CacheStrategyException;
import tt.smart.agency.cache.strategy.CacheStrategy;

/**
 * <p>
 * 缓存策略工厂
 * </p>
 *
 * @author MC_Yang
 * @version V1.0
 **/
public class CacheStrategyFactory {
    /**
     * 创建缓存策略服务
     *
     * @param cacheType 缓存类型
     * @return 缓存策略服务
     */
    @SuppressWarnings(value = {"unchecked", "rawtypes"})
    public static CacheStrategy createCacheStrategyBlend(CacheType cacheType) {
        BaseCacheStrategyFactory strategyFactory = cacheType.getStrategyFactory();
        if (!strategyFactory.canStartSpecifiesCache()) {
            throw new CacheStrategyException("启动缓存策略异常：[" + cacheType.getName() + "]缓存策略启动条件不满足");
        }
        return strategyFactory.createStrategy(strategyFactory.buildConfig());
    }
}