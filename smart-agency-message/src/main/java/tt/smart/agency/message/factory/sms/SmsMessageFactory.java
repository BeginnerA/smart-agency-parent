package tt.smart.agency.message.factory.sms;

import tt.smart.agency.message.api.sms.SmsMessageBlend;
import tt.smart.agency.message.config.properties.sms.SupplierConfig;
import tt.smart.agency.message.enums.PlatformType;
import tt.smart.agency.message.enums.sms.SupplierPlatformType;

/**
 * <p>
 * 短信工厂
 * </p>
 *
 * @author YangMC
 * @version V1.0
 **/
public class SmsMessageFactory {

    /**
     * 创建供应商的短信服务
     *
     * @param supplierPlatformType 供应商类型
     * @return 通用短信服务
     */
    @SuppressWarnings(value = {"unchecked", "rawtypes"})
    public static SmsMessageBlend createSmsBlend(PlatformType supplierPlatformType) {
        BaseSmsPlatformFactory providerFactory = supplierPlatformType.getPlatformFactory();
        return providerFactory.createSms((SupplierConfig) providerFactory.buildConfig());
    }

    /**
     * 创建供应商的短信服务
     *
     * @param supplierPlatformType 供应商类型
     * @return 通用短信服务
     */
    @SuppressWarnings(value = {"unchecked", "rawtypes"})
    public static SmsMessageBlend createSmsBlend(SupplierPlatformType supplierPlatformType) {
        BaseSmsPlatformFactory providerFactory = supplierPlatformType.getPlatformFactory();
        return providerFactory.createSms((SupplierConfig) providerFactory.buildConfig());
    }

}
