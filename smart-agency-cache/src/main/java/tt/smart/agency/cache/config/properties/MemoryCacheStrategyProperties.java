package tt.smart.agency.cache.config.properties;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * <p>
 * 内存缓存策略配置属性
 * </p>
 *
 * @author YangMC
 * @version V1.0
 **/
@Data
@SuperBuilder
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class MemoryCacheStrategyProperties extends BaseCacheStrategyProperties {
    @Override
    public boolean checkStartCondition() {
        return true;
    }
}