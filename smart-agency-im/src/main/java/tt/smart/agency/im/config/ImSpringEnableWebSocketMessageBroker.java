package tt.smart.agency.im.config;

import org.springframework.boot.autoconfigure.condition.AnyNestedCondition;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Conditional;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

/**
 * <p>
 * Spring WebSocket 启动配置
 * </p>
 *
 * @author YangMC
 * @version V1.0
 **/
@EnableWebSocketMessageBroker
@Conditional(ImSpringEnableWebSocketMessageBroker.SpringWebSocketBeanCondition.class)
@ConditionalOnMissingBean(type = {"webSocketScopeConfigurer", "stompWebSocketHandlerMapping"})
public class ImSpringEnableWebSocketMessageBroker {

    /**
     * <p>
     * 判断是否需要注入 SpringWebSocket 的 Bean 条件
     * </p>
     *
     * @author YangMC
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

    /**
     * spring WebSocket 配置
     */
    public static class ImSpringWebSocketMessageBrokerConfigurer implements WebSocketMessageBrokerConfigurer {
        @Override
        public void registerStompEndpoints(StompEndpointRegistry registry) {
            registry.addEndpoint("/agency-im").setAllowedOriginPatterns("*");
        }
    }
}
