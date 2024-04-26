package com.reid.smart.agency.message.wx.mp;

import jakarta.annotation.Resource;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import tt.smart.agency.message.api.wx.WxMpMessage;
import tt.smart.agency.message.domain.wx.mp.WxMpMessageSendResult;
import tt.smart.agency.message.domain.wx.mp.template.WxMpSubscribeMessage;
import tt.smart.agency.message.domain.wx.mp.template.WxMpTemplateMessage;
import tt.smart.agency.message.domain.wx.mp.template.WxTemplateData;
import tt.smart.agency.message.factory.wx.WxMessageFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 微信公众号消息测试
 * </p>
 *
 * @author YangMC
 * @version V1.0
 **/
@SpringBootTest
public class WxMpMessageTest {

    @Resource
    private WxMpMessage wxMpMessage;

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
     * 测试微信公众号模板消息
     */
    @Test
    public void testWxMpTemplateMessage() {
        WxMpTemplateMessage templateMessage = WxMpTemplateMessage.builder()
                .toUser("oYetG6kNgutckVJ234X62uDsI35k")
                .templateId("Tdy8DL8U-XWJowkINe84yRgMlQvpC4F82EaWhZ1vbCw")
                .url("https://www.baidu.com/")
                .templateData(foundTemplateData())
                .build();
//        WxMpMessageSendResult messageSendResult = WxMessageFactory.createWxMpMessageBlend().sendMessage(templateMessage);
        WxMpMessageSendResult messageSendResult = wxMpMessage.sendMessage(templateMessage);
        System.out.print("结果：" + messageSendResult);
    }

    /**
     * 测试微信公众号订阅消息
     */
    @Test
    public void testWxMpSubscribeMessage() {
        WxMpSubscribeMessage subscribeMessage = WxMpSubscribeMessage.builder()
                .toUser("oYetG6kNgutckVJ234X62uDsI35k")
                .templateId("Tdy8DL8U-XWJowkINe84yRgMlQvpC4F82EaWhZ1vbCw")
                .templateData(foundTemplateData())
                .build();
        WxMpMessageSendResult messageSendResult = WxMessageFactory.createWxMpMessageBlend().sendMessage(subscribeMessage);
//        WxMpMessageSendResult messageSendResult = wxMpMessage.sendMessage(subscribeMessage);
        System.out.print("结果：" + messageSendResult);
    }

    private List<WxTemplateData> foundTemplateData() {
        List<WxTemplateData> data = new ArrayList<>();
        data.add(new WxTemplateData("keyword1", "未来科技股份有限公司"));
        data.add(new WxTemplateData("keyword2", "原子弹维护保养合同"));
        data.add(new WxTemplateData("keyword3", "原子弹保养"));
        data.add(new WxTemplateData("keyword4", "原子弹表面积灰，请立刻马上进行擦拭"));
        data.add(new WxTemplateData("keyword5", "法外狂徒"));
        return data;
    }

}