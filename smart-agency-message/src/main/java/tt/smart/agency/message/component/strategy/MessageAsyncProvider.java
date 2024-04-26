package tt.smart.agency.message.component.strategy;

import jakarta.servlet.AsyncContext;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.task.AsyncTaskExecutor;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.concurrent.Executor;

/**
 * <p>
 * 异步信息推送提供程序
 * </p>
 *
 * @author YangMC
 * @version V1.0
 **/
@Slf4j
public class MessageAsyncProvider implements Executor {

    private final AsyncTaskExecutor asyncTaskExecutor;

    public MessageAsyncProvider(@Qualifier("messageThreadPoolTaskExecutor") AsyncTaskExecutor asyncTaskExecutor) {
        this.asyncTaskExecutor = asyncTaskExecutor;
    }

    @Override
    public void execute(@NotNull Runnable command) {
        ServletRequestAttributes requestAttributes = null;
        try {
            requestAttributes = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        } catch (IllegalStateException e) {
            log.info("未找到线程绑定请求：您指的是实际 Web 请求之外的请求属性，还是在最初接收线程之外处理请求？如果您实际在 Web 请求中操作，但仍收到此消息，则您的代码可能在 DispatcherServlet 外部运行： 在这种情况下，请使用 RequestContextListener 或 RequestContextFilter 公开当前请求。");
        } finally {
            asyncTaskExecutor.submit(new ContextAwareTask(requestAttributes, command));
        }
    }

    /**
     * 上下文感知任务
     */
    static class ContextAwareTask implements Runnable {
        private final ServletRequestAttributes requestAttributes;
        private final Runnable task;

        public ContextAwareTask(ServletRequestAttributes requestAttributes, Runnable task) {
            this.requestAttributes = requestAttributes;
            this.task = task;
        }

        @Override
        public void run() {
            try {
                // 将当前请求的上下文信息传递给异步任务
                if (requestAttributes != null) {
                    // 线程上下文传递
                    RequestContextHolder.setRequestAttributes(requestAttributes, true);
                    // 开启异步
                    AsyncContext asyncContext = requestAttributes.getRequest().startAsync();
                    task.run();
                    asyncContext.complete();
                } else {
                    task.run();
                }
            } finally {
                // 清除当前线程的请求上下文
                RequestContextHolder.resetRequestAttributes();
            }
        }
    }
}