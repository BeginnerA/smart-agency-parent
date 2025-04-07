package tt.smart.agency.sql.builder.sql.dml;

import lombok.extern.slf4j.Slf4j;
import tt.smart.agency.sql.builder.sql.LambdaFunction;
import tt.smart.agency.sql.builder.sql.SqlBuilderRoute;
import tt.smart.agency.sql.builder.sql.dql.SelectSqlBuilderRoute;
import tt.smart.agency.sql.constant.CommonConstant;
import tt.smart.agency.sql.constant.InsertMode;
import tt.smart.agency.sql.tools.LambdaTools;
import tt.smart.agency.sql.tools.SqlNameTools;

import java.util.Arrays;
import java.util.List;

/**
 * <p>
 * INSERT SQL 构造器
 * </p>
 *
 * @author MC_Yang
 * @version V1.0
 **/
@Slf4j
public class InsertSqlBuilder implements SqlBuilderRoute, ValuesSqlBuilderRoute, SelectSqlBuilderRoute {

    /**
     * 表
     */
    protected final String table;
    /**
     * INSERT 模式
     */
    protected final InsertMode mode;
    /**
     * 列
     */
    protected final List<String> columns;

    public InsertSqlBuilder(String table, InsertMode mode, String... columns) {
        this.table = SqlNameTools.handleName(table);
        this.mode = mode;
        this.columns = Arrays.stream(columns).map(SqlNameTools::handleName).toList();
    }

    public InsertSqlBuilder(String table, InsertMode mode, LambdaFunction<?, ?>... lambdaFunctions) {
        this.table = SqlNameTools.handleName(table);
        this.mode = mode;
        this.columns = Arrays.stream(lambdaFunctions).map(LambdaTools::getColumnName).map(SqlNameTools::handleName)
                .toList();
    }

    @Override
    public String precompileSql() {
        if (columns.isEmpty()) {
            return mode.getPrefix() + " " + table;
        }
        return mode.getPrefix() + " " + table + "(" + String.join(", ", columns) + ")";
    }

    @Override
    public Object[] precompileArgs() {
        return CommonConstant.EMPTY_OBJECT_ARRAY;
    }
}
