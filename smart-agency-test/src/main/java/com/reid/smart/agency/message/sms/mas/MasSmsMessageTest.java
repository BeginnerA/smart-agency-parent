package com.reid.smart.agency.message.sms.mas;

import cn.hutool.core.codec.Base64;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.digest.DigestUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import lombok.Data;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * 政企移动短信测试
 * </p>
 *
 * @author MC_Yang
 * @version V1.0
 **/
public class MasSmsMessageTest {


    @Data
    static class MasSmsProperties {
        /**
         * 客户名称
         */
        private String ecName;
        /**
         * 应用ID
         */
        private String apId;
        /**
         * 秘钥
         */
        private String secretKey;
        /**
         * 签名
         */
        private String sign;
        /**
         * 短信接口链接
         */
        private String url;

        // 以下非配置

        /**
         * 手机号
         */
        private String mobiles;
        /**
         * 短信模板 ID（模板短信）
         */
        private String templateId;
        /**
         * 短信模板参数变量（模板短信）
         */
        private String params;
        /**
         * 短信内容（普通短信）
         */
        private String content;
        /**
         * 参数签名
         */
        private String mac;
        /**
         * 扩展码
         */
        private String addSerial = "";
    }

    private MasSmsProperties masSmsProperties;
    private String phone = "15587100090";

    public MasSmsMessageTest() {
        init();
    }

    /**
     * 从属性中获取参数签名
     *
     * @param masSmsProperties 属性
     * @return 签名
     */
    private String getTemplateMac(MasSmsProperties masSmsProperties) {
        String macData = masSmsProperties.getEcName() +
                masSmsProperties.getApId() +
                masSmsProperties.getSecretKey() +
                masSmsProperties.getTemplateId() +
                masSmsProperties.getMobiles() +
                masSmsProperties.getParams() +
                masSmsProperties.getSign() +
                masSmsProperties.getAddSerial();
        System.out.println("MAC：" + macData);
        String lowerCase = DigestUtil.md5Hex(macData).toLowerCase();
        System.out.println("MAC（MD5）：" + lowerCase);
        return lowerCase;
    }

    /**
     * 从属性中获取参数签名
     *
     * @param masSmsProperties 属性
     * @return 签名
     */
    private String getContentMac(MasSmsProperties masSmsProperties) {
        String macData = masSmsProperties.getEcName() +
                masSmsProperties.getApId() +
                masSmsProperties.getSecretKey() +
                masSmsProperties.getMobiles() +
                masSmsProperties.getContent() +
                masSmsProperties.getSign() +
                masSmsProperties.getAddSerial();
        System.out.println("MAC：" + macData);
        String lowerCase = DigestUtil.md5Hex(macData).toLowerCase();
        System.out.println("MAC（MD5）：" + lowerCase);
        return lowerCase;
    }


    /**
     * 发送 HTTP 短信
     *
     * @param masSmsProperties 属性
     * @return 发送结果
     */
    private String sendHttpSms(MasSmsProperties masSmsProperties) {
        SendReq req = new SendReq();
        req.setApId(masSmsProperties.getApId());
        req.setEcName(masSmsProperties.getEcName());
//        req.setSecretKey(masSmsProperties.getSecretKey());
        req.setMobiles(masSmsProperties.getMobiles());
        req.setContent(masSmsProperties.getContent());
        req.setSign(masSmsProperties.getSign());
        req.setMac(masSmsProperties.getMac());
        req.setAddSerial("");
        String paramStr = JSONUtil.toJsonStr(req);
        System.out.println("请求参数：" + paramStr);
        String encodeParam = Base64.encode(paramStr);
        System.out.println("请求参数（Base64）：" + encodeParam);
        return HttpRequest.post(masSmsProperties.getUrl()).contentType("UTF-8").keepAlive(true).body(encodeParam).execute().body();
    }

    /**
     * 发送内容短信
     *
     * @param phoneNumbers 推送号码
     * @param content      短信内容
     * @return 结果
     */
    private JSONObject sendContentSms(String phoneNumbers, String content) {
        masSmsProperties.setMobiles(phoneNumbers);
        masSmsProperties.setContent(StrUtil.isEmpty(content) ? "" : content);
        // 参数签名
        masSmsProperties.setMac(getContentMac(masSmsProperties));
        // 发送http短信
        String httpSmsResult = sendHttpSms(masSmsProperties);
        // 解析结果
        return JSONUtil.parseObj(httpSmsResult);
    }

    /**
     * 发送模板短信
     *
     * @param phoneNumbers      推送号码
     * @param templateId        模板 ID
     * @param templateParamJson 模式参数
     * @return 结果
     */
    private JSONObject sendTemplateSms(String phoneNumbers, String templateId, String templateParamJson) {
        masSmsProperties.setMobiles(phoneNumbers);
        masSmsProperties.setTemplateId(templateId);
        // 数组类型的参数,多个参数值用空格分隔
        masSmsProperties.setParams(StrUtil.isEmpty(templateParamJson) ? "[\"\"]" : templateParamJson);
        // 参数签名
        masSmsProperties.setMac(getTemplateMac(masSmsProperties));
        // 发送http短信
        String httpSmsResult = sendHttpSms(masSmsProperties);
        // 解析结果
        return JSONUtil.parseObj(httpSmsResult);
    }

    /**
     * 初始化配置
     */
    private void init() {
        masSmsProperties = new MasSmsProperties();
        masSmsProperties.ecName = "中共会泽县委员会政法委员会";
        masSmsProperties.apId = "qjhzzfw";
//        masSmsProperties.apId = "hz_test";
//        masSmsProperties.apId = "hz001";
        masSmsProperties.secretKey = "nK$O,ct6NPWQ4so.";
        masSmsProperties.sign = "CaeqOIgVi";
        // 普通短信
//        masSmsProperties.url = "http://112.33.46.17:37891/sms/norsubmit";
        // 模板短信
//        masSmsProperties.url = "http://112.33.46.17:37891/sms/tmpsubmit";
    }

    /**
     * 发送普通短信
     */
    @Test
    public void sendOrdinarySmsTest() {
        // 普通短信
        masSmsProperties.url = "http://112.33.46.17:37891/sms/norsubmit";
        String content = "移动改变生活：测试短信";
        JSONObject entries = sendContentSms(phone, content);
        System.out.println("完成发送：" + entries.toString());
    }


}
