package tt.smart.agency.message.service.wx;

import cn.hutool.core.util.StrUtil;
import tt.smart.agency.message.config.properties.wx.WxMpProperties;
import tt.smart.agency.message.domain.wx.mp.WxMpMessageSendResult;
import tt.smart.agency.message.enums.wx.WxApiUrl;
import tt.smart.agency.message.service.AbstractMessageBlend;

/**
 * <p>
 * 抽象的微信消息服务
 * </p>
 *
 * @author MC_Yang
 * @version V1.0
 **/
public abstract class AbstractWxMpMessage<R extends WxMpMessageSendResult> extends AbstractMessageBlend<R, WxMpProperties> {

    protected WxMpProperties wxConfig;

    @Override
    public WxMpProperties getConfig() {
        return wxConfig;
    }

    @Override
    public String getAccessToken(String ident) {
        if (StrUtil.isBlank(ident)) {
            ident = wxConfig.getAppId();
        }
        String url = WxApiUrl.Other.GET_ACCESS_TOKEN_URL.getUrl();
        String unbrokenUrl = String.format(url, wxConfig.getAppId(), wxConfig.getSecret());
        return super.getAccessToken(unbrokenUrl, ident);
    }
}