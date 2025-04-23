package com.reid.smart.agency.db.sql;

import org.junit.jupiter.api.Test;
import tt.smart.agency.sql.SqlBuilder;
import tt.smart.agency.sql.builder.sql.SqlBuilderRoute;
import tt.smart.agency.sql.constant.QueryRule;

import java.util.Arrays;

/**
 * <p>
 * 简单删除 SQL 测试
 * </p>
 *
 * @author MC_Yang
 * @version V1.0
 **/
public class SimpleDeleteSql {

    @Test
    public void testSimpleDeleteSql() {
        SqlBuilderRoute builder = SqlBuilder
                .delete("table_a")
                .where("column_1", QueryRule.GE, 10);
        System.out.println(builder.build());
        System.out.println(builder.precompileSql());
        System.out.println(Arrays.toString(builder.precompileArgs()));
    }

}
