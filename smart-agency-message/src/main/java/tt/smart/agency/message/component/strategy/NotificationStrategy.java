package tt.smart.agency.message.component.strategy;

import tt.smart.agency.message.component.context.ParameterContext;
import tt.smart.agency.message.component.context.ParameterContextModel;

/**
 * <p>
 * 通知策略
 * </p>
 *
 * @author YangMC
 * @version V1.0
 **/
public interface NotificationStrategy {

    /**
     * 设置消息参数上下文
     *
     * @param parameterContext 参数上下文
     */
    void setParameterContext(ParameterContextModel parameterContext);

    /**
     * 推送通知策略
     *
     * @param notificationTemplate 消息通知模板
     * @param parameterContext     方法参数
     * @return 通知消息模板列表
     */
    NotificationTemplate sendNotification(NotificationTemplate notificationTemplate, ParameterContext parameterContext);
}