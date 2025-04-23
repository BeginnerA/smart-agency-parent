package tt.smart.agency.message.config.properties.wx;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * <p>
 * 微信接入相关配置属性
 * </p>
 *
 * @author MC_Yang
 * @version V1.0
 **/
@Data
@SuperBuilder
@NoArgsConstructor
public class WxMpProperties implements WxConfig {

    /**
     * 设置微信公众号的 appid
     */
    private String appId;

    /**
     * 设置微信公众号的 app secret
     */
    private String secret;

    /**
     * 设置微信公众号的 token
     */
    private String token;

    /**
     * 设置微信公众号的 EncodingAESKey
     */
    private String aesKey;

}
