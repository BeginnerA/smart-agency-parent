package tt.smart.agency.message.component.strategy;

import tt.smart.agency.message.component.context.ParameterContext;
import tt.smart.agency.message.component.context.ParameterContextModel;
import tt.smart.agency.message.component.domain.MessageTemplateConfig;

/**
 * <p>
 * 抽象的通知策略
 * </p>
 *
 * @author YangMC
 * @version V1.0
 **/
public abstract class AbstractNotificationStrategy implements NotificationStrategy {

    /**
     * 参数上下文
     */
    protected ParameterContext parameterContext;
    /**
     * 消息模板配置
     */
    protected MessageTemplateConfig messageTemplateConfig;

    @Override
    public void setParameterContext(ParameterContextModel parameterContext) {
        this.parameterContext = parameterContext;
        this.messageTemplateConfig = parameterContext.getMessageTemplateConfig();
    }

}