package com.reid.smart.agency.im.socketio;

import com.corundumstudio.socketio.SocketConfig;
import com.corundumstudio.socketio.SocketIOServer;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.annotation.Configuration;

/**
 * <p>
 * SocketIO 配置
 * </p>
 *
 * @author YangMC
 * @version V1.0
 **/
@Configuration
public class SocketIoConfig extends SocketIoProperties implements InitializingBean {

    @Override
    public void afterPropertiesSet() {
        SocketConfig socketConfig = new SocketConfig();
        socketConfig.setReuseAddress(true);
        socketConfig.setTcpNoDelay(true);
        socketConfig.setSoLinger(0);

        com.corundumstudio.socketio.Configuration configuration = new com.corundumstudio.socketio.Configuration();
//        configuration.setContext("/agency/socket.io");
        configuration.setSocketConfig(socketConfig);
        // host 在本地测试可以设置为 localhost 或者本机 IP，在 Linux 服务器跑可换成服务器IP
        configuration.setHostname(hostName);
        configuration.setPort(port);
        // socket 连接数大小（如只监听一个端口boss线程组为1即可）
        configuration.setBossThreads(bossCount);
        configuration.setWorkerThreads(workCount);
        configuration.setAllowCustomRequests(allowCustomRequests);
        // 协议升级超时时间（毫秒），默认10秒。HTTP 握手升级为 ws 协议超时时间
        configuration.setUpgradeTimeout(upgradeTimeout);
        // Ping 消息超时时间（毫秒），默认60秒，这个时间间隔内没有接收到心跳消息就会发送超时事件
        configuration.setPingTimeout(pingTimeout);
        // Ping 消息间隔（毫秒），默认25秒。客户端向服务器发送一条心跳消息间隔
        configuration.setPingInterval(pingInterval);

        SocketIOServer socketIOServer = new SocketIOServer(configuration);
        //添加事件监听器
//        socketIOServer.addListeners(socketIOHandler);
        // 启动 SocketIOServer
        socketIOServer.start();
        System.out.println("SocketIO启动完毕");
    }
}
