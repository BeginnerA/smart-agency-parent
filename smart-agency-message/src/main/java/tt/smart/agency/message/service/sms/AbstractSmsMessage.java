package tt.smart.agency.message.service.sms;

import tt.smart.agency.message.config.properties.sms.BaseSmsProperties;
import tt.smart.agency.message.domain.sms.SmsResponseResult;
import tt.smart.agency.message.service.AbstractMessageBlend;

/**
 * <p>
 * 抽象的通用短信服务
 * </p>
 *
 * @param <R> 消息推送返回结果对象
 * @param <C> 消息配置对象
 * @author MC_Yang
 * @version V1.0
 **/
public abstract class AbstractSmsMessage<R extends SmsResponseResult, C extends BaseSmsProperties>
        extends AbstractMessageBlend<R, C> {

    protected C smsProperties;

    @Override
    public C getConfig() {
        return smsProperties;
    }

    @Override
    public String getAccessToken(String ident) {
        return null;
    }

}
