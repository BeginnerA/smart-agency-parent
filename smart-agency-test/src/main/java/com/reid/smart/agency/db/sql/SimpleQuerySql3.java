package com.reid.smart.agency.db.sql;

import tt.smart.agency.sql.SqlBuilder;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * 简单查询 SQL 测试
 * </p>
 *
 * @author MC_Yang
 * @version V1.0
 **/
public class SimpleQuerySql3 {

    public static void main(String[] args) {
        Map<String, Object> criteria = new HashMap<>();
        criteria.put("a", "2");
        criteria.put("b", 1);
        criteria.put("c", Arrays.asList("1", "2", "3"));
        criteria.put("d", new Integer[]{1, 2, 3});
        System.out.println(
                SqlBuilder.selectAll().from("xxx")
                        .where(criteria)
                        .build()
        );
    }

}
