package com.reid.smart.agency.db.sql;

import tt.smart.agency.sql.SqlBuilder;
import tt.smart.agency.sql.annotation.Query;
import tt.smart.agency.sql.constant.QueryRule;

import java.util.Arrays;
import java.util.List;

/**
 * <p>
 * 简单查询 SQL 测试
 * </p>
 *
 * @author MC_Yang
 * @version V1.0
 **/
public class SimpleQuerySql2 {
    public static void main(String[] args) {
        System.out.println(
                SqlBuilder.selectAll().from("demo").where(new QueryCriteria(null, "dragons",
                                Arrays.asList(new Item(null, null), new Item(null, null))))
                        .build()
        );
    }
}

class Item {
    Integer id;
    String name;

    public Item(Integer id, String name) {
        this.id = id;
        this.name = name;
    }
}

class QueryCriteria {
    @Query
    private Integer id;
    @Query(type = QueryRule.LIKE)
    private String name;
    @Query(value = "item_id", type = QueryRule.IN, attr = "id")
    private List<Item> items;

    public QueryCriteria(Integer id, String name, List<Item> items) {
        this.id = id;
        this.name = name;
        this.items = items;
    }
}
