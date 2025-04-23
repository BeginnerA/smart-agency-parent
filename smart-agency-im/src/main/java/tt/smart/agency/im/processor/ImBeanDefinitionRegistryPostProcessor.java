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
 * @author MC_Yang
 * @version V1.0
 **/
public class ImBeanDefinitionRegistryPostProcessor implements BeanDefinitionRegistryPostProcessor {

    @Override
    public void postProcessBeanDefinitionRegistry(@NotNull BeanDefinitionRegistry registry) throws BeansException {
        boolean webSocketHandlerMapping = registry.containsBeanDefinition("webSocketHandlerMapping");
        boolean haveStompWebSocketHandlerMapping = registry.containsBeanDefinition("stompWebSocketHandlerMapping");
        if (webSocketHandlerMapping || haveStompWebSocketHandlerMapping) {
            // 注册 Spring WebSocket
            BeanDefinition imSpringWebSocketConfigurer = BeanDefinitionBuilder
                    .rootBeanDefinition(ImSpringEnableWebSocketMessageBroker.ImSpringWebSocketConfigurer.class).getBeanDefinition();
            registry.registerBeanDefinition("imSpringWebSocketConfigurer", imSpringWebSocketConfigurer);
        }

    }

    @Override
    public void postProcessBeanFactory(@NotNull ConfigurableListableBeanFactory beanFactory) throws BeansException {
        BeanDefinitionRegistryPostProcessor.super.postProcessBeanFactory(beanFactory);
    }

}
