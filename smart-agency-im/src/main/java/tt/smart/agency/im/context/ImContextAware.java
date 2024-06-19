package tt.smart.agency.im.context;

import com.corundumstudio.socketio.SocketIOServer;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * <p>
 * 即时通讯上下文感知
 * </p>
 *
 * @author YangMC
 * @version V1.0
 **/
public class ImContextAware implements ApplicationContextAware {
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        System.out.println("SpringWebSocketContextAware");
        String[] beanNamesForType = applicationContext.getBeanNamesForType(SocketIOServer.class);
    }

}
