package tt.smart.agency.message.config.properties.sms;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * <p>
 * 腾讯云短信服务配置属性
 * </p>
 *
 * @author MC_Yang
 * @version V1.0
 **/
@Data
@SuperBuilder
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class TencentProperties extends BaseSmsProperties {
    /**
     * 短信 sdkAppId
     */
    private String sdkAppId;

    /**
     * 地域信息默认为 ap-guangzhou
     */
    @Builder.Default
    private String territory = "ap-guangzhou";

    /**
     * 请求超时时间
     */
    @Builder.Default
    private Integer connTimeout = 60;
    /**
     * 请求地址
     */
    @Builder.Default
    private String requestUrl = "sms.tencentcloudapi.com";
    /**
     * 接口名称
     */
    @Builder.Default
    private String action = "SendSms";

    /**
     * 接口版本
     */
    @Builder.Default
    private String version = "2021-01-11";

    /**
     * 服务名
     */
    @Builder.Default
    private String service = "sms";

}