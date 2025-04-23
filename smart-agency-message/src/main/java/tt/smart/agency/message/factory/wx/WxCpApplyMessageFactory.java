package tt.smart.agency.message.factory.wx;

import tt.smart.agency.message.api.wx.WxCpApplyMessage;
import tt.smart.agency.message.config.properties.wx.WxCpProperties;
import tt.smart.agency.message.service.wx.WxCpApplyMessageImpl;

/**
 * <p>
 * 企业微信应用消息工厂
 * </p>
 *
 * @author MC_Yang
 * @version V1.0
 **/
public class WxCpApplyMessageFactory implements BaseWxPlatformFactory<WxCpApplyMessage, WxCpProperties> {

    private static final WxCpApplyMessageFactory INSTANCE = new WxCpApplyMessageFactory();
    private static WxCpApplyMessageImpl wxCpMessage;

    private static final class ConfigHolder {
        private static final WxCpProperties CONFIG = WxCpProperties.builder().build();
    }

    /**
     * 得到建造者实例
     *
     * @return 建造者实例
     */
    public static WxCpApplyMessageFactory instance() {
        return INSTANCE;
    }

    @Override
    public WxCpProperties buildConfig() {
        return ConfigHolder.CONFIG;
    }

    @Override
    public WxCpApplyMessageImpl createWxMessage(WxCpProperties wxProperties) {
        if (wxCpMessage == null) {
            wxCpMessage = new WxCpApplyMessageImpl(wxProperties);
        }
        return wxCpMessage;
    }
}