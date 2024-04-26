package tt.smart.agency.message.factory.dd;

import tt.smart.agency.message.api.dd.DdMessageBlend;
import tt.smart.agency.message.config.properties.dd.DdConfig;
import tt.smart.agency.message.domain.dd.DdMessage;
import tt.smart.agency.message.domain.dd.DdMessageSendResult;
import tt.smart.agency.message.factory.InitConfigFactory;

/**
 * <p>
 * 基础钉钉平台工厂
 * </p>
 *
 * @author YangMC
 * @version V1.0
 **/
public interface BaseDdPlatformFactory<S extends DdMessageBlend<DdMessageSendResult, DdMessage>, C extends DdConfig> extends InitConfigFactory<C> {

    /**
     * 创建钉钉消息实例
     *
     * @param c 钉钉消息配置
     * @return 钉钉消息实例
     */
    S createDdMessage(C c);

}