package tt.smart.agency.sql.builder.sql.dql;

import tt.smart.agency.sql.builder.sql.SqlBuilderRoute;

/**
 * <p>
 * JOIN SQL 构造器
 * </p>
 *
 * @author MC_Yang
 * @version V1.0
 **/
public interface JoinSqlBuilderRoute extends SqlBuilderRoute {

    default JoinSqlBuilder join(Object table) {
        return new JoinSqlBuilder(precompileSql(), precompileArgs(), "JOIN", table);
    }

    default JoinSqlBuilder innerJoin(Object table) {
        return new JoinSqlBuilder(precompileSql(), precompileArgs(), "INNER JOIN", table);
    }

    default JoinSqlBuilder leftJoin(Object table) {
        return new JoinSqlBuilder(precompileSql(), precompileArgs(), "LEFT JOIN", table);
    }

    default JoinSqlBuilder rightJoin(Object table) {
        return new JoinSqlBuilder(precompileSql(), precompileArgs(), "RIGHT JOIN", table);
    }

    default JoinSqlBuilder fullJoin(Object table) {
        return new JoinSqlBuilder(precompileSql(), precompileArgs(), "FULL JOIN", table);
    }

}
