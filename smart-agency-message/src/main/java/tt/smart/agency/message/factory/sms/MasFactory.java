package tt.smart.agency.message.factory.sms;

import tt.smart.agency.message.api.sms.MasSms;
import tt.smart.agency.message.config.properties.sms.MasProperties;
import tt.smart.agency.message.service.sms.MasSmsImpl;

/**
 * <p>
 * 移动云短信工厂
 * </p>
 *
 * @author MC_Yang
 * @version V1.0
 **/
public class MasFactory implements BaseSmsPlatformFactory<MasSms, MasProperties> {

    private static final MasFactory INSTANCE = new MasFactory();
    private static MasSms masSms;

    private static final class ConfigHolder {
        private static final MasProperties CONFIG = MasProperties.builder().build();
    }

    /**
     * 得到建造者实例
     *
     * @return 建造者实例
     */
    public static MasFactory instance() {
        return INSTANCE;
    }

    @Override
    public MasSms createSms(MasProperties masProperties) {
        if (masSms == null) {
            masSms = new MasSmsImpl(masProperties);
        }
        return masSms;
    }

    @Override
    public MasProperties buildConfig() {
        return MasFactory.ConfigHolder.CONFIG;
    }
}
