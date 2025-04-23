package tt.smart.agency.message.service.dd;

import cn.hutool.core.util.StrUtil;
import tt.smart.agency.message.config.properties.dd.DdProperties;
import tt.smart.agency.message.domain.dd.DdMessageSendResult;
import tt.smart.agency.message.enums.dd.DdApiUrl;
import tt.smart.agency.message.service.AbstractMessageBlend;

/**
 * <p>
 * 抽象的钉钉消息服务
 * </p>
 *
 * @param <R> 消息推送返回结果对象
 * @author MC_Yang
 * @version V1.0
 **/
public abstract class AbstractDdMessage<R extends DdMessageSendResult> extends AbstractMessageBlend<R, DdProperties> {
    protected DdProperties ddMessageConfig;

    @Override
    public DdProperties getConfig() {
        return ddMessageConfig;
    }

    @Override
    public String getAccessToken(String ident) {
        if (StrUtil.isBlank(ident)) {
            ident = ddMessageConfig.getAppKey();
        }
        String url = DdApiUrl.Other.GET_ACCESS_TOKEN_URL.getUrl();
        String unbrokenUrl = String.format(url, ddMessageConfig.getAppKey(), ddMessageConfig.getAppSecret());
        return super.getAccessToken(unbrokenUrl, ident);
    }

}