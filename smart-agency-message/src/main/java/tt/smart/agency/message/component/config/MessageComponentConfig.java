package tt.smart.agency.message.component.config;

import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import tt.smart.agency.message.component.strategy.DefaultNotificationStrategy;

import java.util.concurrent.ThreadPoolExecutor;

/**
 * <p>
 * 消息线程池配置
 * </p>
 *
 * @author MC_Yang
 * @version V1.0
 **/
public class MessageComponentConfig {

    @Bean(name = "messageThreadPoolTaskExecutor")
    public ThreadPoolTaskExecutor messageThreadPoolTaskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        // 设置核心线程数
        executor.setCorePoolSize(5);
        // 设置最大线程数
        executor.setMaxPoolSize(10);
        // 设置队列大小
        executor.setQueueCapacity(200);
        // 设置线程活跃时间(秒)
        executor.setKeepAliveSeconds(60);
        // 设置等待终止时间(秒)
        executor.setAwaitTerminationSeconds(60);
        // 设置线程名前缀+分组名称
        executor.setThreadGroupName("MessageAsyncOperationGroup");
        executor.setThreadNamePrefix("MessageAsyncTaskExecutor-");
        // 所有任务结束后关闭线程池
        executor.setWaitForTasksToCompleteOnShutdown(true);
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        executor.initialize();
        return executor;
    }

    @Bean
    public DefaultNotificationStrategy defaultNotificationStrategy() {
        return new DefaultNotificationStrategy();
    }
}
