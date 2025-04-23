package tt.smart.agency.message.config.properties.dd;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * <p>
 * 钉钉接入相关配置属性
 * </p>
 *
 * @author MC_Yang
 * @version V1.0
 **/
@Data
@SuperBuilder
@NoArgsConstructor
public class DdProperties implements DdConfig {

    /**
     * 钉钉应用的唯一标识 KEY
     */
    private String appKey;

    /**
     * 钉钉应用的密钥。AppKey 和 AppSecret 可在钉钉开发者后台的应用详情页面获取
     */
    private String appSecret;

    /**
     * 推送消息时使用的微应用的 AgentID。
     * 企业内部应用可在开发者后台的应用详情页面查看。
     * 第三方企业应用可调用获取企业授权信息接口获取。
     */
    protected String agentId;

}
