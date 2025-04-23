package tt.smart.agency.cache.config;

import tt.smart.agency.cache.enums.CacheType;

import java.io.Serializable;

/**
 * <p>
 * 缓存策略配置
 * </p>
 *
 * @author MC_Yang
 * @version V1.0
 **/
public interface CacheStrategyConfig extends Serializable {

    /**
     * 检查缓存策略启动条件
     *
     * @return 配置属性是否满足缓存策略启动条件
     */
    boolean checkStartCondition();

    /**
     * 得到配置指定的缓存策略类型
     *
     * @return 缓存策略类型
     */
    CacheType getSpecifiesCacheType();

}