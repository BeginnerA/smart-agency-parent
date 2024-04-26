package tt.smart.agency.message.service.sms;

import cn.hutool.core.util.HexUtil;
import cn.hutool.crypto.digest.DigestUtil;
import cn.hutool.crypto.digest.HMac;
import cn.hutool.crypto.digest.HmacAlgorithm;
import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import tt.smart.agency.message.api.sms.TencentSms;
import tt.smart.agency.message.config.properties.sms.TencentProperties;
import tt.smart.agency.message.domain.MessageSendBlend;
import tt.smart.agency.message.domain.sms.SmsResponseResult;
import tt.smart.agency.message.exception.SmsMessageException;

import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * <p>
 * 腾讯云短信服务实现
 * </p>
 *
 * @author YangMC
 * @version V1.0
 **/
public class TencentSmsImpl extends AbstractSmsMessage<SmsResponseResult, TencentProperties> implements TencentSms {

    /**
     * 加密方式
     */
    private static final String ALGORITHM = "TC3-HMAC-SHA256";
    protected final TencentProperties tencentSmsConfig;

    public TencentSmsImpl(TencentProperties tencentSmsConfig) {
        this.tencentSmsConfig = tencentSmsConfig;
        super.smsProperties = tencentSmsConfig;
    }

    @Override
    public SmsResponseResult sendMessage(String phone, String message) {
        String[] split = message.split("&");
        LinkedHashMap<String, String> map = new LinkedHashMap<>();
        for (int i = 0; i < split.length; i++) {
            map.put(String.valueOf(i), split[i]);
        }
        return sendMessage(phone, tencentSmsConfig.getTemplateId(), map);
    }

    @Override
    public SmsResponseResult sendMessage(String phone, String templateId, LinkedHashMap<String, String> messages) {
        List<String> messageList = new ArrayList<>();
        for (Map.Entry<String, String> entry : messages.entrySet()) {
            messageList.add(entry.getValue());
        }
        List<String> phones = List.of(phone);
        String timestamp = String.valueOf(System.currentTimeMillis() / 1000);
        MessageSendBlend messageSend;
        try {
            tencentSmsConfig.checkIntegrity();
            String signature = generateSignature(templateId, messageList, phones, timestamp);
            Map<String, List<String>> headers = signHeader(signature, timestamp, tencentSmsConfig.getAction(),
                    tencentSmsConfig.getVersion(), tencentSmsConfig.getTerritory(), tencentSmsConfig.getRequestUrl());
            Map<String, Object> requestBody = generateRequestBody(phones, tencentSmsConfig.getSdkAppId(),
                    tencentSmsConfig.getSignature(), templateId, messageList);
            String url = HTTPS_PREFIX + tencentSmsConfig.getRequestUrl();
            messageSend = MessageSendBlend.builder().url(url).headers(headers).paramMap(requestBody).build();
        } catch (Exception e) {
            throw new SmsMessageException("腾讯云短信推送消息错误：" + e.getMessage());
        }
        return super.sendMessage(messageSend);
    }

    /**
     * 生成腾讯云短信请求头 MAP
     *
     * @param authorization 签名信息
     * @param timestamp     时间戳
     * @param action        接口名称
     * @param version       接口版本
     * @param territory     服务器地区
     * @param requestUrl    请求地址
     * @return map
     */
    private Map<String, List<String>> signHeader(String authorization, String timestamp, String action,
                                                 String version, String territory, String requestUrl) {
        Map<String, List<String>> headers = new HashMap<>(7);
        headers.put("Authorization", List.of(authorization));
        headers.put("Content-Type", List.of(FROM_JSON));
        headers.put("Host", List.of(requestUrl));
        headers.put("X-TC-Action", List.of(action));
        headers.put("X-TC-Timestamp", List.of(timestamp));
        headers.put("X-TC-Version", List.of(version));
        headers.put("X-TC-Region", List.of(territory));
        return headers;
    }

