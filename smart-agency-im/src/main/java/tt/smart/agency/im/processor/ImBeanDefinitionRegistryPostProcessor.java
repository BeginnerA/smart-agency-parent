package tt.smart.agency.im.processor;

import org.jetbrains.annotations.NotNull;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import tt.smart.agency.im.config.ImSpringEnableWebSocketMessageBroker;

/**
 * <p>
 * 即时通讯 bean 定义注册表后处理程序
 * </p>
 *
 * @author YangMC
 * @version V1.0
 **/
public class ImBeanDefinitionRegistryPostProcessor implements BeanDefinitionRegistryPostProcessor {

    @Override
    public void postProcessBeanDefinitionRegistry(@NotNull BeanDefinitionRegistry registry) throws BeansException {
        boolean haveWebSocketScopeConfigurer = registry.containsBeanDefinition("webSocketScopeConfigurer");
        boolean haveStompWebSocketHandlerMapping = registry.containsBeanDefinition("stompWebSocketHandlerMapping");
        if (haveWebSocketScopeConfigurer && haveStompWebSocketHandlerMapping) {
            // 注册 spring WebSocket
            BeanDefinition imWebSocketMessageBrokerConfigurerBean = BeanDefinitionBuilder
                    .rootBeanDefinition(ImSpringEnableWebSocketMessageBroker.ImSpringWebSocketMessageBrokerConfigurer.class).getBeanDefinition();
            registry.registerBeanDefinition("imSpringWebSocketMessageBrokerConfigurer", imWebSocketMessageBrokerConfigurerBean);
        }

    }

    @Override
    public void postProcessBeanFactory(@NotNull ConfigurableListableBeanFactory beanFactory) throws BeansException {
        BeanDefinitionRegistryPostProcessor.super.postProcessBeanFactory(beanFactory);
    }

}
