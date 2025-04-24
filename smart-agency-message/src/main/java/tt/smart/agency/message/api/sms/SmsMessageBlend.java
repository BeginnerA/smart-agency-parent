package tt.smart.agency.message.api.sms;

import org.jetbrains.annotations.NotNull;
import tt.smart.agency.message.domain.sms.SmsResponseResult;

import java.util.LinkedHashMap;

/**
 * <p>
 * 通用短信服务
 * </p>
 *
 * @author MC_Yang
 * @version V1.0
 **/
public interface SmsMessageBlend {

    /**
     * 推送消息
     *
     * @param phone   接收短信的手机号
     * @param message 消息内容
     * @return 响应结果
     */
    SmsResponseResult sendMessage(@NotNull String phone, @NotNull String message);

    /**
     * 推送消息
     *
     * @param phone      接收短信的手机号
     * @param templateId 消息模板 ID
     * @param messages   消息模板参数。key 为模板变量名称 value 为模板变量值
     * @return 响应结果
     */
    SmsResponseResult sendMessage(@NotNull String phone, @NotNull String templateId, LinkedHashMap<String, String> messages);

}