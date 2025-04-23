package tt.smart.agency.message.domain;

import cn.hutool.core.util.StrUtil;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;
import tt.smart.agency.message.http.HttpRequestBase;

/**
 * <p>
 * 通用消息推送对象
 * </p>
 *
 * @author MC_Yang
 * @version V1.0
 **/
@Data
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
public class MessageSendBlend extends HttpRequestBase {
    /**
     * 消息内容
     */
    private String messageContent;
    /**
     * 第三方消息推送 API URL 地址
     */
    private String url;
    /**
     * 访问令牌
     */
    private String accessToken;

    @Override
    public String getWithAccessTokenUrl() {
        if (StrUtil.isBlank(accessToken)) {
            return url;
        }
        return url + (url.contains("?") ? "&" : "?") + "access_token=" + accessToken;
    }

    @Override
    public String getBody() {
        return messageContent;
    }
}