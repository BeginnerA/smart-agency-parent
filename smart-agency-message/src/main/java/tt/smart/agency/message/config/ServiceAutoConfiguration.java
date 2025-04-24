package tt.smart.agency.message.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import tt.smart.agency.message.api.dd.DdApplyMessage;
import tt.smart.agency.message.api.sms.*;
import tt.smart.agency.message.api.wx.WxCpApplyMessage;
import tt.smart.agency.message.api.wx.WxMpMessage;
import tt.smart.agency.message.config.properties.dd.DdProperties;
import tt.smart.agency.message.config.properties.sms.*;
import tt.smart.agency.message.config.properties.wx.WxCpProperties;
import tt.smart.agency.message.config.properties.wx.WxMpProperties;
import tt.smart.agency.message.enums.dd.DdPlatformType;
import tt.smart.agency.message.enums.sms.SupplierPlatformType;
import tt.smart.agency.message.factory.dd.DdMessageFactory;
import tt.smart.agency.message.factory.sms.SmsMessageFactory;
import tt.smart.agency.message.factory.wx.WxMessageFactory;

/**
 * <p>
 * 服务自动配置
 * </p>
 *
 * @author MC_Yang
 * @version V1.0
 **/
public class ServiceAutoConfiguration {

    @Bean
    @ConditionalOnBean(DdProperties.class)
    @ConditionalOnMissingBean
    public DdApplyMessage initDdApplyMessageService() {
        return (DdApplyMessage) DdMessageFactory.createDdMessageBlend(DdPlatformType.DD_APPLY);
    }

    @Bean
    @ConditionalOnBean(WxCpProperties.class)
    @ConditionalOnMissingBean
    public WxCpApplyMessage initWxCpApplyMessageService() {
        return (WxCpApplyMessage) WxMessageFactory.createWxCpMessageBlend();
    }

    @Bean
    @ConditionalOnBean(WxMpProperties.class)
    @ConditionalOnMissingBean
    public WxMpMessage initWxMpApplyMessageService() {
        return (WxMpMessage) WxMessageFactory.createWxMpMessageBlend();
    }

    @Bean
    @ConditionalOnBean(AlibabaProperties.class)
    @ConditionalOnMissingBean
    public AlibabaSms initAlibabaSmsService() {
        return (AlibabaSms) SmsMessageFactory.createSmsBlend(SupplierPlatformType.ALIBABA);
    }

    @Bean
    @ConditionalOnBean(HuaweiProperties.class)
    @ConditionalOnMissingBean
    public HuaweiSms initHuaweiSmsService() {
        return (HuaweiSms) SmsMessageFactory.createSmsBlend(SupplierPlatformType.HUAWEI);
    }

    @Bean
    @ConditionalOnBean(TencentProperties.class)
    @ConditionalOnMissingBean
    public TencentSms initTencentSmsService() {
        return (TencentSms) SmsMessageFactory.createSmsBlend(SupplierPlatformType.TENCENT);
    }

    @Bean
    @ConditionalOnBean(CtyunProperties.class)
    @ConditionalOnMissingBean
    public CtyunSms initCtyunSmsService() {
        return (CtyunSms) SmsMessageFactory.createSmsBlend(SupplierPlatformType.CTYUN);
    }

    @Bean
    @ConditionalOnBean(NeteaseProperties.class)
    @ConditionalOnMissingBean
    public NeteaseSms initNeteaseSmsService() {
        return (NeteaseSms) SmsMessageFactory.createSmsBlend(SupplierPlatformType.NETEASE);
    }

    @Bean
    @ConditionalOnBean(MasProperties.class)
    @ConditionalOnMissingBean
    public MasSms initMasSmsService() {
        return (MasSms) SmsMessageFactory.createSmsBlend(SupplierPlatformType.MAS);
    }

}
