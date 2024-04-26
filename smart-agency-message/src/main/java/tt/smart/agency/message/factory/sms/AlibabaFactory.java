package tt.smart.agency.message.factory.sms;

import tt.smart.agency.message.api.sms.AlibabaSms;
import tt.smart.agency.message.config.properties.sms.AlibabaProperties;
import tt.smart.agency.message.service.sms.AlibabaSmsImpl;

/**
 * <p>
 * 阿里云短信工厂
 * </p>
 *
 * @author YangMC
 * @version V1.0
 **/
public class AlibabaFactory implements BaseSmsPlatformFactory<AlibabaSms, AlibabaProperties> {

    private static final AlibabaFactory INSTANCE = new AlibabaFactory();
    private static AlibabaSms alibabaSms;

    private static final class ConfigHolder {
        private static final AlibabaProperties CONFIG = AlibabaProperties.builder().build();
    }

    /**
     * 得到建造者实例
     *
     * @return 建造者实例
     */
    public static AlibabaFactory instance() {
        return INSTANCE;
    }

    @Override
    public AlibabaSms createSms(AlibabaProperties alibabaProperties) {
        if (alibabaSms == null) {
            alibabaSms = new AlibabaSmsImpl(alibabaProperties);
        }
        return alibabaSms;
    }

    @Override
    public AlibabaProperties buildConfig() {
        return ConfigHolder.CONFIG;
    }

}
