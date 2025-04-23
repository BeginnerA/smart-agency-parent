package com.reid.smart.agency.db.sql;

import org.junit.jupiter.api.Test;
import tt.smart.agency.sql.SqlBuilder;
import tt.smart.agency.sql.builder.sql.dml.InsertSqlBuilder;

import java.util.Arrays;

/**
 * <p>
 * 简单插入 SQL 测试
 * </p>
 *
 * @author MC_Yang
 * @version V1.0
 **/
public class SimpleInsertSql {

    @Test
    public void testSimpleInsertSql() {
        String sql1 = SqlBuilder.insertInto("t1", "c1", "c2", "c3")
            .values()
            .addValue("v1", "v2", "v3")
            .addValue("v11", "v22", "v33")
            .addValues(Arrays.asList(new Object[]{"v111", "v222", "v333"}, new Object[]{"v1111", "v2222", "v3333"}))
            .build();
        System.out.println(sql1);

        String sql2 = SqlBuilder.replaceInto("t2", "id", "c1", "c2", "c3")
            .selectAll()
            .from("t1")
            .limit(10)
            .build();
        System.out.println(sql2);
    }
}
