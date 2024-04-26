package tt.smart.agency.message.component.strategy;

import lombok.AllArgsConstructor;
import lombok.Getter;
import tt.smart.agency.message.enums.PlatformType;
import tt.smart.agency.message.enums.dd.DdPlatformType;
import tt.smart.agency.message.enums.sms.SupplierPlatformType;
import tt.smart.agency.message.enums.wx.WxPlatformType;

/**
 * <p>
 * 消息推送平台枚举
 * </p>
 *
 * @author YangMC
 * @version V1.0
 **/
@Getter
@AllArgsConstructor
public enum MessagePushPlatformEnum {
    /**
     * Netty 网络消息推送
     */
    NETTY("NETTY", "Netty 网络消息推送"),
    /**
     * webSocket 消息推送
     */
    WEB_SOCKET("WEB_SOCKET", "webSocket 消息推送"),
    /**
     * SOCKET_IO 消息推送
     */
    SOCKET_IO("SOCKET_IO", "SOCKET_IO 消息推送"),
    /**
     * 微信公众号模板消息推送
     */
    MP_TEMPLATE("MP_TEMPLATE", "微信公众号模板消息推送"),
    /**
     * 微信公众号订阅消息推送
     */
    MP_SUBSCRIBE("MP_SUBSCRIBE", "微信公众号推送"),
    /**
     * 企业微信应用消息推送
     */
    CP_APPLY("CP_APPLY", "企业微信应用消息推送"),
    /**
     * 钉钉应用消息推送
     */
    DD_APPLY("DD_APPLY", "钉钉应用消息推送"),
    /**
     * 阿里云短信消息推送
     */
    ALIBABA("ALIBABA", "阿里云短信消息推送"),
    /**
     * 华为云短信消息推送
     */
    HUAWEI("HUAWEI", "华为云短信消息推送"),
    /**
     * 腾讯云短信消息推送
     */
    TENCENT("TENCENT", "腾讯云短信消息推送"),
    /**
     * 天翼云短信消息推送
     */
    CTYUN("CTYUN", "天翼云短信消息推送"),
    /**
     * 网易云短信消息推送
     */
    NETEASE("NETEASE", "网易云短信消息推送"),
    /**
     * 自定义消息推送
     */
    CUSTOM("CUSTOM", "自定义消息推送"),
    ;

    /**
     * 代码
     */
    private final String code;
    /**
     * 代码描述
     */
    private final String description;

    /**
     * 得到消息推送平台枚举
     *
     * @param code 推送平台代码
     * @return 推送平台枚举
     */
    public static MessagePushPlatformEnum getPlatformEnum(String code) {
        for (MessagePushPlatformEnum value : MessagePushPlatformEnum.values()) {
            if (value.getCode().equalsIgnoreCase(code)) {
                return value;
            }
        }
        return null;
    }

    /**
     * 得到推送平台类型工厂
     *
     * @param platform 消息推送平台
     * @return 推送平台类型工厂
     */
    public PlatformType getPlatformTypeFactory(MessagePushPlatformEnum platform) {
        PlatformType platformType = null;
        switch (platform) {
            case NETTY:
                System.out.print("Netty 待实现");
                break;
            case WEB_SOCKET:
                System.out.print("WEB_SOCKET 待实现");
                break;
            case SOCKET_IO:
                System.out.print("SOCKET_IO 待实现");
                break;
            case MP_TEMPLATE:
                platformType = WxPlatformType.MP_TEMPLATE;
                break;
            case MP_SUBSCRIBE:
                platformType = WxPlatformType.MP_SUBSCRIBE;
                break;
            case CP_APPLY:
                platformType = WxPlatformType.CP_APPLY;
                break;
            case DD_APPLY:
                platformType = DdPlatformType.DD_APPLY;
                break;
            case ALIBABA:
                platformType = SupplierPlatformType.ALIBABA;
                break;
            case HUAWEI:
                platformType = SupplierPlatformType.HUAWEI;
                break;
            case TENCENT:
                platformType = SupplierPlatformType.TENCENT;
                break;
            case CTYUN:
                platformType = SupplierPlatformType.CTYUN;
                break;
            case NETEASE:
                platformType = SupplierPlatformType.NETEASE;
                break;
            case CUSTOM:
                break;
            default:
                System.out.print("未找到推送平台");
                break;
        }
        return platformType;
    }

}