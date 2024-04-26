package tt.smart.agency.message.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import tt.smart.agency.message.config.properties.dd.DdProperties;
import tt.smart.agency.message.config.properties.sms.*;
import tt.smart.agency.message.config.properties.wx.WxCpProperties;
import tt.smart.agency.message.config.properties.wx.WxMpProperties;
import tt.smart.agency.message.factory.dd.DdApplyMessageFactory;
import tt.smart.agency.message.factory.sms.*;
import tt.smart.agency.message.factory.wx.WxCpApplyMessageFactory;
import tt.smart.agency.message.factory.wx.WxMpMessageFactory;

/**
 * <p>
 * 配置属性自动装配
 * </p>
 *
 * @author YangMC
 * @version V1.0
 **/
public class AttributeAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean
    @ConditionalOnProperty(prefix = "agency.message.dd", name = "app-key")
    @ConfigurationProperties(prefix = "agency.message.dd")
    public DdProperties ddConfig() {
        return DdApplyMessageFactory.instance().buildConfig();
    }

    @Bean
    @ConditionalOnMissingBean
    @ConditionalOnProperty(prefix = "agency.message.wx.cp", name = "corp-id")
    @ConfigurationProperties(prefix = "agency.message.wx.cp")
    public WxCpProperties wxCpConfig() {
        return WxCpApplyMessageFactory.instance().buildConfig();
    }

    @Bean
    @ConditionalOnMissingBean
    @ConditionalOnProperty(prefix = "agency.message.wx.mp", name = "app-id")
    @ConfigurationProperties(prefix = "agency.message.wx.mp")
    public WxMpProperties wxConfig() {
        return WxMpMessageFactory.instance().buildConfig();
    }

    @Bean
    @ConditionalOnMissingBean
    @ConditionalOnProperty(prefix = "agency.message.sms.alibaba", name = "access-key")
    @ConfigurationProperties(prefix = "agency.message.sms.alibaba")
    protected AlibabaProperties alibabaConfig() {
        return AlibabaFactory.instance().buildConfig();
    }

    @Bean
    @ConditionalOnMissingBean
    @ConditionalOnProperty(prefix = "agency.message.sms.huawei", name = "access-key")
    @ConfigurationProperties(prefix = "agency.message.sms.huawei")
    protected HuaweiProperties huaweiConfig() {
        return HuaweiFactory.instance().buildConfig();
    }

    @Bean
    @ConditionalOnMissingBean
    @ConditionalOnProperty(prefix = "agency.message.sms.tencent", name = "access-key")
    @ConfigurationProperties(prefix = "agency.message.sms.tencent")
    protected TencentProperties tencentConfig() {
        return TencentFactory.instance().buildConfig();
    }

    @Bean
    @ConditionalOnMissingBean
    @ConditionalOnProperty(prefix = "agency.message.sms.ctyun", name = "access-key")
    @ConfigurationProperties(prefix = "agency.message.sms.ctyun")
    protected CtyunProperties ctyunConfig() {
        return CtyunFactory.instance().buildConfig();
    }

    @Bean
    @ConditionalOnMissingBean
    @ConditionalOnProperty(prefix = "agency.message.sms.netease", name = "access-key")
    @ConfigurationProperties(prefix = "agency.message.sms.netease")
    protected NeteaseProperties neteaseConfig() {
        return NeteaseFactory.instance().buildConfig();
    }

}
