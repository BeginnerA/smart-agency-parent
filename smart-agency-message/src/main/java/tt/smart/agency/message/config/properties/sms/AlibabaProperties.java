package tt.smart.agency.message.config.properties.sms;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * <p>
 * 阿里云短信服务配置
 * </p>
 *
 * @author YangMC
 * @version V1.0
 **/
@Data
@SuperBuilder
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class AlibabaProperties extends BaseSmsProperties {

    /**
     * 模板变量名称
     */
    private String templateName;

    /**
     * 请求地址
     */
    @Builder.Default
    private String requestUrl = "https://dysmsapi.aliyuncs.com";

    /**
     * 接口名称
     */
    @Builder.Default
    private String action = "SendSms";

    /**
     * 接口版本号
     */
    @Builder.Default
    private String version = "2017-05-25";

    /**
     * 地域信息默认为 cn-hangzhou
     */
    @Builder.Default
    private String regionId = "cn-hangzhou";

}
