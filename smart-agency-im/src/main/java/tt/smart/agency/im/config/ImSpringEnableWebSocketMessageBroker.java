package tt.smart.agency.im.config;

import org.springframework.boot.autoconfigure.condition.AnyNestedCondition;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Conditional;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;
import tt.smart.agency.im.handler.SpringWebSocketImClientHandler;

/**
 * <p>
 * Spring WebSocket 启动配置
 * </p>
 *
 * @author MC_Yang
 * @version V1.0
 **/
@EnableWebSocket
@Conditional(ImSpringEnableWebSocketMessageBroker.SpringWebSocketBeanCondition.class)
@ConditionalOnMissingBean(type = {"webSocketHandlerMapping", "stompWebSocketHandlerMapping"})
public class ImSpringEnableWebSocketMessageBroker {

    /**
     * <p>
     * 判断是否需要注入 SpringWebSocket 的 Bean 条件
     * </p>
     *
     * @author MC_Yang
     * @version V1.0
     **/
    static class SpringWebSocketBeanCondition extends AnyNestedCondition {
        public SpringWebSocketBeanCondition() {
            super(ConfigurationPhase.REGISTER_BEAN);
        }

        @ConditionalOnBean(WebSocketConfigurer.class)
        static class WebSocketConfigurerCondition {
        }

        @ConditionalOnBean(WebSocketMessageBrokerConfigurer.class)
        static class WebSocketMessageBrokerConfigurerCondition {
        }

        @ConditionalOnBean(ServerEndpointExporter.class)
        static class ServerEndpointExporterCondition {
        }

    }

    public static class ImSpringWebSocketConfigurer implements WebSocketConfigurer {

        @Override
        public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
            // 注册自定义 WebSocket 处理器到指定路径上
            registry.addHandler(new SpringWebSocketImClientHandler(), "/agency-im").setAllowedOriginPatterns("*");
        }
    }

}
