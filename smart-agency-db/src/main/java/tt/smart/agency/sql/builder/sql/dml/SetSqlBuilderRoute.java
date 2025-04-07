package tt.smart.agency.sql.builder.sql.dml;

import tt.smart.agency.sql.builder.sql.LambdaFunction;
import tt.smart.agency.sql.builder.sql.SqlBuilderRoute;

/**
 * <p>
 * SET SQL 构造器
 * </p>
 *
 * @author MC_Yang
 * @version V1.0
 **/
public interface SetSqlBuilderRoute extends SqlBuilderRoute {

    default SetSqlBuilder set(String setter) {
        return new SetSqlBuilder(precompileSql(), precompileArgs(), setter);
    }

    default SetSqlBuilder set(String column, Object value) {
        return new SetSqlBuilder(precompileSql(), precompileArgs(), column, value);
    }

    default <P> SetSqlBuilder set(LambdaFunction<P, ?> lambdaFunction, Object value) {
        return new SetSqlBuilder(precompileSql(), precompileArgs(), lambdaFunction, value);
    }

}
