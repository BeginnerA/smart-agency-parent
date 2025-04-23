package com.reid.smart.agency.message.component;

import jakarta.annotation.Resource;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * <p>
 * 消息注解组件测试
 * </p>
 *
 * @author MC_Yang
 * @version V1.0
 **/
@SpringBootTest
public class ComponentTest {

    @Resource
    private TestComponentClass testComponentClass;

    /**
     * 开始
     */
    @BeforeAll
    public static void beforeClass() {
        System.out.println("\n\n------------------------ 基础测试 star ...");
    }

    /**
     * 结束
     */
    @AfterAll
    public static void afterClass() {
        System.out.println("\n\n------------------------ 基础测试 end ... \n");
    }

    /**
     * 测试默认通知策略
     */
    @Test
    public void testDefaultNotificationStrategy() {
        testComponentClass.testDefaultNotificationStrategy();
    }

    /**
     * 测试自定义通知策略
     */
    @Test
    public void testCustomNotificationStrategy() {
//        testComponentClass.testWxNotificationStrategy();
//        testComponentClass.testDdNotificationStrategy();
        testComponentClass.testSmsNotificationStrategy();
    }

}
