package com.reid.smart.agency.db.sql;

import org.junit.jupiter.api.Test;
import tt.smart.agency.sql.SqlBuilder;
import tt.smart.agency.sql.builder.sql.SqlBuilderRoute;
import tt.smart.agency.sql.builder.sql.dql.Conditions;
import tt.smart.agency.sql.constant.Order;
import tt.smart.agency.sql.constant.QueryRule;

import java.util.Arrays;

/**
 * <p>
 * 简单查询 SQL 测试
 * </p>
 *
 * @author MC_Yang
 * @version V1.0
 **/
public class SimpleQuerySql {

    @Test
    public void testSimpleQuerySql() {
        SqlBuilderRoute builder = SqlBuilder
                .select("t1.*", "t2.*")
                .from("t1")
                .join("t2")
                .on("t1.a = t2.a")
                .where("t1.b", QueryRule.GE, 10)
                .orLe("t2.b", 5)
                .or(Conditions.where("t1.c", QueryRule.IN, 3, 4, 5).and("t2.c", QueryRule.NOT_BETWEEN_AND, 5, 10))
                .andLe("t1.d", SqlBuilder.select("count(*)").from("t3").where("t3.a < ?", 100))
                .andLike("t1.b", 1)
                .and(true, Conditions.whereLe("id", 10), Conditions.whereLe("ip", 20))
                .groupBy("t1.z")
                .having("count(1)", QueryRule.GE, 100)
                .orderBy("t1.z", Order.ASC)
                .limit(10, 100);
        System.out.println(builder.build());
        System.out.println(builder.precompileSql());
        System.out.println(Arrays.toString(builder.precompileArgs()));
    }
}
