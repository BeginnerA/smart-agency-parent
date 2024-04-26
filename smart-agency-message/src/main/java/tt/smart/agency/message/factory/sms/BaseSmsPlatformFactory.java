package tt.smart.agency.message.factory.sms;

import tt.smart.agency.message.api.sms.SmsMessageBlend;
import tt.smart.agency.message.config.properties.sms.SupplierConfig;
import tt.smart.agency.message.factory.InitConfigFactory;

/**
 * <p>
 * 基础短信平台工厂
 * </p>
 *
 * @param <S> 短信服务
 * @param <C> 短信配置
 * @author YangMC
 * @version V1.0
 **/
public interface BaseSmsPlatformFactory<S extends SmsMessageBlend, C extends SupplierConfig> extends InitConfigFactory<C> {

    /**
     * 创建短信实例
     *
     * @param c 短信配置
     * @return 短信实例
     */
    S createSms(C c);

}
