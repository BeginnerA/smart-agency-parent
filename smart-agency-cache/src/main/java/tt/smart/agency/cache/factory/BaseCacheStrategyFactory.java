package tt.smart.agency.cache.factory;

import tt.smart.agency.cache.config.CacheStrategyConfig;
import tt.smart.agency.cache.enums.CacheType;
import tt.smart.agency.cache.strategy.CacheStrategy;

/**
 * <p>
 * 基本缓存策略工厂
 * </p>
 *
 * @author YangMC
 * @version V1.0
 **/
@SuppressWarnings(value = {"rawtypes"})
public abstract class BaseCacheStrategyFactory<C extends CacheStrategyConfig> {

    /**
     * 判断指定启动缓存策略是否满足启动条件
     *
     * @return 是否满足启动条件
     */
    protected boolean canStartSpecifiesCache() {
        CacheType specifiesCacheType = buildConfig().getSpecifiesCacheType();
        if (specifiesCacheType != null) {
            return buildConfig().checkStartCondition();
        }
        return false;
    }

    /**
     * 构建缓存策略配置
     *
     * @return 缓存策略配置
     */
    public abstract C buildConfig();

    /**
     * 创建缓存策略实例
     *
     * @param c 策略配置
     * @return 缓存策略实例
     */
    public abstract CacheStrategy createStrategy(C c);

}