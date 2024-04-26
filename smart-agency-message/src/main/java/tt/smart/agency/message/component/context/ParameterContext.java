package tt.smart.agency.message.component.context;

import lombok.Getter;
import lombok.Setter;
import tt.smart.agency.message.component.annotation.MessageStrategy;
import tt.smart.agency.message.component.annotation.NotificationPolicyEnum;
import tt.smart.agency.message.component.domain.MessageTemplateConfig;

/**
 * <p>
 * 参数上下文
 * </p>
 *
 * @author YangMC
 * @version V1.0
 **/
@Getter
@Setter
public abstract class ParameterContext {

    /**
     * 消息模板配置
     */
    private MessageTemplateConfig messageTemplateConfig;

    /**
     * 在同一个策略中如果存在多个消息时可以指定需要推送的消息
     */
    private String[] idents;

    /**
     * 在同一个策略中如果存在多个消息时可以指定需要推送的消息
     *
     * @param ident 消息标识号
     */
    public void setMessageTemplateFiltration(String... ident) {
        this.idents = ident;
    }

    /**
     * 获取返回值。<br>
     * 注意：在通过{@link MessageStrategy}注解方式使用通知时，政策为{@link NotificationPolicyEnum#AFTER}时才会存在返回值。
     *
     * @return 返回值
     */
    public abstract <T> T getReturnValue();

    /**
     * 获取输入参数。<br>
     * 注意：如果存在多个入参，该方法只返回入参列表中的第一个参数。
     *
     * @return 输入参数中的第一个参数
     */
    public abstract <T> T getInputParameter();

    /**
     * 按参数序号获取输入参数
     *
     * @param index 参数序号（索引从0开始）
     * @return 输入参数
     */
    public abstract <T> T getInputParameter(int index);

    /**
     * 获取输入参数列表
     *
     * @return 输入参数列表
     */
    public abstract Object[] getInputParameters();
}