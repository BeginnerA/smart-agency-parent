package tt.smart.agency.cache.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import tt.smart.agency.cache.CacheStrategyManager;
import tt.smart.agency.cache.config.properties.BaseCacheStrategyProperties;
import tt.smart.agency.cache.factory.CacheStrategyFactory;
import tt.smart.agency.cache.strategy.CacheStrategy;

/**
 * <p>
 * 服务自动装配
 * </p>
 *
 * @author MC_Yang
 * @version V1.0
 **/
@SuppressWarnings(value = {"rawtypes"})
public class ServiceAutoConfiguration {

    @Bean
    @ConditionalOnBean(BaseCacheStrategyProperties.class)
    @ConditionalOnMissingBean
    public CacheStrategy initCacheStrategyService(BaseCacheStrategyProperties strategyProperties) {
        CacheStrategy cacheStrategyBlend = CacheStrategyFactory.createCacheStrategyBlend(strategyProperties.getCacheType());
        CacheStrategyManager.setCacheStrategy(cacheStrategyBlend);
        return cacheStrategyBlend;
    }

}