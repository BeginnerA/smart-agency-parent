package tt.smart.agency.message.factory.sms;

import tt.smart.agency.message.api.sms.CtyunSms;
import tt.smart.agency.message.config.properties.sms.CtyunProperties;
import tt.smart.agency.message.service.sms.CtyunSmsImpl;

/**
 * <p>
 * 天翼云短信工厂
 * </p>
 *
 * @author YangMC
 * @version V1.0
 **/
public class CtyunFactory implements BaseSmsPlatformFactory<CtyunSms, CtyunProperties> {

    private static final CtyunFactory INSTANCE = new CtyunFactory();
    private static CtyunSms ctyunSms;

    private static final class ConfigHolder {
        private static final CtyunProperties CONFIG = CtyunProperties.builder().build();
    }

    /**
     * 得到建造者实例
     *
     * @return 建造者实例
     */
    public static CtyunFactory instance() {
        return INSTANCE;
    }

    @Override
    public CtyunSms createSms(CtyunProperties ctyunProperties) {
        if (ctyunSms == null) {
            ctyunSms = new CtyunSmsImpl(ctyunProperties);
        }
        return ctyunSms;
    }

    @Override
    public CtyunProperties buildConfig() {
        return CtyunFactory.ConfigHolder.CONFIG;
    }

}