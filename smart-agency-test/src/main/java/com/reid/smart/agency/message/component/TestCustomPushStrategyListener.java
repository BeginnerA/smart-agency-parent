package com.reid.smart.agency.message.component;

import org.springframework.stereotype.Component;
import tt.smart.agency.message.component.domain.Message;
import tt.smart.agency.message.component.listener.CustomPushStrategyListener;
import tt.smart.agency.message.domain.MessageSendResult;

/**
 * <p>
 * 测试自定义推送策略监听器
 * </p>
 *
 * @author MC_Yang
 * @version V1.0
 **/
@Component
public class TestCustomPushStrategyListener implements CustomPushStrategyListener {
    @Override
    public MessageSendResult send(Message message) {
        System.out.println("执行自定义推送策略监听器:" + message + "\n");
        return null;
    }
}
