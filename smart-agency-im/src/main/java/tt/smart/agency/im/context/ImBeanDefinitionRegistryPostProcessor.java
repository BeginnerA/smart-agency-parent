package tt.smart.agency.im.context;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;
import tt.smart.agency.im.handler.SpringWebSocketImClientHandler;
import tt.smart.agency.im.interceptor.WebSocketChannelInterceptor;

/**
 * <p>
 *
 * </p>
 *
 * @author YangMC
 * @version V1.0
 **/
public class ImBeanDefinitionRegistryPostProcessor implements BeanDefinitionRegistryPostProcessor {

    @Override
    public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry) throws BeansException {
//        BeanDefinition beanDefinition = BeanDefinitionBuilder.rootBeanDefinition(ImWebSocketConfig1.class)
//                .getBeanDefinition();
//        registry.registerBeanDefinition("imWebSocketConfig1", beanDefinition);
//
//        BeanDefinition beanDefinition = BeanDefinitionBuilder.rootBeanDefinition(ImWebSocketConfig2.class)
//                .getBeanDefinition();
//        registry.registerBeanDefinition("imWebSocketConfig2", beanDefinition);
//
    }

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        BeanDefinitionRegistryPostProcessor.super.postProcessBeanFactory(beanFactory);
    }

    static class ImWebSocketConfig1 implements WebSocketConfigurer {
        @Override
        public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
            // 注册自定义 WebSocket 处理器到指定路径上
            registry.addHandler(new SpringWebSocketImClientHandler(), "/websocket")
                    .setAllowedOriginPatterns("*");
        }
    }

    static class ImWebSocketConfig2 implements WebSocketMessageBrokerConfigurer {
//        @Override
//        public void registerStompEndpoints(StompEndpointRegistry registry) {
//            registry.addEndpoint("/agency-im") //端点名称
//                    .addInterceptors(new ImHandshakeInterceptor()) //注册Http拦截器
//                    .setAllowedOriginPatterns("*");
//        }
//
//        @Override
//        public void configureClientInboundChannel(ChannelRegistration registration) {
//            registration.interceptors(new WebSocketChannelInterceptor());
//        }
//
//        @Override
//        public void configureClientOutboundChannel(ChannelRegistration registration) {
//            registration.interceptors(new WebSocketChannelInterceptor());
//        }
    }
}
