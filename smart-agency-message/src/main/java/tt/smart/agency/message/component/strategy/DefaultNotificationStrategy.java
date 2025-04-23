package tt.smart.agency.message.component.strategy;

import tt.smart.agency.message.component.context.ParameterContext;

/**
 * <p>
 * 默认通知策略
 * </p>
 *
 * @author MC_Yang
 * @version V1.0
 **/
public class DefaultNotificationStrategy extends AbstractNotificationStrategy {
    @Override
    public NotificationTemplate sendNotification(NotificationTemplate notificationTemplate, ParameterContext parameterContext) {
        System.out.println("执行默认通知策略\n");
        return notificationTemplate;
    }
}
