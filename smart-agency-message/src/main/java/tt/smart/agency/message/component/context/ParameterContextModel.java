package tt.smart.agency.message.component.context;

import lombok.Getter;
import lombok.Setter;
import tt.smart.agency.message.component.annotation.NotificationPolicyEnum;

/**
 * <p>
 * 参数上下文模型
 * </p>
 *
 * @author MC_Yang
 * @version V1.0
 **/
@Getter
@Setter
@SuppressWarnings("unchecked")
public class ParameterContextModel extends ParameterContext {

    /**
     * 方法返回值。<br>
     * 注意：通过注解方式执行消息时，返回值只有在 MessageStrategy 注解通知政策为 {@link NotificationPolicyEnum#AFTER} 时才会存在。
     */
    private Object returnValue;

    /**
     * 方法输入参数列表
     */
    private Object[] inputParameters;

    /**
     * 参数上下文构造函数
     */
    public ParameterContextModel() {

    }

    /**
     * 参数上下文构造函数
     *
     * @param returnValue 方法返回值
     */
    public ParameterContextModel(Object returnValue) {
        this.returnValue = returnValue;
    }

    /**
     * 参数上下文构造函数
     *
     * @param returnValue    方法返回值
     * @param inputParameter 方法输入参数
     */
    public ParameterContextModel(Object returnValue, Object... inputParameter) {
        this.returnValue = returnValue;
        this.inputParameters = inputParameter;
    }

    @Override
    public <T> T getInputParameter() {
        return (T) inputParameters[0];
    }

    @Override
    public <T> T getInputParameter(int index) {
        if (inputParameters.length > index) {
            return (T) inputParameters[index];
        }
        return null;
    }
}