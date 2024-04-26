package com.reid.smart.agency.message.dd;

import jakarta.annotation.Resource;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import tt.smart.agency.message.api.dd.DdApplyMessage;
import tt.smart.agency.message.domain.MessageSendResult;
import tt.smart.agency.message.domain.dd.DdMessage;

/**
 * <p>
 * 钉钉消息测试
 * </p>
 *
 * @author YangMC
 * @version V1.0
 **/
@SpringBootTest
public class DdMessageTest {

    @Resource
    private DdApplyMessage ddApplyMessage;

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
     * 钉钉文本消息
     */
    @Test
    public void testDdTextMessage() {
        // 文本消息
        DdMessage textMsg = DdMessage.textMsg()
                .agentId("2695452363")
                .useridList("manager1374")
                .content("你的快递已到，请携带工卡前往邮件中心领取。出发前可查看<a href=\"https://oa.dingtalk.com\">邮件中心视频实况</a>，聪明避开排队。")
                .build();
//        MessageSendResult messageSendResult = DdMessageFactory.createDdMessageBlend(DdPlatformType.DD_APPLY).sendMessage(textMsg);
        MessageSendResult messageSendResult = ddApplyMessage.sendMessage(textMsg);
        System.out.print("结果：" + messageSendResult);
    }

}