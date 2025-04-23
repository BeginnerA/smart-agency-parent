package tt.smart.agency.message.factory.wx;

import tt.smart.agency.message.api.wx.WxMessageBlend;
import tt.smart.agency.message.config.properties.wx.WxConfig;
import tt.smart.agency.message.factory.InitConfigFactory;

/**
 * <p>
 * 基础微信平台工厂
 * </p>
 *
 * @author MC_Yang
 * @version V1.0
 **/
@SuppressWarnings(value = {"rawtypes"})
public interface BaseWxPlatformFactory<S extends WxMessageBlend, C extends WxConfig> extends InitConfigFactory<C> {

    /**
     * 创建微信消息实例
     *
     * @param c 微信消息配置
     * @return 微信消息实例
     */
    S createWxMessage(C c);

}