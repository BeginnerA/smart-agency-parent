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
public class SimpleQuerySql4 {

    public static void main(String[] args) {
        System.out.println(SqlBuilder.selectAll()
                .from("table_a")
                .where(new TagQueryCriteria(Arrays.asList(
                        new Tag("id", Arrays.asList(4, 5)),
                        new Tag("cat", Arrays.asList("7", "8"))
                )))
                .build()
        );
    }

}

class Tag {
    private String type;
    private List<Object> values;

    public Tag(String type, List<Object> values) {
        this.type = type;
        this.values = values;
    }

    public String getType() {
        return type;
    }
}

class TagQueryCriteria {
    @Query(value = "${type}", type = QueryRule.IN, attr = "values")
    private List<Tag> tags;

    public TagQueryCriteria(List<Tag> tags) {
        this.tags = tags;
    }
}
