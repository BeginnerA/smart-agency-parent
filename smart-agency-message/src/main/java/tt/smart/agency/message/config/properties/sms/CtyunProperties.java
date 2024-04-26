package tt.smart.agency.message.config.properties.sms;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * <p>
 * 天翼云短信服务配置属性
 * </p>
 *
 * @author YangMC
 * @version V1.0
 **/
@Data
@SuperBuilder
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class CtyunProperties extends BaseSmsProperties {

    /**
     * 模板变量名称
     */
    private String templateName;

    /**
     * 请求地址
     */
    @Builder.Default
    private String requestUrl = "https://sms-global.ctapi.ctyun.cn/sms/api/v1";

    /**
     * 接口名称
     */
    @Builder.Default
    private String action = "SendSms";

}