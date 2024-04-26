package tt.smart.agency.message.factory.sms;

import tt.smart.agency.message.api.sms.TencentSms;
import tt.smart.agency.message.config.properties.sms.TencentProperties;
import tt.smart.agency.message.service.sms.TencentSmsImpl;

/**
 * <p>
 * 腾讯云短信工厂
 * </p>
 *
 * @author YangMC
 * @version V1.0
 **/
public class TencentFactory implements BaseSmsPlatformFactory<TencentSms, TencentProperties> {

    private static final TencentFactory INSTANCE = new TencentFactory();
    private static TencentSms tencentSms;

    private static final class ConfigHolder {
        private static final TencentProperties CONFIG = TencentProperties.builder().build();
    }

    /**
     * 得到建造者实例
     *
     * @return 建造者实例
     */
    public static TencentFactory instance() {
        return INSTANCE;
    }

    @Override
    public TencentSms createSms(TencentProperties tencentProperties) {
        if (tencentSms == null) {
            tencentSms = new TencentSmsImpl(tencentProperties);
        }
        return tencentSms;
    }

    @Override
    public TencentProperties buildConfig() {
        return TencentFactory.ConfigHolder.CONFIG;
    }

}