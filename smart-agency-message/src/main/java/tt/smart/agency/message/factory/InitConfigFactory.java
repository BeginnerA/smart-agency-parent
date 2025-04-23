package tt.smart.agency.message.factory;

import tt.smart.agency.message.config.properties.Config;

/**
 * <p>
 * 初始化配置工厂
 * </p>
 *
 * @param <C> 配置
 * @author MC_Yang
 * @version V1.0
 **/
public interface InitConfigFactory<C extends Config> {

    /**
     * 构建配置
     *
     * @return 配置
     */
    C buildConfig();

}
