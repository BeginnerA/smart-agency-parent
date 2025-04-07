package tt.smart.agency.sql.builder.sql.dql;

import tt.smart.agency.sql.builder.sql.LambdaFunction;
import tt.smart.agency.sql.builder.sql.SqlBuilderRoute;
import tt.smart.agency.sql.domain.Alias;

/**
 * <p>
 * SELECT SQL 构造器
 * </p>
 *
 * @author MC_Yang
 * @version V1.0
 **/
public interface SelectSqlBuilderRoute extends SqlBuilderRoute {


    default SelectSqlBuilder selectAll() {
        return new SelectSqlBuilder(precompileSql(), precompileArgs(), "*");
    }

    default SelectSqlBuilder select(String... columns) {
        return new SelectSqlBuilder(precompileSql(), precompileArgs(), columns);
    }

    default SelectSqlBuilder select(Alias... columns) {
        return new SelectSqlBuilder(precompileSql(), precompileArgs(), columns);
    }

    default SelectSqlBuilder select(Object... columns) {
        return new SelectSqlBuilder(precompileSql(), precompileArgs(), columns);
    }

    @SuppressWarnings({"unchecked", "varargs"})
    default <P> SelectSqlBuilder select(LambdaFunction<P, ?>... lambdaFunctions) {
        return new SelectSqlBuilder(precompileSql(), precompileArgs(), lambdaFunctions);
    }

}
