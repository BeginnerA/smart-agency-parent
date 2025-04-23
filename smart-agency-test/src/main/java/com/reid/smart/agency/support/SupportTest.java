package com.reid.smart.agency.support;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

/**
 * <p>
 * 测试
 * </p>
 *
 * @author MC_Yang
 * @version V1.0
 **/
public class SupportTest {

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
     * 导出简单的 Word 模板
     */
    @Test
    public void test() {
        // 上个月开始时间和结束时间
        DateTime yesterday = DateUtil.lastMonth();
        System.out.println(DateUtil.formatDate(DateUtil.beginOfMonth(yesterday)) + " 00:00:00");
        System.out.println(DateUtil.formatDate(DateUtil.endOfMonth(yesterday)) + " 23:59:59");
    }

}
