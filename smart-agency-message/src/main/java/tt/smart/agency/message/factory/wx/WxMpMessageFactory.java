package tt.smart.agency.message.factory.wx;

import tt.smart.agency.message.api.wx.WxMpMessage;
import tt.smart.agency.message.config.properties.wx.WxMpProperties;
import tt.smart.agency.message.service.wx.WxMpMessageImpl;

/**
 * <p>
 * 微信公众号消息工厂
 * </p>
 *
 * @author MC_Yang
 * @version V1.0
 **/
public class WxMpMessageFactory implements BaseWxPlatformFactory<WxMpMessage, WxMpProperties> {

    private static final WxMpMessageFactory INSTANCE = new WxMpMessageFactory();
    private static WxMpMessageImpl wxMpMessage;

    private static final class ConfigHolder {
        private static final WxMpProperties CONFIG = WxMpProperties.builder().build();
    }

    /**
     * 得到建造者实例
     *
     * @return 建造者实例
     */
    public static WxMpMessageFactory instance() {
        return INSTANCE;
    }

    @Override
    public WxMpProperties buildConfig() {
        return ConfigHolder.CONFIG;
    }

    @Override
    public WxMpMessageImpl createWxMessage(WxMpProperties wxMpProperties) {
        if (wxMpMessage == null) {
            wxMpMessage = new WxMpMessageImpl(wxMpProperties);
        }
        return wxMpMessage;
    }
}