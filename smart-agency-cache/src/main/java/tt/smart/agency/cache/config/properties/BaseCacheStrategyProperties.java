package tt.smart.agency.cache.config.properties;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import tt.smart.agency.cache.config.CacheStrategyConfig;
import tt.smart.agency.cache.enums.CacheType;

/**
 * <p>
 * 缓存策略属性
 * </p>
 *
 * @author MC_Yang
 * @version V1.0
 **/
@Data
@SuperBuilder
@NoArgsConstructor
public abstract class BaseCacheStrategyProperties implements CacheStrategyConfig {

    /**
     * 缓存策略类型
     */
    private CacheType cacheType;

    @Override
    public CacheType getSpecifiesCacheType() {
        return cacheType;
    }

}