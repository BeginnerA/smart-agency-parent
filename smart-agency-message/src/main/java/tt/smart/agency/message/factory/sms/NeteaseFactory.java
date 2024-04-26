package tt.smart.agency.message.factory.sms;

import tt.smart.agency.message.api.sms.NeteaseSms;
import tt.smart.agency.message.config.properties.sms.NeteaseProperties;
import tt.smart.agency.message.service.sms.NeteaseSmsImpl;

/**
 * <p>
 * 网易云短信工厂
 * </p>
 *
 * @author YangMC
 * @version V1.0
 **/
public class NeteaseFactory implements BaseSmsPlatformFactory<NeteaseSms, NeteaseProperties> {

    private static final NeteaseFactory INSTANCE = new NeteaseFactory();
    private static NeteaseSms neteaseSms;

    private static final class ConfigHolder {
        private static final NeteaseProperties CONFIG = NeteaseProperties.builder().build();
    }

    /**
     * 得到建造者实例
     *
     * @return 建造者实例
     */
    public static NeteaseFactory instance() {
        return INSTANCE;
    }

    @Override
    public NeteaseSms createSms(NeteaseProperties neteaseProperties) {
        if (neteaseSms == null) {
            neteaseSms = new NeteaseSmsImpl(neteaseProperties);
        }
        return neteaseSms;
    }

    @Override
    public NeteaseProperties buildConfig() {
        return NeteaseFactory.ConfigHolder.CONFIG;
    }

}