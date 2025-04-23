package tt.smart.agency.message.service.sms;

import cn.hutool.crypto.digest.HMac;
import cn.hutool.crypto.digest.HmacAlgorithm;
import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson2.JSONObject;
import tt.smart.agency.message.api.sms.AlibabaSms;
import tt.smart.agency.message.config.properties.sms.AlibabaProperties;
import tt.smart.agency.message.domain.MessageSendBlend;
import tt.smart.agency.message.domain.sms.SmsResponseResult;
import tt.smart.agency.message.exception.SmsMessageException;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * <p>
 * 阿里云短信服务实现
 * </p>
 *
 * @author MC_Yang
 * @version V1.0
 **/
public class AlibabaSmsImpl extends AbstractSmsMessage<SmsResponseResult, AlibabaProperties> implements AlibabaSms {

    protected final AlibabaProperties alibabaSmsConfig;

    public AlibabaSmsImpl(AlibabaProperties alibabaSmsConfig) {
        this.alibabaSmsConfig = alibabaSmsConfig;
        super.smsProperties = alibabaSmsConfig;
    }

    @Override
    public SmsResponseResult sendMessage(String phone, String message) {
        LinkedHashMap<String, String> map = new LinkedHashMap<>();
        map.put(alibabaSmsConfig.getTemplateName(), message);
        return sendMessage(phone, alibabaSmsConfig.getTemplateId(), map);
    }

    @Override
    public SmsResponseResult sendMessage(String phone, String templateId, LinkedHashMap<String, String> messages) {
        String messageStr = JSONUtil.toJsonStr(messages);
        return smsResponse(phone, messageStr, templateId);
    }

    /**
     * 推送
     *
     * @param phone      接收短信的手机号
     * @param message    消息
     * @param templateId 消息模板 ID
     * @return 结果
     */
    private SmsResponseResult smsResponse(String phone, String message, String templateId) {
        MessageSendBlend messageSend;
        try {
            alibabaSmsConfig.checkIntegrity();
            String requestUrl = getSendSmsRequestUrl(message, phone, templateId);
            String paramStr = generateParamBody(phone, message, templateId);
            Map<String, List<String>> headers = new HashMap<>(1);
            headers.put("Content-Type", List.of(FROM_URLENCODED));
            messageSend = MessageSendBlend.builder().url(requestUrl).headers(headers).messageContent(paramStr).build();
        } catch (Exception e) {
            throw new SmsMessageException("阿里云短信推送消息错误：" + e.getMessage());
        }
        return super.sendMessage(messageSend);
    }

    /**
     * 获取推送短信请求地址
     *
     * @param message    消息
     * @param phone      接收短信的手机号
     * @param templateId 消息模板 ID
     * @return 请求地址
     */
    private String getSendSmsRequestUrl(String message, String phone, String templateId) {
        // 这里一定要设置 GMT 时区
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(JAVA_DATE);
        simpleDateFormat.setTimeZone(new SimpleTimeZone(0, "GMT"));
        Map<String, String> paras = new HashMap<>(16);
        // 公共请求参数
        paras.put("SignatureMethod", "HMAC-SHA1");
        paras.put("SignatureNonce", UUID.randomUUID().toString());
        paras.put("AccessKeyId", alibabaSmsConfig.getAccessKey());
        paras.put("SignatureVersion", "1.0");
        paras.put("Timestamp", simpleDateFormat.format(new Date()));
        paras.put("Format", "JSON");
        paras.put("Action", alibabaSmsConfig.getAction());
        paras.put("version", alibabaSmsConfig.getVersion());
        paras.put("RegionId", alibabaSmsConfig.getRegionId());
        // 业务 API 参数
        Map<String, String> paramMap = generateParamMap(phone, message, templateId);
        // 参数 KEY 排序
        Map<String, String> sortParas = new TreeMap<>(paras);
        sortParas.putAll(paramMap);
        // 构造待签名的字符串
        Iterator<String> it = sortParas.keySet().iterator();
        StringBuilder sortQueryStringTmp = new StringBuilder();
        while (it.hasNext()) {
            String key = it.next();
            sortQueryStringTmp.append("&").append(specialUrlEncode(key)).append("=").append(specialUrlEncode(sortParas.get(key)));
        }
        // 加密请求
        String stringToSign = "POST" + "&" +
                specialUrlEncode("/") + "&" +
                specialUrlEncode(sortQueryStringTmp.substring(1));
        String accessSecret = alibabaSmsConfig.getAccessKeySecret() + "&";
        HMac hMac = new HMac(HmacAlgorithm.HmacSHA1, accessSecret.getBytes());
        String signature = hMac.digestBase64(stringToSign, StandardCharsets.UTF_8, false);
        // 生成请求的 url 参数
        StringBuilder sortQueryString = new StringBuilder();
        it = paras.keySet().iterator();
        while (it.hasNext()) {
            String key = it.next();
            sortQueryString.append("&").append(specialUrlEncode(key)).append("=").append(specialUrlEncode(paras.get(key)));
        }
        // 生成合法请求URL
        return alibabaSmsConfig.getRequestUrl() + "/?Signature=" + specialUrlEncode(signature) + sortQueryString;
    }

    /**
     * 生成请求参数 Body 参数
     *
     * @param phone      手机号
     * @param message    短信内容
     * @param templateId 模板 ID
     */
    private String generateParamBody(String phone, String message, String templateId) {
        Map<String, String> paramMap = generateParamMap(phone, message, templateId);
        StringBuilder sortQueryString = new StringBuilder();
        for (String key : paramMap.keySet()) {
            sortQueryString.append("&").append(specialUrlEncode(key)).append("=")
                    .append(specialUrlEncode(paramMap.get(key)));
        }
        return sortQueryString.substring(1);
    }

    /**
     * 生成请求 Body 参数
     *
     * @param phone      手机号
     * @param message    短信内容
     * @param templateId 模板 ID
     */
    private Map<String, String> generateParamMap(String phone, String message, String templateId) {
        Map<String, String> paramMap = new HashMap<>(16);
        paramMap.put("PhoneNumbers", phone);
        paramMap.put("SignName", alibabaSmsConfig.getSignature());
        paramMap.put("TemplateParam", message);
        paramMap.put("TemplateCode", templateId);
        return paramMap;
    }

    /**
     * url 编码
     *
     * @param value 值
     * @return url 编码
     */
    private String specialUrlEncode(String value) {
        return URLEncoder.encode(value, StandardCharsets.UTF_8).replace("+", "%20")
                .replace("*", "%2A").replace("%7E", "~");
    }

    @Override
    protected SmsResponseResult handleResponseResult(JSONObject resultBody) {
        SmsResponseResult result = new SmsResponseResult();
        result.setCode(resultBody.getString("Code"));
        result.setMessage(resultBody.getString("Message"));
        if (OK.equalsIgnoreCase(result.getCode())) {
            result.setSuccess(true);
        }
        return result;
    }
}
