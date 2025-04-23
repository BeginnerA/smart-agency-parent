package com.reid.smart.agency.message.component;

import tt.smart.agency.message.component.context.ParameterContext;
import tt.smart.agency.message.component.strategy.AbstractNotificationStrategy;
import tt.smart.agency.message.component.strategy.NotificationTemplate;

/**
 * <p>
 * 测试自定义短信通知策略
 * </p>
 *
 * @author MC_Yang
 * @version V1.0
 **/
public class TestSmsNotificationStrategy extends AbstractNotificationStrategy {
    @Override
    public NotificationTemplate sendNotification(NotificationTemplate notificationTemplate, ParameterContext parameterContext) {
        System.out.print("执行自定义短信通知策略\n");
        return notificationTemplate;
    }
}