    /**
     * 生成腾讯云短信请求 Body 参数
     *
     * @param phones           手机号
     * @param sdkAppId         appid
     * @param signatureName    短信签名
     * @param templateId       模板 ID
     * @param templateParamSet 模板参数
     * @return body
     */
    private Map<String, Object> generateRequestBody(List<String> phones, String sdkAppId, String signatureName,
                                                    String templateId, List<String> templateParamSet) {
        Map<String, Object> requestBody = new HashMap<>(5);
        requestBody.put("PhoneNumberSet", phones.toArray(new String[0]));
        requestBody.put("SmsSdkAppId", sdkAppId);
        requestBody.put("SignName", signatureName);
        requestBody.put("TemplateId", templateId);
        requestBody.put("TemplateParamSet", templateParamSet.toArray(new String[0]));
        return requestBody;
    }

    /**
     * 生成腾讯云推送短信接口签名
     *
     * @param templateId 模板 ID
     * @param messages   短信内容
     * @param phones     手机号
     * @param timestamp  时间戳
     */
    private String generateSignature(String templateId, List<String> messages, List<String> phones, String timestamp) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
        String date = sdf.format(new Date(Long.parseLong(timestamp + "000")));
        String canonicalUri = "/";
        String canonicalQueryString = "";
        String canonicalHeaders = "content-type:application/json; charset=utf-8\nhost:" + tencentSmsConfig.getRequestUrl() + "\n";
        String signedHeaders = "content-type;host";
        Map<String, Object> params = new HashMap<>(5);
        params.put("PhoneNumberSet", phones.toArray(new String[0]));
        params.put("SmsSdkAppId", tencentSmsConfig.getSdkAppId());
        params.put("SignName", tencentSmsConfig.getSignature());
        params.put("TemplateId", templateId);
        params.put("TemplateParamSet", messages.toArray(new String[0]));
        String payload = JSONUtil.toJsonStr(params);
        String hashedRequestPayload = DigestUtil.sha256Hex(payload);
        String canonicalRequest = "POST\n" + canonicalUri + "\n" + canonicalQueryString + "\n" + canonicalHeaders + "\n" + signedHeaders + "\n" + hashedRequestPayload;
        String credentialScope = date + "/" + tencentSmsConfig.getService() + "/tc3_request";
        String hashedCanonicalRequest = DigestUtil.sha256Hex(canonicalRequest);
        String stringToSign = ALGORITHM + "\n" + timestamp + "\n" + credentialScope + "\n" + hashedCanonicalRequest;
        byte[] secretDate = hmac256(("TC3" + tencentSmsConfig.getAccessKeySecret()).getBytes(StandardCharsets.UTF_8), date);
        byte[] secretService = hmac256(secretDate, tencentSmsConfig.getService());
        byte[] secretSigning = hmac256(secretService, "tc3_request");
        String signature = HexUtil.encodeHexStr(hmac256(secretSigning, stringToSign)).toLowerCase();
        return ALGORITHM + " Credential=" + tencentSmsConfig.getAccessKey() + "/" + credentialScope + ", SignedHeaders=" + signedHeaders + ", Signature=" + signature;
    }

    /**
     * Hmac256 加密
     *
     * @param key key
     * @param msg 消息
     * @return 加密结果
     */
    private byte[] hmac256(byte[] key, String msg) {
        HMac hMac = new HMac(HmacAlgorithm.HmacSHA256, key);
        return hMac.digest(msg.getBytes(StandardCharsets.UTF_8));
    }

    @Override
    protected SmsResponseResult handleResponseResult(JSONObject resultBody) {
        SmsResponseResult result = new SmsResponseResult();
        JSONObject response = resultBody.getJSONObject("Response");
        JSONObject error = response.getJSONObject("Error");
        if (error != null) {
            result.setCode(error.getString("Code"));
            result.setMessage(error.getString("Message"));
        } else {
            JSONArray sendStatusSet = response.getJSONArray("SendStatusSet");
            result.setCode(sendStatusSet.getJSONObject(0).getString("Code"));
            result.setMessage(sendStatusSet.getJSONObject(0).getString("Message"));
            result.setSuccess(true);
        }
        return result;
    }
}