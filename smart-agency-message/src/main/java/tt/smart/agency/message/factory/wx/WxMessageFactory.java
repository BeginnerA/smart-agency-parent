package tt.smart.agency.message.factory.wx;

import tt.smart.agency.message.api.wx.WxMessageBlend;
import tt.smart.agency.message.config.properties.wx.WxConfig;
import tt.smart.agency.message.domain.wx.WxBaseMessage;
import tt.smart.agency.message.domain.wx.WxBaseMessageSendResult;
import tt.smart.agency.message.domain.wx.cp.WxCpMessage;
import tt.smart.agency.message.domain.wx.cp.WxCpMessageSendResult;
import tt.smart.agency.message.domain.wx.mp.WxMpBaseMessage;
import tt.smart.agency.message.domain.wx.mp.WxMpMessageSendResult;
import tt.smart.agency.message.enums.PlatformType;
import tt.smart.agency.message.enums.wx.WxPlatformType;

/**
 * <p>
 * 微信消息工厂
 * </p>
 *
 * @author MC_Yang
 * @version V1.0
 **/
public class WxMessageFactory {

    /**
     * 创建微信消息服务
     *
     * @param platformType 微信平台类型
     * @return 微信消息服务
     */
    @SuppressWarnings(value = {"unchecked", "rawtypes"})
    public static WxMessageBlend<WxBaseMessageSendResult, WxBaseMessage> createWxMessageBlend(PlatformType platformType) {
        BaseWxPlatformFactory platformFactory = platformType.getPlatformFactory();
        return platformFactory.createWxMessage((WxConfig) platformFactory.buildConfig());
    }

    /**
     * 创建微信消息服务
     *
     * @param platformType 微信平台类型
     * @return 微信消息服务
     */
    @SuppressWarnings(value = {"unchecked", "rawtypes"})
    public static WxMessageBlend<WxBaseMessageSendResult, WxBaseMessage> createWxMessageBlend(WxPlatformType platformType) {
        BaseWxPlatformFactory platformFactory = platformType.getPlatformFactory();
        return platformFactory.createWxMessage((WxConfig) platformFactory.buildConfig());
    }

    /**
     * 创建企业微信消息服务
     *
     * @return 微信消息服务
     */
    @SuppressWarnings(value = {"unchecked", "rawtypes"})
    public static WxMessageBlend<WxCpMessageSendResult, WxCpMessage> createWxCpMessageBlend() {
        BaseWxPlatformFactory platformFactory = WxPlatformType.CP_APPLY.getPlatformFactory();
        return platformFactory.createWxMessage((WxConfig) platformFactory.buildConfig());
    }

    /**
     * 创建微信公众号消息服务
     *
     * @return 微信公众号消息服务
     */
    @SuppressWarnings(value = {"unchecked", "rawtypes"})
    public static WxMessageBlend<WxMpMessageSendResult, WxMpBaseMessage> createWxMpMessageBlend() {
        BaseWxPlatformFactory platformFactory = WxPlatformType.MP.getPlatformFactory();
        return platformFactory.createWxMessage((WxConfig) platformFactory.buildConfig());
    }

}
