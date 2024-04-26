package com.reid.smart.agency.message.sms;

import jakarta.annotation.Resource;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import tt.smart.agency.message.api.sms.AlibabaSms;
import tt.smart.agency.message.domain.sms.SmsResponseResult;
import tt.smart.agency.message.enums.sms.SupplierPlatformType;
import tt.smart.agency.message.factory.sms.SmsMessageFactory;

/**
 * <p>
 * 短信消息测试
 * </p>
 *
 * @author YangMC
 * @version V1.0
 **/
@SpringBootTest
public class SmsMessageTest {

    @Resource
    private AlibabaSms alibabaSms;

    /**
     * 填测试手机号
     */
    private static final String PHONE = "18287959467";

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
     * 阿里云短信测试
     */
    @Test
    public void alibabaSmsTest() {
//        SmsResponseResult smsResponseResult = SmsMessageFactory.createSmsBlend(SupplierType.ALIBABA).sendMessage(PHONE, "10101");
        SmsResponseResult smsResponseResult = alibabaSms.sendMessage(PHONE, "10101");
        System.out.print("结果：" + smsResponseResult);
    }

    /**
     * 华为云短信测试
     */
    @Test
    public void huaweiSmsTest() {
        SmsResponseResult smsResponseResult = SmsMessageFactory.createSmsBlend(SupplierPlatformType.HUAWEI).sendMessage(PHONE, "10101");
        System.out.print("结果：" + smsResponseResult);
    }

    /**
     * 腾讯云短信测试
     */
    @Test
    public void tencentSmsTest() {
        SmsResponseResult smsResponseResult = SmsMessageFactory.createSmsBlend(SupplierPlatformType.TENCENT).sendMessage(PHONE, "10101");
        System.out.print("结果：" + smsResponseResult);
    }

    /**
     * 天翼云短信测试
     */
    @Test
    public void ctyunSmsTest() {
        SmsResponseResult smsResponseResult = SmsMessageFactory.createSmsBlend(SupplierPlatformType.CTYUN).sendMessage(PHONE, "10101");
        System.out.print("结果：" + smsResponseResult);
    }

    /**
     * 网易云短信测试
     */
    @Test
    public void neteaseSmsTest() {
        SmsResponseResult smsResponseResult = SmsMessageFactory.createSmsBlend(SupplierPlatformType.NETEASE).sendMessage(PHONE, "10101");
        System.out.print("结果：" + smsResponseResult);
    }

}