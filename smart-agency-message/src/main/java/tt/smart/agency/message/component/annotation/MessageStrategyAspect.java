package tt.smart.agency.message.component.annotation;

import cn.hutool.core.bean.BeanUtil;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.web.bind.annotation.RequestMapping;
import tt.smart.agency.message.component.context.ParameterContextModel;
import tt.smart.agency.message.component.strategy.MessagePushStrategyFactory;

import java.util.Map;

/**
 * <p>
 * 消息通知策略 AOP
 * </p>
 *
 * @author YangMC
 * @version V1.0
 **/
@Slf4j
@Aspect
public class MessageStrategyAspect {

    private final MessagePushStrategyFactory messagePushStrategyFactory;

    public MessageStrategyAspect(MessagePushStrategyFactory messagePushStrategyFactory) {
        this.messagePushStrategyFactory = messagePushStrategyFactory;
    }

    @Pointcut("@annotation(tt.smart.agency.message.component.annotation.MessageStrategy)")
    public void pointCut() {
    }

    @Around(value = "pointCut()")
    public Object round(ProceedingJoinPoint joinPoint) throws Throwable {
        Object[] args = joinPoint.getArgs();
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        MessageStrategy strategyAnnotation = signature.getMethod().getAnnotation(MessageStrategy.class);
        Object result = null;
        int proceedExecuted = 0;
        try {
            ParameterContextModel parameterContext = new ParameterContextModel();
            parameterContext.setIdents(strategyAnnotation.idents());
            parameterContext.setInputParameters(args);
            if (NotificationPolicyEnum.BEFORE.equals(strategyAnnotation.policy())) {
                messagePushStrategyFactory.pushMessage(strategyAnnotation.strategy(), parameterContext, strategyAnnotation.isAsync());
            }
            proceedExecuted++;
            result = executiveMasterMethod(joinPoint);
            // 标记已经执行了 proceed 方法
            proceedExecuted++;

            if (NotificationPolicyEnum.AFTER.equals(strategyAnnotation.policy())) {
                parameterContext.setReturnValue(getResultData(joinPoint, result));
                messagePushStrategyFactory.pushMessage(strategyAnnotation.strategy(), parameterContext, strategyAnnotation.isAsync());
            }
        } catch (Throwable e) {
            if (proceedExecuted == 0) {
                // 防止在主方法未执行前出错
                result = executiveMasterMethod(joinPoint);
            } else if (proceedExecuted == 1) {
                // 执行主方法出错抛出原异常
                throw e;
            } else {
                // 捕获消息抛出异常，防止消息异常影响正常业务执行
                log.error("消息推送失败", e);
            }
        }
        return result;
    }

    /**
     * 得到主方法执行结果数据
     *
     * @param joinPoint ProceedingJoinPoint
     * @param result    主方法执行结果
     * @return 主方法执行结果数据
     */
    private Object getResultData(ProceedingJoinPoint joinPoint, Object result) {
        Object resultData = result;
        try {
            if (result != null) {
                // 获取目标类
                Class<?> targetClass = joinPoint.getTarget().getClass();
                if (targetClass.isAnnotationPresent(RequestMapping.class)) {
                    if (result instanceof Map) {
                        resultData = ((Map<?, ?>) result).get("data");
                    } else {
                        resultData = BeanUtil.getFieldValue(result, "data");
                    }
                }
            }
        } catch (Exception e) {
            log.debug(e.getMessage());
        }
        return resultData;
    }

    /**
     * 执行主方法
     *
     * @param joinPoint ProceedingJoinPoint
     * @return 执行结果
     */
    private Object executiveMasterMethod(ProceedingJoinPoint joinPoint) throws Throwable {
        Object result;
        try {
            result = joinPoint.proceed();
        } catch (Throwable e) {
            log.error("消息推送失败，【MessageStrategy】注解标注方法异常");
            throw e;
        }
        return result;
    }

}