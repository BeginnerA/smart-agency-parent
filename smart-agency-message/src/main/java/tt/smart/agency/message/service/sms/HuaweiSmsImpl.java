package tt.smart.agency.message.service.sms;

import cn.hutool.core.codec.Base64;
import com.alibaba.fastjson2.JSONObject;
import tt.smart.agency.message.api.sms.HuaweiSms;
import tt.smart.agency.message.config.properties.sms.HuaweiProperties;
import tt.smart.agency.message.domain.MessageSendBlend;
import tt.smart.agency.message.domain.sms.SmsResponseResult;
import tt.smart.agency.message.exception.SmsMessageException;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * <p>
 * 华为云短信服务实现
 * </p>
 *
 * @author YangMC
 * @version V1.0
 **/

public class HuaweiSmsImpl extends AbstractSmsMessage<SmsResponseResult, HuaweiProperties> implements HuaweiSms {

    /**
     * 华为云国内短信访问 URI
     */
    public static final String HUAWEI_REQUEST_URL = "/sms/batchSendSms/v1";
    /**
     * 用于格式化鉴权头域,给"Authorization"参数赋值
     */
    public static final String HUAWEI_AUTH_HEADER_VALUE = "WSSE realm=\"SDP\",profile=\"UsernameToken\",type=\"Appkey\"";
    /**
     * 用于格式化鉴权头域,给"X-WSSE"参数赋值
     */
    public static final String HUAWEI_WSSE_HEADER_FORMAT = "UsernameToken Username=\"%s\",PasswordDigest=\"%s\",Nonce=\"%s\",Created=\"%s\"";

    protected final HuaweiProperties huaweiSmsConfig;

    public HuaweiSmsImpl(HuaweiProperties huaweiSmsConfig) {
        this.huaweiSmsConfig = huaweiSmsConfig;
        super.smsProperties = huaweiSmsConfig;
    }

    @Override
    public SmsResponseResult sendMessage(String phone, String message) {
        LinkedHashMap<String, String> mes = new LinkedHashMap<>();
        mes.put(UUID.randomUUID().toString().replaceAll("-", ""), message);
        return sendMessage(phone, huaweiSmsConfig.getTemplateId(), mes);
    }

    @Override
    public SmsResponseResult sendMessage(String phone, String templateId, LinkedHashMap<String, String> messages) {
        List<String> list = new ArrayList<>();
        for (Map.Entry<String, String> entry : messages.entrySet()) {
            list.add(entry.getValue());
        }
        MessageSendBlend messageSend;
        try {
            String mess = listToString(list);
            String requestBody = buildRequestBody(phone, templateId, mess);
            Map<String, List<String>> headers = new HashMap<>(3);
            headers.put("Authorization", List.of(HUAWEI_AUTH_HEADER_VALUE));
            headers.put("X-WSSE", List.of(buildWsseHeader()));
            headers.put("Content-Type", List.of(FROM_URLENCODED));
            String url = huaweiSmsConfig.getUrl() + HUAWEI_REQUEST_URL;
            messageSend = MessageSendBlend.builder().url(url).headers(headers).messageContent(requestBody).build();
        } catch (Exception e) {
            throw new SmsMessageException("华为云短信推送消息错误：" + e.getMessage());
        }
        return super.sendMessage(messageSend);
    }

    /**
     * 构造 X-WSSE 参数值
     *
     * @return X-WSSE 参数值
     */
    private String buildWsseHeader() {
        String appKey = huaweiSmsConfig.getAccessKey();
        String appSecret = huaweiSmsConfig.getAccessKeySecret();
        String time = new SimpleDateFormat(JAVA_DATE).format(new Date());
        String nonce = UUID.randomUUID().toString().replace("-", "");
        byte[] passwordDigest;
        try {
            huaweiSmsConfig.checkIntegrity();
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update((nonce + time + appSecret).getBytes());
            passwordDigest = md.digest();
        } catch (NoSuchAlgorithmException e) {
            throw new SmsMessageException("华为云短信构造 X-WSSE 参数值失败");
        }

        String passwordDigestBase64Str = Base64.encode(passwordDigest);
        return String.format(HUAWEI_WSSE_HEADER_FORMAT, appKey, passwordDigestBase64Str, nonce, time);
    }

    /**
     * 构造请求 Body 参数
     *
     * @param receiver      短信接收者
     * @param templateId    短信模板id
     * @param templateParas 模板参数
     */
    private String buildRequestBody(String receiver, String templateId, String templateParas) {
        String sender = huaweiSmsConfig.getSender();
        String statusCallBack = huaweiSmsConfig.getStatusCallBack();
        String signature = huaweiSmsConfig.getSignature();
        Map<String, String> map = new HashMap<>(6);

        map.put("from", sender);
        map.put("to", receiver);
        map.put("templateId", templateId);
        if (null != templateParas && !templateParas.isEmpty()) {
            map.put("templateParas", templateParas);
        }
        if (null != statusCallBack && !statusCallBack.isEmpty()) {
            map.put("statusCallback", statusCallBack);
        }
        if (null != signature && !signature.isEmpty()) {
            map.put("signature", signature);
        }

        StringBuilder sb = new StringBuilder();
        String temp;

        for (String s : map.keySet()) {
            temp = URLEncoder.encode(map.get(s), StandardCharsets.UTF_8);
            sb.append(s).append("=").append(temp).append("&");
        }

        return sb.deleteCharAt(sb.length() - 1).toString();
    }

    /**
     * 消息模板值处理
     *
     * @param list 消息模板值
     * @return 结果
     */
    private String listToString(List<String> list) {
        StringBuilder stringBuffer = new StringBuilder();
        stringBuffer.append("[\"");
        for (String s : list) {
            stringBuffer.append(s);
            stringBuffer.append("\"");
            stringBuffer.append(",");
            stringBuffer.append("\"");
        }
        stringBuffer.delete(stringBuffer.length() - 3, stringBuffer.length() - 1);
        stringBuffer.append("]");
        return stringBuffer.toString();
    }

    @Override
    protected SmsResponseResult handleResponseResult(JSONObject resultBody) {
        SmsResponseResult result = new SmsResponseResult();
        result.setCode(resultBody.getString("code"));
        result.setMessage(resultBody.getString("description"));
        if ("000000".equals(result.getCode())) {
            result.setSuccess(true);
        }
        return result;
    }
}