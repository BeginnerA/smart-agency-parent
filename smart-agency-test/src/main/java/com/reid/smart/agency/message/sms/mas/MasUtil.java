package com.reid.smart.agency.message.sms.mas;

import cn.hutool.crypto.digest.DigestUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.json.JSONUtil;

import java.io.IOException;
import java.util.Base64;

/**
 * <p>
 * 政企 MAS 移动云短信
 * </p>
 *
 * @author MC_Yang
 * @version V1.0
 **/
public class MasUtil {

    // 用户名
    private static String apId = "hz001";
    // 密码
    private static String secretKey = "nK$O,ct6NPWQ4so.";
    // 集团名称
    private static String ecName = "中共会泽县委员会政法委员会";
    // 网关签名编码
    private static String sign = "CaeqOIgVi";
    // 拓展码 填空
    private static String addSerial = "";
    // 请求url
    public static String url = "https://112.33.46.17:37892/sms/submit";

    /**
     * 多用户发送短信信息
     *
     * @param phone 手机号码逗号分隔
     * @return 返回1表示成功，0表示失败
     */
    public static int sendMsg(String phone) {

        String code = "1111";

        String content = "您本次登录的验证码为：" + code + "";

        SendReq sendReq = new SendReq();
        sendReq.setApId(apId);
        sendReq.setEcName(ecName);
        sendReq.setSecretKey(secretKey);
        sendReq.setContent(content);
        sendReq.setMobiles(phone);
        sendReq.setAddSerial(addSerial);
        sendReq.setSign(sign);

        String stringBuffer = sendReq.getEcName() +
                sendReq.getApId() +
                sendReq.getSecretKey() +
                sendReq.getMobiles() +
                sendReq.getContent() +
                sendReq.getSign() +
                sendReq.getAddSerial();

        System.out.println("源MAC:" + stringBuffer);
        sendReq.setMac(DigestUtil.md5Hex(stringBuffer).toLowerCase());
        System.out.println("加密MAC:" + sendReq.getMac());

        String resStr = "";
        try {
            String reqText = JSONUtil.toJsonStr(sendReq);
            String encodeParam = Base64.getEncoder().encodeToString(reqText.getBytes("UTF-8"));
//        String encodeParam = cn.hutool.core.codec.Base64.encode(reqText);

            //System.out.println(encodeParam);

            resStr = HttpClient.sendPost(url, encodeParam,"");
//            resStr = HttpRequest.post(url).contentType("UTF-8").keepAlive(true)
//                    .body(encodeParam).execute().body();
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println("发送短信结果：" + resStr);

        SendRes sendRes = JSONUtil.toBean(resStr, SendRes.class);

        if (sendRes.isSuccess() && !"".equals(sendRes.getMsgGroup()) && "success".equals(sendRes.getRspcod())) {
            return 1;
        } else {
            return 0;
        }
    }

    /**
     * 示例使用方式
     */
    public static void main(String[] args) {
        int i = MasUtil.sendMsg("15587100090");
    }

}
