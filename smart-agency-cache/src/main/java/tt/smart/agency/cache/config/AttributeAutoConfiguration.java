package tt.smart.agency.cache.config;

import cn.hutool.extra.spring.SpringUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import tt.smart.agency.cache.config.properties.BaseCacheStrategyProperties;
import tt.smart.agency.cache.config.properties.MemoryCacheStrategyProperties;
import tt.smart.agency.cache.config.properties.RedisCacheStrategyProperties;
import tt.smart.agency.cache.enums.CacheType;
import tt.smart.agency.cache.factory.MemoryCacheStrategyFactory;
import tt.smart.agency.cache.factory.RedisCacheStrategyFactory;

/**
 * <p>
 * 配置属性自动装配
 * </p>
 *
 * @author MC_Yang
 * @version V1.0
 **/
@Slf4j
public class AttributeAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean
    @ConditionalOnProperty(prefix = "agency.cache", name = "cache-type", havingValue = "MEMORY")
    @ConfigurationProperties(prefix = "agency.cache.memory")
    public MemoryCacheStrategyProperties memoryCacheStrategyConfig() {
        MemoryCacheStrategyProperties strategyProperties = MemoryCacheStrategyFactory.instance().buildConfig();
        strategyProperties.setCacheType(CacheType.MEMORY);
        return strategyProperties;
    }

    @Bean
    @ConditionalOnMissingBean
    @ConditionalOnProperty(prefix = "agency.cache", name = "cache-type", havingValue = "REDIS")
    @ConfigurationProperties(prefix = "agency.cache.redis")
    public RedisCacheStrategyProperties redisCacheStrategyConfig() {
        RedisCacheStrategyProperties strategyProperties = RedisCacheStrategyFactory.instance().buildConfig();
        strategyProperties.setCacheType(CacheType.REDIS);
        strategyProperties.setLettuceConnectionFactory(SpringUtil.getBean(LettuceConnectionFactory.class));
        return strategyProperties;
    }

    @Bean
    @ConditionalOnMissingBean(value = {MemoryCacheStrategyProperties.class, RedisCacheStrategyProperties.class})
    public BaseCacheStrategyProperties defaultCacheStrategyConfig() {
        BaseCacheStrategyProperties defaultProperties = null;

        try {
            // Redis
            RedisCacheStrategyProperties redisProperties = RedisCacheStrategyFactory.instance().buildConfig();
            redisProperties.setCacheType(CacheType.REDIS);
            redisProperties.setLettuceConnectionFactory(SpringUtil.getBean(LettuceConnectionFactory.class));
            if (redisProperties.checkStartCondition()) {
                defaultProperties = redisProperties;
            } else {
                redisProperties.setCacheType(null);
                redisProperties.setLettuceConnectionFactory(null);
            }
        } catch (Exception e) {
            log.debug("未找到任何第三方缓存配置资源");
        } finally {
            if (defaultProperties == null) {
                // 内存
                MemoryCacheStrategyProperties memoryProperties = MemoryCacheStrategyFactory.instance().buildConfig();
                memoryProperties.setCacheType(CacheType.MEMORY);
                defaultProperties = memoryProperties;
            }
        }

        return defaultProperties;
    }

}