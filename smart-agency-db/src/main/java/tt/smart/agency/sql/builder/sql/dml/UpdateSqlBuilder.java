package tt.smart.agency.sql.builder.sql.dml;

import tt.smart.agency.sql.builder.sql.SqlBuilderRoute;
import tt.smart.agency.sql.constant.CommonConstant;
import tt.smart.agency.sql.tools.SqlNameTools;

/**
 * <p>
 * UPDATE SQL 构造器
 * </p>
 *
 * @author MC_Yang
 * @version V1.0
 **/
public class UpdateSqlBuilder implements SqlBuilderRoute, SetSqlBuilderRoute {

    /**
     * 表
     */
    protected final String table;

    public UpdateSqlBuilder(String table) {
        this.table = SqlNameTools.handleName(table);
    }

    @Override
    public String precompileSql() {
        return "UPDATE " + table;
    }

    @Override
    public Object[] precompileArgs() {
        return CommonConstant.EMPTY_OBJECT_ARRAY;
    }

}
