package tt.smart.agency.message.config.properties.wx;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * <p>
 * 企业微信接入相关配置属性
 * </p>
 *
 * @author YangMC
 * @version V1.0
 **/
@Data
@SuperBuilder
@NoArgsConstructor
public class WxCpProperties implements WxConfig {

    /**
     * 设置企业微信企业 ID
     */
    private String corpId;

    /**
     * 设置企业微信企业应用 ID
     */
    private String corpSecret;

    /**
     * 设置企业微信的 token
     */
    private String token;

    /**
     * 设置企业微信的 EncodingAESKey
     */
    private String encodingAesKey;

    /**
     * 企业应用的 ID。企业内部开发，可在应用的设置页面查看；第三方服务商，可通过接口<获取企业授权信息>获取该参数值
     */
    private String agentId;

}
