package tt.smart.agency.message.enums.wx;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * <p>
 * 微信公众号接口 API 地址
 * </p>
 *
 * @author MC_Yang
 * @version V1.0
 **/
public interface WxApiUrl {

    /**
     * 默认微信 URL
     */
    String API_DEFAULT_HOST_URL = "https://api.weixin.qq.com";

    /**
     * 企业微信 URL
     */
    String API_CP_HOST_URL = "https://qyapi.weixin.qq.com";

    /**
     * 路径
     *
     * @return 路径
     */
    String getPath();

    /**
     * 前缀
     *
     * @return 前缀
     */
    String getPrefix();

    /**
     * 得到 API 完整地址
     *
     * @return API 地址
     */
    default String getUrl() {
        return this.getPrefix() + this.getPath();
    }

    /**
     * 权限
     */
    @Getter
    @AllArgsConstructor
    enum Other implements WxApiUrl {
        /**
         * 登录凭证校验
         */
        JSCODE_TO_SESSION_URL(API_DEFAULT_HOST_URL, "/sns/jscode2session?appid=%s&secret=%s&js_code=%s&grant_type=authorization_code"),
        /**
         * 获取 access_token
         */
        GET_ACCESS_TOKEN_URL(API_DEFAULT_HOST_URL, "/cgi-bin/token?grant_type=client_credential&appid=%s&secret=%s"),
        /**
         * 获取企业微信 access_token
         */
        GET_CP_ACCESS_TOKEN_URL(API_CP_HOST_URL, "/cgi-bin/gettoken?corpid=%s&corpsecret=%s");

        /**
         * 前缀
         */
        private final String prefix;
        /**
         * 路径
         */
        private final String path;

    }

    /**
     * 模板消息 API 地址枚举
     */
    @Getter
    @AllArgsConstructor
    enum Message implements WxApiUrl {
        /**
         * 推送公众号模板消息
         */
        MESSAGE_TEMPLATE_SEND(API_DEFAULT_HOST_URL, "/cgi-bin/message/template/send"),
        /**
         * 推送订阅模板消息
         */
        MESSAGE_TEMPLATE_SUBSCRIBE_SEND(API_DEFAULT_HOST_URL, "/cgi-bin/message/subscribe/bizsend"),
        /**
         * 企业微信推送应用消息
         */
        MESSAGE_CP_APPLY_SEND(API_CP_HOST_URL, "/cgi-bin/message/send");

        /**
         * 前缀
         */
        private final String prefix;
        /**
         * 路径
         */
        private final String path;

    }

}
