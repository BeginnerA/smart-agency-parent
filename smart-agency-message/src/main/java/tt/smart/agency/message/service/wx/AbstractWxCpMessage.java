package tt.smart.agency.message.service.wx;

import cn.hutool.core.util.StrUtil;
import tt.smart.agency.message.config.properties.wx.WxCpProperties;
import tt.smart.agency.message.domain.wx.cp.WxCpMessageSendResult;
import tt.smart.agency.message.enums.wx.WxApiUrl;
import tt.smart.agency.message.service.AbstractMessageBlend;

/**
 * <p>
 * 抽象的企业微信消息服务
 * </p>
 *
 * @author YangMC
 * @version V1.0
 **/
public abstract class AbstractWxCpMessage<R extends WxCpMessageSendResult> extends AbstractMessageBlend<R, WxCpProperties> {

    protected WxCpProperties wxCpConfig;

    @Override
    public WxCpProperties getConfig() {
        return wxCpConfig;
    }

    @Override
    public String getAccessToken(String ident) {
        if (StrUtil.isBlank(ident)) {
            ident = wxCpConfig.getCorpId();
        }
        String url = WxApiUrl.Other.GET_ACCESS_TOKEN_URL.getUrl();
        String unbrokenUrl = String.format(url, wxCpConfig.getCorpId(), wxCpConfig.getCorpSecret());
        return super.getAccessToken(unbrokenUrl, ident);
    }
}