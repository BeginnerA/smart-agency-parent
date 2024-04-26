package tt.smart.agency.message.component.annotation;

import tt.smart.agency.message.component.domain.MessageTemplateConfig;
import tt.smart.agency.message.component.strategy.AbstractNotificationStrategy;
import tt.smart.agency.message.component.strategy.DefaultNotificationStrategy;

import java.lang.annotation.*;

/**
 * <p>
 * 消息策略。<br>
 * 根据定义消息策略进行消息推送
 * </p>
 *
 * @author YangMC
 * @version V1.0
 **/
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface MessageStrategy {

    /**
     * 通知策略
     *
     * @return 通知策略
     */
    Class<? extends AbstractNotificationStrategy> strategy() default DefaultNotificationStrategy.class;

    /**
     * 在同一个策略中如果存在多个消息时可以指定需要推送的消息。<br>
     * 注意：指定的{@code idents}必须存在{@link MessageTemplateConfig#getIdent()}消息模板配置中。
     */
    String[] idents() default {};

    /**
     * 通知政策。<br>
     * 在标注方法执行之前通知 {@link NotificationPolicyEnum#BEFORE}<br>
     * 在标注方法执行之后通知（默认） {@link NotificationPolicyEnum#AFTER}<br>
     *
     * @return 通知政策
     */
    NotificationPolicyEnum policy() default NotificationPolicyEnum.AFTER;

    /**
     * 是异步的消息推送
     *
     * @return 是否是异步的消息推送
     */
    boolean isAsync() default true;
}