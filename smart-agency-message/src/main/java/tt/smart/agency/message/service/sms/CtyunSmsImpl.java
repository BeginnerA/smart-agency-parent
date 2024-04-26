package tt.smart.agency.message.service.sms;

import cn.hutool.core.codec.Base64;
import cn.hutool.crypto.digest.HMac;
import cn.hutool.crypto.digest.HmacAlgorithm;
import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson2.JSONObject;
import tt.smart.agency.message.api.sms.CtyunSms;
import tt.smart.agency.message.config.properties.sms.CtyunProperties;
import tt.smart.agency.message.domain.MessageSendBlend;
import tt.smart.agency.message.domain.sms.SmsResponseResult;
import tt.smart.agency.message.exception.SmsMessageException;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * <p>
 * 天翼云短信服务实现
 * </p>
 *
 * @author YangMC
 * @version V1.0
 **/
public class CtyunSmsImpl extends AbstractSmsMessage<SmsResponseResult, CtyunProperties> implements CtyunSms {

    protected final CtyunProperties ctyunSmsConfig;

    public CtyunSmsImpl(CtyunProperties ctyunSmsConfig) {
        this.ctyunSmsConfig = ctyunSmsConfig;
        super.smsProperties = ctyunSmsConfig;
    }

    @Override
    public SmsResponseResult sendMessage(String phone, String message) {
        LinkedHashMap<String, String> map = new LinkedHashMap<>();
        map.put(ctyunSmsConfig.getTemplateName(), message);
        return sendMessage(phone, ctyunSmsConfig.getTemplateId(), map);
    }

    @Override
    public SmsResponseResult sendMessage(String phone, String templateId, LinkedHashMap<String, String> messages) {
        MessageSendBlend messageSend;
        try {
            ctyunSmsConfig.checkIntegrity();
            String requestUrl = ctyunSmsConfig.getRequestUrl();
            String paramStr = generateParamJsonStr(phone, JSONUtil.toJsonStr(messages), templateId);
            Map<String, List<String>> headers = signHeader(paramStr, ctyunSmsConfig.getAccessKey(), ctyunSmsConfig.getAccessKeySecret());
            messageSend = MessageSendBlend.builder().url(requestUrl).headers(headers).messageContent(paramStr).build();
        } catch (Exception e) {
            throw new SmsMessageException("天翼云短信推送消息错误：" + e.getMessage());
        }
        return super.sendMessage(messageSend);
    }

    /**
     * 获取签名请求头
     *
     * @param body   body
     * @param key    Access Key
     * @param secret Access Key Secret
     * @return header
     */
    private Map<String, List<String>> signHeader(String body, String key, String secret) {
        // 构造时间戳
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
        Date now = new Date();
        String signatureDate = dateFormat.format(now);
        SimpleDateFormat timeFormat = new SimpleDateFormat("yyyyMMdd'T'HHmmss'Z'");
        String signatureTime = timeFormat.format(new Date());
        // 构造请求流水号
        String uuid = UUID.randomUUID().toString();
        // 签名
        String calculateContentHash = calculateContentHash(body);
        byte[] kTime = hmacSha256(signatureTime.getBytes(), secret.getBytes());
        byte[] kAk = hmacSha256(key.getBytes(), kTime);
        byte[] kDate = hmacSha256(signatureDate.getBytes(), kAk);

        // 构造待签名字符串
        // 报文原封不动进行 sha256 摘要
        String signatureStr = String.format("ctyun-eop-request-id:%s\neop-date:%s\n", uuid, signatureTime) + "\n\n" + calculateContentHash;
        // 构造签名
        String signature = Base64.encode(hmacSha256(signatureStr.getBytes(StandardCharsets.UTF_8), kDate));
        String signHeader = String.format("%s Headers=ctyun-eop-request-id;eop-date Signature=%s", key, signature);

        Map<String, List<String>> map = new ConcurrentHashMap<>();
        map.put("Content-Type", List.of(FROM_JSON));
        map.put("ctyun-eop-request-id", List.of(uuid));
        map.put("Eop-date", List.of(signatureTime));
        map.put("Eop-Authorization", List.of(signHeader));
        return map;
    }

    /**
     * 签名
     *
     * @param body body
     * @return 结果
     */
    private String calculateContentHash(String body) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(body.getBytes(StandardCharsets.UTF_8));
            byte[] digest = md.digest();
            StringBuilder sb = new StringBuilder(digest.length * 2);
            for (byte b : digest) {
                String hex = Integer.toHexString(b);
                if (hex.length() == 1) {
                    sb.append("0");
                } else if (hex.length() == 8) {
                    hex = hex.substring(6);
                }
                sb.append(hex);
            }
            return sb.toString().toLowerCase(Locale.getDefault());
        } catch (NoSuchAlgorithmException e) {
            throw new SmsMessageException("天翼云短信签名失败");
        }
    }

    /**
     * HmacSHA256
     *
     * @param data data
     * @param key  key
     * @return 结果
     */
    private byte[] hmacSha256(byte[] data, byte[] key) {
        try {
            HMac hMac = new HMac(HmacAlgorithm.HmacSHA256, key);
            return hMac.digest(data);
        } catch (Exception e) {
            throw new SmsMessageException(e.getMessage());
        }
    }

    /**
     * 生成请求 Body 参数
     *
     * @param phone      手机号
     * @param message    短信内容
     * @param templateId 模板 ID
     */
    private String generateParamJsonStr(String phone, String message, String templateId) {
        Map<String, String> paramMap = new HashMap<>();
        paramMap.put("action", ctyunSmsConfig.getAction());
        paramMap.put("phoneNumber", phone);
        paramMap.put("signName", ctyunSmsConfig.getSignature());
        paramMap.put("templateParam", message);
        paramMap.put("templateCode", templateId);
        return JSONUtil.toJsonStr(paramMap);
    }

    @Override
    protected SmsResponseResult handleResponseResult(JSONObject resultBody) {
        SmsResponseResult result = new SmsResponseResult();
        result.setCode(resultBody.getString("code"));
        result.setMessage(resultBody.getString("message"));
        if (OK.equalsIgnoreCase(result.getCode())) {
            result.setSuccess(true);
        }
        return result;
    }
}