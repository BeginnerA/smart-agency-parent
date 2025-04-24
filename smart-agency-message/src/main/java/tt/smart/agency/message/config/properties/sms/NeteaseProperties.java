package tt.smart.agency.message.config.properties.sms;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * <p>
 * 网易云短信服务配置属性
 * </p>
 *
 * @author MC_Yang
 * @version V1.0
 **/
@Data
@SuperBuilder
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class NeteaseProperties extends BaseSmsProperties {

    /**
     * 模板变量名称
     */
    private String templateName;

    /**
     * 模板短信请求地址
     */
    @Builder.Default
    private String templateUrl = "https://api.netease.im/sms/sendtemplate.action";

    /**
     * 验证码短信请求地址
     */
    @Builder.Default
    private String codeUrl = "https://api.netease.im/sms/sendcode.action";

    /**
     * 验证码验证请求地址
     */
    @Builder.Default
    private String verifyUrl = "https://api.netease.im/sms/verifycode.action";

    /**
     * 是否需要支持短信上行。true:需要，false:不需要
     * 说明：如果开通了短信上行抄送功能，该参数需要设置为true，其它情况设置无效
     */
    private Boolean needUp;

}