package tt.smart.agency.message.service.sms;

import cn.hutool.core.codec.Base64;
import cn.hutool.core.convert.Convert;
import cn.hutool.crypto.digest.DigestUtil;
import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson2.JSONObject;
import org.jetbrains.annotations.NotNull;
import tt.smart.agency.message.api.sms.MasSms;
import tt.smart.agency.message.config.properties.sms.MasProperties;
import tt.smart.agency.message.domain.MessageSendBlend;
import tt.smart.agency.message.domain.sms.SmsResponseResult;
import tt.smart.agency.message.domain.sms.mas.MasSendReq;
import tt.smart.agency.message.enums.sms.MasSmsType;
import tt.smart.agency.message.exception.SmsMessageException;

import java.util.Collection;
import java.util.LinkedHashMap;

/**
 * <p>
 * 移动云短信服务实现
 * </p>
 *
 * @author MC_Yang
 * @version V1.0
 **/
public class MasSmsImpl extends AbstractSmsMessage<SmsResponseResult, MasProperties> implements MasSms {

    protected final MasProperties masConfig;

    public MasSmsImpl(MasProperties masConfig) {
        this.masConfig = masConfig;
        super.smsProperties = masConfig;
    }

    @Override
    public SmsResponseResult sendMessage(@NotNull String phone, @NotNull String message) {
        MasSendReq req = MasSendReq.builder().ecName(masConfig.getEcName()).apId(masConfig.getAccessKey())
                .secretKey(masConfig.getAccessKeySecret()).mobiles(phone).content(message)
                .sign(masConfig.getSignature()).build();
        String contentMac = getContentMac(req);
        req.setMac(contentMac);
        return sendMessage(req);
    }

    @Override
    public SmsResponseResult sendMessage(@NotNull String phone, @NotNull String templateId, LinkedHashMap<String, String> messages) {
        String templateParam;
        if (messages == null) {
            templateParam = "[\"\"]";
        } else {
            Collection<String> values = messages.values();
            templateParam = JSONUtil.toJsonStr(values);
        }
        MasSendReq req = MasSendReq.builder().ecName(masConfig.getEcName()).apId(masConfig.getAccessKey())
                .secretKey(masConfig.getAccessKeySecret()).mobiles(phone).templateId(templateId).params(templateParam)
                .sign(masConfig.getSignature()).build();
        String contentMac = getTemplateMac(req);
        req.setMac(contentMac);
        return sendMessage(req);
    }

    /**
     * 发送消息
     *
     * @param req 请求
     * @return 结果
     */
    private SmsResponseResult sendMessage(@NotNull MasSendReq req) {
        MessageSendBlend messageSend;
        try {
            String url = masConfig.getRequestUrl() + masConfig.getContentAddress();
            if (MasSmsType.TEMPLATE.equals(masConfig.getSmsType())) {
                url = masConfig.getRequestUrl() + masConfig.getTemplateAddress();
            }
            String messageContent = Base64.encode(JSONUtil.toJsonStr(req));
            messageSend = MessageSendBlend.builder().url(url).messageContent(messageContent).build();
        } catch (Exception e) {
            throw new SmsMessageException("移动云短信推送消息错误：" + e.getMessage());
        }
        return super.sendMessage(messageSend);
    }

    /**
     * 从属性中获取参数签名
     *
     * @param req 发送请求
     * @return 签名
     */
    private String getTemplateMac(MasSendReq req) {
        String macData = req.getEcName() +
                req.getApId() +
                req.getSecretKey() +
                req.getTemplateId() +
                req.getMobiles() +
                req.getParams() +
                req.getSign() +
                masConfig.getAddSerial();
        return DigestUtil.md5Hex(macData).toLowerCase();
    }

    /**
     * 从属性中获取参数签名
     *
     * @param req 发送请求
     * @return 签名
     */
    private String getContentMac(MasSendReq req) {
        String macData = req.getEcName() +
                req.getApId() +
                req.getSecretKey() +
                req.getMobiles() +
                req.getContent() +
                req.getSign() +
                req.getAddSerial();
        return DigestUtil.md5Hex(macData).toLowerCase();
    }

    @Override
    protected SmsResponseResult handleResponseResult(JSONObject resultBody) {
        SmsResponseResult result = new SmsResponseResult();
        String success = Convert.toStr(resultBody.get("success"));
        result.setSuccess("true".equals(success));
        result.setCode(Convert.toStr(resultBody.get("rspcod")));
        result.setMessage(Convert.toStr(resultBody.get("msgGroup")));
        return result;
    }
}
