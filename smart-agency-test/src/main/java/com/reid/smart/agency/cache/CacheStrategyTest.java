package com.reid.smart.agency.cache;

import cn.hutool.core.date.DateUnit;
import cn.hutool.core.date.DateUtil;
import cn.hutool.json.JSONObject;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import tt.smart.agency.cache.CacheStrategyManager;
import tt.smart.agency.cache.strategy.CacheStrategy;

import java.util.Date;

/**
 * <p>
 * 缓存策略测试
 * </p>
 *
 * @author YangMC
 * @version V1.0
 **/
@SpringBootTest
public class CacheStrategyTest {

    @Resource
    CacheStrategy<String, Object> cacheStrategy;

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

    @Test
    public void testCacheStrategy() {

        long between = DateUtil.between(new Date(), DateUtil.parse("2024-09-05"), DateUnit.DAY);
        System.out.println(between);

        cacheStrategy.put("1", new JSONObject().putOnce("test", "11111111"), 5000);
        System.out.print("CacheStrategy——value：" + cacheStrategy.get("1") + "\n");

        CacheStrategyManager.put("2", new JSONObject().putOnce("test", "22222222"), 5000);
        System.out.print("CacheStrategyManager——value：" + CacheStrategyManager.get("2"));
    }

}