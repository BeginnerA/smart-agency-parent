package com.reid.smart.agency.im.socketio;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * <p>
 * socket.io 属性
 * </p>
 *
 * @author MC_Yang
 * @version V1.0
 **/
@Data
@SuperBuilder
@NoArgsConstructor
@ConfigurationProperties(prefix = "socket-io")
public class SocketIoProperties {

    /**
     * 主机（IP地址/主机名）
     */
    protected String hostName;
    /**
     * 端口
     */
    protected Integer port;
    /**
     * 主线程
     */
    protected int bossCount;
    /**
     * 工作线程
     */
    protected int workCount;
    /**
     * 自定义请求 允许为不同于 socket.io 协议的自定义请求提供服务
     */
    protected boolean allowCustomRequests;
    /**
     * 协议升级超时
     */
    protected int upgradeTimeout;
    /**
     * ping 超时
     */
    protected int pingTimeout;
    /**
     * ping 间隔
     */
    protected int pingInterval;
    /**
     * 最大内容长度
     */
    protected int maxFramePayloadLength;
    /**
     * Http 最大内容长度
     */
    protected int maxHttpContentLength;

}