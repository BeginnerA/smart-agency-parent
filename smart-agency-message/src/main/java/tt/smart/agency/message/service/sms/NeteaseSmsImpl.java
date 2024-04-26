package tt.smart.agency.message.service.sms;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.IdUtil;
import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import tt.smart.agency.message.api.sms.NeteaseSms;
import tt.smart.agency.message.config.properties.sms.NeteaseProperties;
import tt.smart.agency.message.domain.MessageSendBlend;
import tt.smart.agency.message.domain.sms.SmsResponseResult;
import tt.smart.agency.message.exception.SmsMessageException;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 网易云短信服务实现
 * </p>
 *
 * @author YangMC
 * @version V1.0
 **/
public class NeteaseSmsImpl extends AbstractSmsMessage<SmsResponseResult, NeteaseProperties> implements NeteaseSms {

    private static final char[] HEX_DIGITS = {'0', '1', '2', '3', '4', '5',
            '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
    protected final NeteaseProperties neteaseSmsConfig;

    public NeteaseSmsImpl(NeteaseProperties neteaseSmsConfig) {
        this.neteaseSmsConfig = neteaseSmsConfig;
        super.smsProperties = neteaseSmsConfig;
    }

    @Override
    public SmsResponseResult sendMessage(String phone, String message) {
        LinkedHashMap<String, String> map = new LinkedHashMap<>();
        map.put(neteaseSmsConfig.getTemplateName(), message);
        return sendMessage(phone, neteaseSmsConfig.getTemplateId(), map);
    }

    @Override
    public SmsResponseResult sendMessage(String phone, String templateId, LinkedHashMap<String, String> messages) {
        MessageSendBlend messageSend;
        try {
            neteaseSmsConfig.checkIntegrity();
            Map<String, List<String>> headers = signHeader();
            Map<String, Object> paramMap = generateParamJsonStr(phone, messages.get(neteaseSmsConfig.getTemplateName()));
            messageSend = MessageSendBlend.builder().url(neteaseSmsConfig.getTemplateUrl()).headers(headers).paramMap(paramMap).build();
        } catch (Exception e) {
            throw new SmsMessageException("天翼云短信推送消息错误：" + e.getMessage());
        }
        return super.sendMessage(messageSend);
    }

    /**
     * 生成请求 Body 参数
     *
     * @param phone   手机号
     * @param message 短信内容
     */
    private Map<String, Object> generateParamJsonStr(String phone, String message) {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("templateid", neteaseSmsConfig.getTemplateId());
        paramMap.put("mobiles", JSONArray.parseArray(JSON.toJSONString(List.of(phone))).toJSONString());
        paramMap.put("params", message);
        paramMap.put("needUp", neteaseSmsConfig.getNeedUp());
        return paramMap;
    }

    /**
     * 获取签名请求头
     *
     * @return header
     */
    private Map<String, List<String>> signHeader() {
        String nonce = IdUtil.fastSimpleUUID();
        String curTime = String.valueOf(DateUtil.currentSeconds());
        String checkSum;
        try {
            MessageDigest md = MessageDigest.getInstance("sha1");
            md.update((neteaseSmsConfig.getAccessKeySecret() + nonce + curTime).getBytes(StandardCharsets.UTF_8));
            byte[] digest = md.digest();
            StringBuilder buf = new StringBuilder(digest.length * 2);
            for (byte aByte : digest) {
                buf.append(HEX_DIGITS[(aByte >> 4) & 0x0f]);
                buf.append(HEX_DIGITS[aByte & 0x0f]);
            }
            checkSum = buf.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new SmsMessageException("网易云短信签名失败");
        }

        Map<String, List<String>> map = new LinkedHashMap<>();
        map.put("Content-Type", List.of(FROM_URLENCODED));
        map.put("AppKey", List.of(neteaseSmsConfig.getAccessKey()));
        map.put("Nonce", List.of(nonce));
        map.put("CurTime", List.of(curTime));
        map.put("CheckSum", List.of(checkSum));
        return map;
    }

    @Override
    protected SmsResponseResult handleResponseResult(JSONObject resultBody) {
        SmsResponseResult result = new SmsResponseResult();
        Integer code = resultBody.getInteger("code");
        result.setCode(String.valueOf(code));
        result.setMessage(resultBody.getString("msg"));
        if (code == 200) {
            result.setSuccess(true);
        }
        return result;
    }
}