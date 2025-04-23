package tt.smart.agency.message.enums.wx;

import lombok.AllArgsConstructor;
import lombok.Getter;
import tt.smart.agency.message.api.wx.WxMessageBlend;
import tt.smart.agency.message.config.properties.wx.WxConfig;
import tt.smart.agency.message.enums.PlatformType;
import tt.smart.agency.message.factory.wx.BaseWxPlatformFactory;
import tt.smart.agency.message.factory.wx.WxCpApplyMessageFactory;
import tt.smart.agency.message.factory.wx.WxMpMessageFactory;

/**
 * <p>
 * 微信平台类型
 * </p>
 *
 * @author MC_Yang
 * @version V1.0
 **/
@Getter
@AllArgsConstructor
public enum WxPlatformType implements PlatformType {
    /**
     * 微信公众号消息
     */
    MP("微信公众号消息", WxMpMessageFactory.instance()),
    /**
     * 微信公众号模板消息
     */
    MP_TEMPLATE("微信公众号模板消息", WxMpMessageFactory.instance()),
    /**
     * 微信公众号订阅消息
     */
    MP_SUBSCRIBE("微信公众号订阅消息", WxMpMessageFactory.instance()),
    /**
     * 企业微信应用消息
     */
    CP_APPLY("企业微信消息", WxCpApplyMessageFactory.instance());

    /**
     * 微信平台名称
     */
    private final String name;

    /**
     * 微信消息平台工厂
     */
    @SuppressWarnings(value = {"rawtypes"})
    private final BaseWxPlatformFactory<? extends WxMessageBlend, ? extends WxConfig> platformFactory;

}