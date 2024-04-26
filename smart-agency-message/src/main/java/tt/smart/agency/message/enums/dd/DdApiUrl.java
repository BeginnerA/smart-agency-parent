package tt.smart.agency.message.enums.dd;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * <p>
 * 微信公众号接口 API 地址
 * </p>
 *
 * @author YangMC
 * @version V1.0
 **/
public interface DdApiUrl {

    /**
     * 默认钉钉 URL
     */
    String API_DEFAULT_HOST_URL = "https://oapi.dingtalk.com";

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


    @Getter
    @AllArgsConstructor
    enum Other implements DdApiUrl {
        /**
         * 获取 access_token
         */
        GET_ACCESS_TOKEN_URL(API_DEFAULT_HOST_URL, "/gettoken?appkey=%s&appsecret=%s");

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
    enum Message implements DdApiUrl {
        /**
         * 推送公众号模板消息
         */
        MESSAGE_TEMPLATE_SEND(API_DEFAULT_HOST_URL, "/topapi/message/corpconversation/asyncsend_v2");

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
