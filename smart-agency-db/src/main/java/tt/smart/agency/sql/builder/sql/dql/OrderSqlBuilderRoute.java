package tt.smart.agency.sql.builder.sql.dql;

import tt.smart.agency.sql.builder.sql.LambdaFunction;
import tt.smart.agency.sql.builder.sql.SqlBuilderRoute;
import tt.smart.agency.sql.constant.Order;
import tt.smart.agency.sql.tools.LambdaTools;

import java.util.function.Supplier;

/**
 * <p>
 * ORDER BY SQL 构造器
 * </p>
 *
 * @author MC_Yang
 * @version V1.0
 **/
public interface OrderSqlBuilderRoute extends SqlBuilderRoute {

    default OrderSqlBuilder orderBy(String sort) {
        return orderBy(Boolean.TRUE, sort);
    }

    default OrderSqlBuilder orderBy(Boolean predicate, String sort) {
        if (Boolean.TRUE.equals(predicate)) {
            return new OrderSqlBuilder(precompileSql(), precompileArgs(), sort);
        }
        return new OrderSqlBuilder(precompileSql(), precompileArgs());
    }

    default OrderSqlBuilder orderBy(Boolean predicate, Supplier<String> sort) {
        if (Boolean.TRUE.equals(predicate)) {
            return new OrderSqlBuilder(precompileSql(), precompileArgs(), sort.get());
        }
        return new OrderSqlBuilder(precompileSql(), precompileArgs());
    }

    default OrderSqlBuilder orderBy(String column, Order order) {
        return orderBy(Boolean.TRUE, column, order);
    }

    default OrderSqlBuilder orderBy(Boolean predicate, String column, Order order) {
        if (Boolean.TRUE.equals(predicate)) {
            return new OrderSqlBuilder(precompileSql(), precompileArgs(), column, order);
        }
        return new OrderSqlBuilder(precompileSql(), precompileArgs());
    }

    default <P>OrderSqlBuilder orderBy(LambdaFunction<P, ?> lambdaFunction, Order order) {
        return orderBy(LambdaTools.getColumnName(lambdaFunction), order);
    }

    default <P>OrderSqlBuilder orderBy(Boolean predicate, LambdaFunction<P, ?> lambdaFunction, Order order) {
        if (Boolean.TRUE.equals(predicate)) {
            return orderBy(lambdaFunction, order);
        }
        return new OrderSqlBuilder(precompileSql(), precompileArgs());
    }

    default OrderSqlBuilder orderByAsc(String ...columns) {
        return orderByAsc(Boolean.TRUE, columns);
    }

    default OrderSqlBuilder orderByAsc(Boolean predicate, String ...columns) {
        if (Boolean.TRUE.equals(predicate)) {
            return new OrderSqlBuilder(precompileSql(), precompileArgs()).addAsc(columns);
        }
        return new OrderSqlBuilder(precompileSql(), precompileArgs());
    }

    default <P>OrderSqlBuilder orderByAsc(LambdaFunction<P, ?> lambdaFunctions) {
        return new OrderSqlBuilder(precompileSql(), precompileArgs()).addAsc(lambdaFunctions);
    }

    default <P>OrderSqlBuilder orderByAsc(Boolean predicate, LambdaFunction<P, ?> lambdaFunctions) {
        if (Boolean.TRUE.equals(predicate)) {
            return orderByAsc(lambdaFunctions);
        }
        return new OrderSqlBuilder(precompileSql(), precompileArgs());
    }


    default OrderSqlBuilder orderByDesc(String ...columns) {
        return orderByDesc(Boolean.TRUE, columns);
    }

    default OrderSqlBuilder orderByDesc(Boolean predicate, String ...columns) {
        if (Boolean.TRUE.equals(predicate)) {
            return new OrderSqlBuilder(precompileSql(), precompileArgs()).addDesc(columns);
        }
        return new OrderSqlBuilder(precompileSql(), precompileArgs());
    }


    default <P>OrderSqlBuilder orderByDesc(LambdaFunction<P, ?> lambdaFunctions) {
        return new OrderSqlBuilder(precompileSql(), precompileArgs()).addDesc(lambdaFunctions);
    }

    default <P>OrderSqlBuilder orderByDesc(Boolean predicate, LambdaFunction<P, ?> lambdaFunctions) {
        if (Boolean.TRUE.equals(predicate)) {
            return orderByDesc(lambdaFunctions);
        }
        return new OrderSqlBuilder(precompileSql(), precompileArgs());
    }

}
