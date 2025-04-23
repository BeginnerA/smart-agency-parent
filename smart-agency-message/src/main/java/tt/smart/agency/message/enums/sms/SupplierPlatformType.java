package tt.smart.agency.message.enums.sms;

import lombok.AllArgsConstructor;
import lombok.Getter;
import tt.smart.agency.message.api.sms.SmsMessageBlend;
import tt.smart.agency.message.config.properties.sms.SupplierConfig;
import tt.smart.agency.message.enums.PlatformType;
import tt.smart.agency.message.factory.sms.*;

/**
 * <p>
 * 短信供应商类型
 * </p>
 *
 * @author MC_Yang
 * @version V1.0
 **/
@Getter
@AllArgsConstructor
public enum SupplierPlatformType implements PlatformType {
    /**
     * 阿里云
     */
    ALIBABA("阿里云短信", AlibabaFactory.instance()),
    /**
     * 华为云
     */
    HUAWEI("华为云短信", HuaweiFactory.instance()),
    /**
     * 腾讯云
     */
    TENCENT("腾讯云短信", TencentFactory.instance()),
    /**
     * 天翼云
     */
    CTYUN("天翼云短信", CtyunFactory.instance()),
    /**
     * 网易云
     */
    NETEASE("网易云短信", NeteaseFactory.instance()),
    ;

    /**
     * 渠道名称
     */
    private final String name;

    /**
     * 短信对象配置
     */
    private final BaseSmsPlatformFactory<? extends SmsMessageBlend, ? extends SupplierConfig> platformFactory;

}
