package com.reid.smart.agency.message.component;

import org.springframework.stereotype.Service;
import tt.smart.agency.message.component.domain.Message;
import tt.smart.agency.message.component.listener.MessageLogListener;
import tt.smart.agency.message.component.strategy.MessagePushPlatformEnum;
import tt.smart.agency.message.domain.MessageSendResult;

/**
 * <p>
 * 测试自定义消息日志监听器
 * </p>
 *
 * @author YangMC
 * @version V1.0
 **/
@Service
public class TestMessageLogListener implements MessageLogListener {
    @Override
    public void log(MessagePushPlatformEnum platform, Message message, MessageSendResult messageSendResult) {
        System.out.println("执行自定义消息日志监听器:\n-----推送平台->" + platform.getDescription() + "\n-----推送消息->" + message + "\n-----消息推送结果->" + messageSendResult + "\n");    }
}
