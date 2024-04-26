package tt.smart.agency.message.factory.sms;

import tt.smart.agency.message.api.sms.HuaweiSms;
import tt.smart.agency.message.config.properties.sms.HuaweiProperties;
import tt.smart.agency.message.service.sms.HuaweiSmsImpl;

/**
 * <p>
 * 华为云短信工厂
 * </p>
 *
 * @author YangMC
 * @version V1.0
 **/
public class HuaweiFactory implements BaseSmsPlatformFactory<HuaweiSms, HuaweiProperties> {

    private static final HuaweiFactory INSTANCE = new HuaweiFactory();
    private static HuaweiSms huaweiSms;

    private static final class ConfigHolder {
        private static final HuaweiProperties CONFIG = HuaweiProperties.builder().build();
    }

    /**
     * 得到建造者实例
     *
     * @return 建造者实例
     */
    public static HuaweiFactory instance() {
        return INSTANCE;
    }

    @Override
    public HuaweiSms createSms(HuaweiProperties huaweiProperties) {
        if (huaweiSms == null) {
            huaweiSms = new HuaweiSmsImpl(huaweiProperties);
        }
        return huaweiSms;
    }

    @Override
    public HuaweiProperties buildConfig() {
        return HuaweiFactory.ConfigHolder.CONFIG;
    }

}