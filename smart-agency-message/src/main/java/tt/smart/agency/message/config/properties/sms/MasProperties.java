package tt.smart.agency.message.config.properties.sms;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import tt.smart.agency.message.enums.sms.MasSmsType;

/**
 * <p>
 * 移动云短信服务配置属性
 * </p>
 *
 * @author MC_Yang
 * @version V1.0
 **/
@Data
@SuperBuilder
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class MasProperties extends BaseSmsProperties {

    /**
     * 短信类型<br/>
     * content：普通内容短信<br/>
     * template：模板短信<br/>
     */
    @Builder.Default
    private MasSmsType smsType = MasSmsType.CONTENT;

    /**
     * 集团名称
     */
    private String ecName;

    /**
     * 拓展码
     */
    private String addSerial;

    /**
     * 请求地址
     */
    private String requestUrl;

    /**
     * 发送普通内容地址{@code requestUrl + contentAddress}<br/>
     * 这里需要注意，移动云发送普通内容短信地址 HTTP 和 HTTPS 是不一样的。<br/>
     * HTTP：/sms/submit<br/>
     * HTTPS：/sms/norsubmit
     */
    @Builder.Default
    private String contentAddress = "/sms/submit";

    /**
     * 发送模板内容地址{@code requestUrl + templateAddress}
     */
    @Builder.Default
    private String templateAddress = "/sms/tmpsubmit";

}
