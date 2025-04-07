package tt.smart.agency.sql.builder.sql.dql;

import tt.smart.agency.sql.builder.sql.SqlBuilderRoute;

/**
 * <p>
 * LIMIT SQL 构造器
 * </p>
 *
 * @author MC_Yang
 * @version V1.0
 **/
public interface LimitSqlBuilderRoute extends SqlBuilderRoute {

    default LimitSqlBuilder limit(int count) {
        return new LimitSqlBuilder(precompileSql(), precompileArgs(), count);
    }

    default LimitSqlBuilder limit(int offset, int count) {
        return new LimitSqlBuilder(precompileSql(), precompileArgs(), offset, count);
    }

    default LimitSqlBuilder limit(Boolean predicate, int count) {
        if (Boolean.TRUE.equals(predicate)) {
            return new LimitSqlBuilder(precompileSql(), precompileArgs(), count);
        }
        return new LimitSqlBuilder(precompileSql(), precompileArgs());
    }

    default LimitSqlBuilder limit(Boolean predicate, int offset, int count) {
        if (Boolean.TRUE.equals(predicate)) {
            return new LimitSqlBuilder(precompileSql(), precompileArgs(), offset, count);
        }
        return new LimitSqlBuilder(precompileSql(), precompileArgs());
    }

}
