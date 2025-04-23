package tt.smart.agency.cache.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import tt.smart.agency.cache.config.CacheStrategyConfig;
import tt.smart.agency.cache.factory.BaseCacheStrategyFactory;
import tt.smart.agency.cache.factory.MemoryCacheStrategyFactory;
import tt.smart.agency.cache.factory.RedisCacheStrategyFactory;

import java.util.Arrays;

/**
 * <p>
 * 储存策略枚举。<br>
 * 枚举顺序就是默认缓存策略启动顺序。
 * </p>
 *
 * @author MC_Yang
 * @version V1.0
 **/
@Getter
@AllArgsConstructor
public enum CacheType {
    /**
     * redis
     */
    REDIS("Redis", RedisCacheStrategyFactory.instance()),
    /**
     * 内存
     */
    MEMORY("Memory", MemoryCacheStrategyFactory.instance());

    /**
     * 缓存策略名称
     */
    private final String name;

    /**
     * 缓存策略工厂
     */
    private final BaseCacheStrategyFactory<? extends CacheStrategyConfig> strategyFactory;

    /**
     * 按枚举序号获取类型。<br>
     * 如果没有匹配类型则返回默认类型 {@code CacheType.Memory}
     *
     * @param ordinal 枚举序号
     * @return 类型
     */
    public static CacheType getCacheType(int ordinal) {
        return Arrays.stream(CacheType.values())
                .filter(type -> type.ordinal() == ordinal)
                .findFirst()
                .orElse(CacheType.MEMORY);
    }

    /**
     * 按枚举名称获取类型。<br>
     * 如果没有匹配类型则返回默认类型 {@code CacheType.Memory}
     *
     * @param name 存储类型名称
     * @return 类型
     */
    public static CacheType getCacheType(String name) {
        return Arrays.stream(CacheType.values())
                .filter(type -> type.name.equalsIgnoreCase(name))
                .findFirst()
                .orElse(CacheType.MEMORY);
    }
}
