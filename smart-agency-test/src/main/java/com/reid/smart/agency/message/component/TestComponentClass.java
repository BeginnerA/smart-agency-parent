package com.reid.smart.agency.message.component;

import org.springframework.stereotype.Component;
import tt.smart.agency.message.component.annotation.MessageStrategy;

/**
 * <p>
 * 测试消息注解组件
 * </p>
 *
 * @author YangMC
 * @version V1.0
 **/
@Component
public class TestComponentClass {

    /**
     * 测试默认消息策略
     */
    @MessageStrategy
    public void testDefaultNotificationStrategy() {
        System.out.print("测试默认通知策略\n");
    }

    /**
     * 测试自定义微信消息策略
     */
    @MessageStrategy(strategy = TestWxNotificationStrategy.class)
    public void testWxNotificationStrategy() {
        System.out.print("测试自定义微信消息策略\n");
    }

    /**
     * 测试自定义钉钉消息策略
     */
    @MessageStrategy(strategy = TestDdNotificationStrategy.class)
    public void testDdNotificationStrategy() {
        System.out.print("测试自定义钉钉消息策略\n");
    }

    /**
     * 测试自定义短信消息策略
     */
    @MessageStrategy(strategy = TestSmsNotificationStrategy.class)
    public void testSmsNotificationStrategy() {
        System.out.print("测试自定义短信消息策略\n");
    }
}
