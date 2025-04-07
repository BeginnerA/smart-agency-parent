package tt.smart.agency.sql.builder.sql.dml;

import tt.smart.agency.sql.builder.sql.SqlBuilderRoute;
import tt.smart.agency.sql.builder.sql.dql.FromSqlBuilderRoute;
import tt.smart.agency.sql.builder.sql.dql.WhereSqlBuilderRoute;
import tt.smart.agency.sql.constant.CommonConstant;
import tt.smart.agency.sql.tools.SqlNameTools;

/**
 * <p>
 * DELETE SQL 构造器
 * </p>
 *
 * @author MC_Yang
 * @version V1.0
 **/
public class DeleteSqlBuilder implements SqlBuilderRoute, WhereSqlBuilderRoute, FromSqlBuilderRoute {

    /**
     * 表
     */
    protected final String table;

    public DeleteSqlBuilder(String table) {
        this.table = SqlNameTools.handleName(table);
    }

    @Override
    public String precompileSql() {
        return "DELETE FROM " + table;
    }

    @Override
    public Object[] precompileArgs() {
        return CommonConstant.EMPTY_OBJECT_ARRAY;
    }

}
