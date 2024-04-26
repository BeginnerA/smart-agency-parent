package com.reid.smart.agency.message.wx.cp;

import jakarta.annotation.Resource;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import tt.smart.agency.message.api.wx.WxCpApplyMessage;
import tt.smart.agency.message.domain.wx.cp.WxCpMessage;
import tt.smart.agency.message.domain.wx.cp.WxCpMessageSendResult;
import tt.smart.agency.message.factory.wx.WxMessageFactory;

/**
 * <p>
 * 企业微信消息测试
 * </p>
 *
 * @author YangMC
 * @version V1.0
 **/
@SpringBootTest
public class WxCpMessageTest {

    @Resource
    private WxCpApplyMessage wxCpApplyMessage;

    /**
     * 开始
     */
    @BeforeAll
    public static void beforeClass() {
        System.out.println("\n\n------------------------ 基础测试 star ...");
    }

    /**
     * 结束
     */
    @AfterAll
    public static void afterClass() {
        System.out.println("\n\n------------------------ 基础测试 end ... \n");
    }

    /**
     * 测试企业微信文本消息
     */
    @Test
    public void testWxCpTextMessage() {
        // 文本消息
        WxCpMessage textMsg = WxCpMessage.textMsg()
                .agentId("1000002")
                .toUser("YangMingChun")
                .content("你的快递已到，请携带工卡前往邮件中心领取。出发前可查看<a href=\"http://work.weixin.qq.com\">邮件中心视频实况</a>，聪明避开排队。")
                .build();
//        WxCpMessageSendResult messageSendResult = WxMessageFactory.createWxCpMessageBlend().sendMessage(textMsg);
        WxCpMessageSendResult messageSendResult = wxCpApplyMessage.sendMessage(textMsg);
        System.out.print("结果：" + messageSendResult);
    }

    /**
     * 测试企业微信图片消息
     */
    @Test
    public void testWxCpImageMessage() {
        // 图片消息
        WxCpMessage textMsg = WxCpMessage.imageMsg()
                .agentId("1000002")
                .toUser("YangMingChun")
                .mediaId("MEDIA_ID")
                .build();
        WxCpMessageSendResult messageSendResult = WxMessageFactory.createWxCpMessageBlend().sendMessage(textMsg);
        System.out.print("结果：" + messageSendResult);
    }

    /**
     * 测试企业微信卡片消息
     */
    @Test
    public void testWxCpCardMessage() {
        // 文本卡片消息
        WxCpMessage textCardMsg = WxCpMessage.textCard()
                .agentId("1000002")
                .toUser("YangMingChun")
                .title("领奖通知")
                .description("<div class=\"gray\">2023年9月9日</div> <div class=\"normal\">恭喜你抽中iPhone 15 Pro Max一台，" +
                        "领奖码：xxxx</div><div class=\"highlight\">请于2023年10月10日前联系行政同事领取</div>")
                .url("http://www.qq.com")
                .btnTxt("更多")
                .build();
        WxCpMessageSendResult messageSendResult = WxMessageFactory.createWxCpMessageBlend().sendMessage(textCardMsg);
        System.out.print("结果：" + messageSendResult);
    }

}