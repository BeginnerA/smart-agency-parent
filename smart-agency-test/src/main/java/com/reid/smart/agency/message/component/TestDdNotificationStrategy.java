package com.reid.smart.agency.message.component;

import tt.smart.agency.message.component.context.ParameterContext;
import tt.smart.agency.message.component.domain.Message;
import tt.smart.agency.message.component.strategy.AbstractNotificationStrategy;
import tt.smart.agency.message.component.strategy.NotificationTemplate;
import tt.smart.agency.message.domain.dd.DdMessage;

/**
 * <p>
 * 测试自定义钉钉通知策略
 * </p>
 *
 * @author YangMC
 * @version V1.0
 **/
public class TestDdNotificationStrategy extends AbstractNotificationStrategy {
    @Override
    public NotificationTemplate sendNotification(NotificationTemplate notificationTemplate, ParameterContext parameterContext) {
        System.out.print("执行自定义钉钉通知策略\n");
        // 文本消息
        DdMessage textMsg = DdMessage.textMsg()
                .agentId("2695452363")
                .useridList("manager1374")
                .content("你的快递已到，请携带工卡前往邮件中心领取。出发前可查看<a href=\"https://oa.dingtalk.com\">邮件中心视频实况</a>，聪明避开排队。")
                .build();
        notificationTemplate.setMessages(Message.builder().content(textMsg.toJson()).build());
        return notificationTemplate;
    }
}
