package tt.smart.agency.message.service;

import com.alibaba.fastjson2.JSONObject;
import tt.smart.agency.message.config.properties.Config;
import tt.smart.agency.message.domain.MessageSendBlend;
import tt.smart.agency.message.domain.MessageSendResult;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * <p>
 * 抽象的消息接口服务
 * </p>
 *
 * @param <R> 消息推送返回结果对象
 * @param <C> 消息配置对象
 * @author YangMC
 * @version V1.0
 **/
public abstract class AbstractMessageBlend<R extends MessageSendResult, C extends Config> extends AbstractAccessTokenBlend<C> implements MessageBlend<R> {

    @Override
    public R sendMessage(MessageSendBlend sendBlend) {
        String responseContent = super.execute(sendBlend);
        R result = handleResponseResult(JSONObject.parseObject(responseContent));
        if (result == null) {
            Type genType = this.getClass().getGenericSuperclass();
            if (genType instanceof ParameterizedType) {
                Type[] params = ((ParameterizedType) genType).getActualTypeArguments();
                result = JSONObject.parseObject(responseContent, params[0]);
                result.setRawResult(responseContent);
            }
        } else {
            if (result.getRawResult() == null) {
                result.setRawResult(responseContent);
            }
        }
        return result;
    }

    /**
     * 封装请求响应结果
     *
     * @param resultBody 请求响应结果
     * @return 响应结果
     */
    protected abstract R handleResponseResult(JSONObject resultBody);

}