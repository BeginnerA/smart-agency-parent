package com.reid.smart.agency.db.sql;

import tt.smart.agency.sql.SqlBuilder;
import tt.smart.agency.sql.annotation.Column;
import tt.smart.agency.sql.annotation.Query;
import tt.smart.agency.sql.annotation.Table;
import tt.smart.agency.sql.config.GlobalConfig;
import tt.smart.agency.sql.constant.QueryRule;
import tt.smart.agency.sql.domain.Alias;

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
public class SimpleQuerySql6 {

    public static void main(String[] args) {
        GlobalConfig.OPEN_LAMBDA_TABLE_NAME_MODE = true;

        System.out.println(
                SqlBuilder.model(U.class)
                        .where(new UserOrderCriteria(1, null, null))
                        .orderByDesc(User::getId)
                        .build()
        );

        System.out.println(
                SqlBuilder.model(U.class)
                        .where(new UserOrderCriteria(null, null, "dragons"))
                        .build()
        );

        System.out.println(
                SqlBuilder.select(U.class).addColumn(Alias.of(O::getId, "order_id"))
                        .from(U.class)
                        .join(O.class)
                        .onEq(U::getId, tt.smart.agency.sql.domain.Column.as(O::getUid))
                        .where(new UserOrderCriteria(null, Arrays.asList(1, 2, 3), null))
                        .orderByAsc(U::getId).addDesc(O::getId)
                        .build()
        );

        System.out.println(
                SqlBuilder.select(U.class).addColumn(Alias.of(O::getId, "order_id"))
                        .from(U.class)
                        .leftJoin(O.class)
                        .onEq(U::getId, tt.smart.agency.sql.domain.Column.as(O::getUid))
                        .where(new UserOrderCriteria(null, Arrays.asList(1, 2, 3), "dragons"))
                        .orderByAsc(U::getId).addDesc(O::getId)
                        .limit(0, 10)
                        .build()
        );
    }

}

@Table("users")
class U {
    private Integer id;

    @Column("name")
    private String username;

    public Integer getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }
}

@Table("orders")
class O {
    private Integer id;

    private Integer uid;

    private String info;

    public Integer getId() {
        return id;
    }

    public Integer getUid() {
        return uid;
    }

    public String getInfo() {
        return info;
    }
}

class UserOrderCriteria {

    @Query(value = "users.id", type = QueryRule.GE)
    private Integer userId;

    @Query(value = "orders.id", type = QueryRule.IN)
    private List<Integer> orderIds;

    @Query(value = "users.name", type = QueryRule.LIKE)
    private String username;

    public UserOrderCriteria(Integer userId, List<Integer> orderIds, String username) {
        this.userId = userId;
        this.orderIds = orderIds;
        this.username = username;
    }
}
