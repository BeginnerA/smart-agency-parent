package tt.smart.agency.message.config.properties.sms;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * <p>
 * 华为云短信服务配置属性
 * </p>
 *
 * @author MC_Yang
 * @version V1.0
 **/
@Data
@SuperBuilder
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class HuaweiProperties extends BaseSmsProperties {

    /**
     * 国内短信签名通道号
     */
    private String sender;
    /**
     * 短信状态报告接收地
     */
    private String statusCallBack;
    /**
     * APP 接入地址
     */
    private String url;

}