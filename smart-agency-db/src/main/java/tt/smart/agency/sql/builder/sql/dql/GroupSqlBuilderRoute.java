package tt.smart.agency.sql.builder.sql.dql;

import tt.smart.agency.sql.builder.sql.LambdaFunction;
import tt.smart.agency.sql.builder.sql.SqlBuilderRoute;
import tt.smart.agency.sql.tools.LambdaTools;

import java.util.Arrays;

/**
 * <p>
 * GROUP BY SQL 构造器
 * </p>
 *
 * @author MC_Yang
 * @version V1.0
 **/
public interface GroupSqlBuilderRoute extends SqlBuilderRoute {

    default GroupSqlBuilder groupBy(String... columns) {
        return new GroupSqlBuilder(precompileSql(), precompileArgs(), columns);
    }

    @SuppressWarnings({"unchecked", "varargs"})
    default <P> GroupSqlBuilder groupBy(LambdaFunction<P, ?>... lambdaFunctions) {
        return new GroupSqlBuilder(precompileSql(), precompileArgs(), Arrays.stream(lambdaFunctions).map(LambdaTools::getColumnName).toArray(String[]::new));
    }

    default GroupSqlBuilder groupBy(SqlBuilderRoute... subQueries) {
        return new GroupSqlBuilder(precompileSql(), precompileArgs(), subQueries);
    }

}
