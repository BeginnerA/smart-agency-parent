package tt.smart.agency.sql.builder.sql.dql;

import tt.smart.agency.sql.builder.sql.SqlBuilderRoute;

/**
 * <p>
 * UNION SQL 构造器
 * </p>
 *
 * @author MC_Yang
 * @version V1.0
 **/
public interface UnionSqlBuilderRoute extends SqlBuilderRoute {

    default UnionSqlBuilder union(SqlBuilderRoute...unions) {
        return new UnionSqlBuilder(precompileSql(), precompileArgs(), unions);
    }

    default UnionSqlBuilder unionAll(SqlBuilderRoute ...unions) {
        return new UnionAllSqlBuilder(precompileSql(), precompileArgs(), unions);
    }

}
