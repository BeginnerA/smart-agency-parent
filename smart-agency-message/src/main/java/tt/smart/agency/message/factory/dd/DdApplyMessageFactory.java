package tt.smart.agency.message.factory.dd;

import tt.smart.agency.message.api.dd.DdApplyMessage;
import tt.smart.agency.message.config.properties.dd.DdProperties;
import tt.smart.agency.message.service.dd.DdApplyMessageImpl;

/**
 * <p>
 * 钉钉应用消息工厂
 * </p>
 *
 * @author YangMC
 * @version V1.0
 **/
public class DdApplyMessageFactory implements BaseDdPlatformFactory<DdApplyMessage, DdProperties> {

    private static final DdApplyMessageFactory INSTANCE = new DdApplyMessageFactory();
    private static DdApplyMessage ddApplyMessage;

    private static final class ConfigHolder {
        private static final DdProperties CONFIG = DdProperties.builder().build();
    }

    /**
     * 得到建造者实例
     *
     * @return 建造者实例
     */
    public static DdApplyMessageFactory instance() {
        return INSTANCE;
    }

    @Override
    public DdProperties buildConfig() {
        return ConfigHolder.CONFIG;
    }

    @Override
    public DdApplyMessage createDdMessage(DdProperties ddProperties) {
        if (ddApplyMessage == null) {
            ddApplyMessage = new DdApplyMessageImpl(ddProperties);
        }
        return ddApplyMessage;
    }

}
