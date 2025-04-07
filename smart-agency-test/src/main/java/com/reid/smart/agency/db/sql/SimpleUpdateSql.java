package com.reid.smart.agency.db.sql;

import org.junit.jupiter.api.Test;
import tt.smart.agency.sql.SqlBuilder;
import tt.smart.agency.sql.builder.sql.SqlBuilderRoute;
import tt.smart.agency.sql.constant.QueryRule;

import java.util.Arrays;

/**
 * <p>
 * 简单更新 SQL 测试
 * </p>
 *
 * @author YangMC
 * @version V1.0
 **/
public class SimpleUpdateSql {

    @Test
    public void testSimpleUpdateSql() {
        SqlBuilderRoute builder = SqlBuilder
                .update("table_a")
                .set("column_1", 18)
                .where("column_2", QueryRule.EQ, "test");
        System.out.println(builder.build());
        System.out.println(builder.precompileSql());
        System.out.println(Arrays.toString(builder.precompileArgs()));
    }
}
