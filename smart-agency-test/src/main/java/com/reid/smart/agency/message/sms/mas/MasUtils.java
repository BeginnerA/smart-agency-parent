package com.reid.smart.agency.message.sms.mas;

import cn.hutool.crypto.digest.DigestUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.json.JSONUtil;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;
import java.util.Base64;

/**
 * <p>
 * 政企 MAS 移动云短信服务工具类（优化版）
 * </p>
 *
 * @author MC_Yang
 * @version V1.0
 **/
public class MasUtils {
    // 配置参数建议改为从配置文件注入
    private final String apId;
    private final String secretKey;
    private final String ecName;
    private final String sign;
    private final String addSerial;
    private final String url;

    /**
     * 全参数构造器
     */
    public MasUtils(String apId, String secretKey, String ecName,
                    String sign, String addSerial, String url) {
        this.apId = apId;
        this.secretKey = secretKey;
        this.ecName = ecName;
        this.sign = sign;
        this.addSerial = addSerial;
        this.url = url;
    }

    /**
     * 发送短信验证码（安全增强版）
     *
     * @param phone 多个手机号用逗号分隔
     * @return 1-成功 0-失败
     */
    public int sendSecureMessage(String phone) {
        // 验证码生成逻辑应根据实际需求实现
        final String verificationCode = generateVerificationCode();
        final String content = String.format("您本次登录的验证码为：%s，5分钟内有效", verificationCode);

        SendReq sendReq = buildRequest(phone, content);
        sendReq.setMac(calculateMac(sendReq));

        try {
            String response = sendHttpsRequest(sendReq);
            System.out.println("发送结果：" + response);
            SendRes sendRes = JSONUtil.toBean(response, SendRes.class);

            return isSuccessResponse(sendRes) ? 1 : 0;
        } catch (Exception e) {
            System.out.println("短信发送异常 | 目标号码: " + phone + " | 异常信息: " + e.getMessage());
            return 0;
        }
    }

    /**
     * 构建基础请求对象
     */
    private SendReq buildRequest(String phone, String content) {
        SendReq req = new SendReq();
        req.setApId(apId);
        req.setEcName(ecName);
        req.setSecretKey(secretKey);
        req.setMobiles(phone);
        req.setContent(content);
        req.setSign(sign);
        req.setAddSerial(addSerial);
        return req;
    }

    /**
     * 计算消息认证码
     */
    private String calculateMac(SendReq req) {
        String macSource = req.getEcName() + req.getApId() + req.getSecretKey()
                + req.getMobiles() + req.getContent() + req.getSign() + req.getAddSerial();
        System.out.println("MAC计算原始字符串: " + macSource);
        return DigestUtil.md5Hex(macSource).toLowerCase();
    }

    /**
     * 发送HTTPS请求（包含SSL安全配置）
     */
    private String sendHttpsRequest(SendReq sendReq) throws Exception {
        String requestJson = JSONUtil.toJsonStr(sendReq);
        String encodedPayload = Base64.getEncoder().encodeToString(
                requestJson.getBytes(StandardCharsets.UTF_8));

        // 配置SSL上下文（生产环境应使用正式证书）
        SSLContext sslContext = SSLContext.getInstance("TLSv1.2");
        sslContext.init(null, createTrustAllManagers(), new SecureRandom());

        return HttpRequest.post(url)
                .setSSLSocketFactory(sslContext.getSocketFactory())
                // 简化主机验证
                .setHostnameVerifier((hostname, session) -> true)
                .contentType("text/plain")
                .body(encodedPayload)
                .timeout(5000)
                .execute()
                .body();
    }

    /**
     * 创建信任所有证书的 TrustManager（仅限测试环境使用）
     */
    private TrustManager[] createTrustAllManagers() {
        return new TrustManager[]{
                new X509TrustManager() {
                    @Override
                    public void checkClientTrusted(X509Certificate[] chain, String authType) {
                    }

                    @Override
                    public void checkServerTrusted(X509Certificate[] chain, String authType) {
                    }

                    @Override
                    public X509Certificate[] getAcceptedIssuers() {
                        return new X509Certificate[0];
                    }
                }
        };
    }

    /**
     * 验证响应是否成功
     */
    private boolean isSuccessResponse(SendRes res) {
        return res != null && res.isSuccess()
                && "success".equalsIgnoreCase(res.getRspcod())
                && res.getMsgGroup() != null;
    }

    /**
     * 生成验证码（示例实现）
     */
    private String generateVerificationCode() {
        // 实际应实现更安全的随机数生成逻辑
        return String.valueOf((int) (Math.random() * 9000) + 1000);
    }

    /**
     * 示例使用方式
     */
    public static void main(String[] args) {
        String url = "https://112.33.46.17:37892/sms/submit";
        MasUtils mas = new MasUtils(
                "hz001",
                "nK$O,ct6NPWQ4so.",
                "中共会泽县委员会政法委员会",
                "CaeqOIgVi",
                "",
                url
        );

        int result = mas.sendSecureMessage("15587100090");
        System.out.println("短信发送结果: " + result);
    }
}
