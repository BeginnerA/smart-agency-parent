package com.reid.smart.agency.db.sql;

import tt.smart.agency.sql.SqlBuilder;
import tt.smart.agency.sql.annotation.Column;
import tt.smart.agency.sql.annotation.Table;
import tt.smart.agency.sql.config.GlobalConfig;
import tt.smart.agency.sql.constant.QueryRule;

/**
 * <p>
 * 简单查询 SQL 测试
 * </p>
 *
 * @author MC_Yang
 * @version V1.0
 **/
public class SimpleQuerySql5 {

    public static void main(String[] args) {
        System.out.println(
                SqlBuilder.model(User.class)
                        .whereLike(User::getUsername, "dragons")
                        .andEq(User::getId, 3)
                        .build()
        );
        // 开启lambda字段表名模式
        GlobalConfig.OPEN_LAMBDA_TABLE_NAME_MODE = true;
        System.out.println(
                SqlBuilder.select(User::getId)
                        .from(User.class)
                        .join(Order.class)
                        .on(User::getId, QueryRule.EQ, tt.smart.agency.sql.domain.Column.as(Order::getUid))
                        .build()
        );

    }

}

@Table("users")
class User {
    @Column("name")
    private String username;

    private Long id;

    public Long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }
}

@Table("orders")
class Order {

    private Long id;

    private Long uid;

    public Long getId() {
        return id;
    }

    public Long getUid() {
        return uid;
    }
}
