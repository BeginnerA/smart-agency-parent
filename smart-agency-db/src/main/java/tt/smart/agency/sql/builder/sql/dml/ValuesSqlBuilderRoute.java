package tt.smart.agency.sql.builder.sql.dml;

import tt.smart.agency.sql.builder.sql.SqlBuilderRoute;

/**
 * <p>
 * VALUES SQL 构造器
 * </p>
 *
 * @author MC_Yang
 * @version V1.0
 **/
public interface ValuesSqlBuilderRoute extends SqlBuilderRoute {

    default ValueSqlBuilder value() {
        return new ValueSqlBuilder(precompileSql(), precompileArgs());
    }

    default ValuesSqlBuilder values() {
        return new ValuesSqlBuilder(precompileSql(), precompileArgs());
    }
}
