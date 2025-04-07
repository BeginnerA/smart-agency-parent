package tt.smart.agency.sql.builder.sql.dql;

import org.jetbrains.annotations.NotNull;
import tt.smart.agency.sql.builder.sql.LambdaFunction;
import tt.smart.agency.sql.builder.sql.SqlBuilderRoute;
import tt.smart.agency.sql.builder.sql.Tuple2;
import tt.smart.agency.sql.constant.CommonConstant;
import tt.smart.agency.sql.constant.QueryRule;
import tt.smart.agency.sql.tools.LambdaTools;
import tt.smart.agency.sql.tools.SqlNameTools;
import tt.smart.agency.sql.tools.SqlTemplateTools;

import java.util.function.Supplier;

/**
 * <p>
 * WHERE SQL 构造器
 * </p>
 *
 * @author MC_Yang
 * @version V1.0
 **/
public interface WhereSqlBuilderRoute extends SqlBuilderRoute {

    default WhereSqlBuilder where(WhereSqlBuilder whereSqlBuilder) {
        return where(Boolean.TRUE, whereSqlBuilder);
    }

    default WhereSqlBuilder where(Boolean predicate, WhereSqlBuilder whereSqlBuilder) {
        if (Boolean.TRUE.equals(predicate)) {
            return new WhereSqlBuilder(Boolean.TRUE, precompileSql(), precompileArgs(), whereSqlBuilder);
        }
        return new WhereSqlBuilder(Boolean.FALSE, precompileSql(), precompileArgs());
    }

    default WhereSqlBuilder where(Boolean predicate, Supplier<WhereSqlBuilder> whereSqlBuilder) {
        if (Boolean.TRUE.equals(predicate)) {
            return new WhereSqlBuilder(Boolean.TRUE, precompileSql(), precompileArgs(), whereSqlBuilder.get());
        }
        return new WhereSqlBuilder(Boolean.FALSE, precompileSql(), precompileArgs());
    }

    default WhereSqlBuilder where(Object... queryCriteria) {
        return where(Boolean.TRUE, queryCriteria);
    }

    default WhereSqlBuilder where(Boolean predicate, Object... queryCriteria) {
        return new WhereSqlBuilder(predicate, precompileSql(), precompileArgs(), queryCriteria);
    }

    default WhereSqlBuilder where(String condition, Object... params) {
        return where(Boolean.TRUE, condition, params);
    }

    default WhereSqlBuilder where(Boolean predicate, String condition, Object... params) {
        return new WhereSqlBuilder(predicate, precompileSql(), precompileArgs(), condition, params);
    }

    default WhereSqlBuilder where(Boolean predicate, String condition, Supplier<Object[]> params) {
        return new WhereSqlBuilder(predicate, precompileSql(), precompileArgs(), condition, params);
    }

    default WhereSqlBuilder where(String column, QueryRule rule, Object... values) {
        return where(Boolean.TRUE, column, rule, values);
    }

    default WhereSqlBuilder where(Boolean predicate, String column, QueryRule rule, Object... values) {
        return getWhereSqlBuilder(predicate, SqlTemplateTools.parsePrecompileCondition(SqlNameTools.handleName(column),
                rule, values));
    }

    default WhereSqlBuilder where(Boolean predicate, String column, QueryRule rule, Supplier<Object[]> values) {
        return getWhereSqlBuilder(predicate, SqlTemplateTools.parsePrecompileCondition(SqlNameTools.handleName(column),
                rule, values.get()));
    }

    default <P> WhereSqlBuilder where(LambdaFunction<P, ?> lambdaFunction, QueryRule rule, Object... values) {
        return where(LambdaTools.getColumnName(lambdaFunction), rule, values);
    }

    default <P> WhereSqlBuilder where(Boolean predicate, LambdaFunction<P, ?> lambdaFunction, QueryRule rule,
                                      Object... values) {
        if (Boolean.TRUE.equals(predicate)) {
            return where(LambdaTools.getColumnName(lambdaFunction), rule, values);
        }
        return new WhereSqlBuilder(Boolean.FALSE, precompileSql(), precompileArgs());
    }

    default <P> WhereSqlBuilder where(Boolean predicate, LambdaFunction<P, ?> lambdaFunction, QueryRule rule,
                                      Supplier<Object[]> values) {
        if (Boolean.TRUE.equals(predicate)) {
            return where(LambdaTools.getColumnName(lambdaFunction), rule, values.get());
        }
        return new WhereSqlBuilder(Boolean.FALSE, precompileSql(), precompileArgs());
    }

    default WhereSqlBuilder whereEq(String column, Object value) {
        return where(Boolean.TRUE, column, QueryRule.EQ, value);
    }

    default WhereSqlBuilder whereEq(Boolean predicate, String column, Object value) {
        return getWhereSqlBuilder(predicate, SqlTemplateTools.parsePrecompileCondition(SqlNameTools.handleName(column),
                QueryRule.EQ, value));
    }

    default WhereSqlBuilder whereEq(Boolean predicate, String column, Supplier<Object> value) {
        return getWhereSqlBuilder(predicate, SqlTemplateTools.parsePrecompileCondition(SqlNameTools.handleName(column),
                QueryRule.EQ, value.get()));
    }

    default <P> WhereSqlBuilder whereEq(LambdaFunction<P, ?> lambdaFunction, Object value) {
        return whereEq(LambdaTools.getColumnName(lambdaFunction), value);
    }

    default <P> WhereSqlBuilder whereEq(Boolean predicate, LambdaFunction<P, ?> lambdaFunction, Object value) {
        if (Boolean.TRUE.equals(predicate)) {
            return whereEq(LambdaTools.getColumnName(lambdaFunction), value);
        }
        return new WhereSqlBuilder(Boolean.FALSE, precompileSql(), precompileArgs());
    }

    default <P> WhereSqlBuilder whereEq(Boolean predicate, LambdaFunction<P, ?> lambdaFunction, Supplier<Object> value) {
        if (Boolean.TRUE.equals(predicate)) {
            return whereEq(LambdaTools.getColumnName(lambdaFunction), value.get());
        }
        return new WhereSqlBuilder(Boolean.FALSE, precompileSql(), precompileArgs());
    }

    default WhereSqlBuilder whereGt(String column, Object value) {
        return where(Boolean.TRUE, column, QueryRule.GT, value);
    }

    default WhereSqlBuilder whereGt(Boolean predicate, String column, Object value) {
        return getWhereSqlBuilder(predicate, SqlTemplateTools.parsePrecompileCondition(SqlNameTools.handleName(column),
                QueryRule.GT, value));
    }

    default WhereSqlBuilder whereGt(Boolean predicate, String column, Supplier<Object> value) {
        return getWhereSqlBuilder(predicate, SqlTemplateTools.parsePrecompileCondition(SqlNameTools.handleName(column),
                QueryRule.GT, value.get()));
    }

    default <P> WhereSqlBuilder whereGt(LambdaFunction<P, ?> lambdaFunction, Object value) {
        return whereGt(LambdaTools.getColumnName(lambdaFunction), value);
    }

    default <P> WhereSqlBuilder whereGt(Boolean predicate, LambdaFunction<P, ?> lambdaFunction, Object value) {
        if (Boolean.TRUE.equals(predicate)) {
            return whereGt(LambdaTools.getColumnName(lambdaFunction), value);
        }
        return new WhereSqlBuilder(Boolean.FALSE, precompileSql(), precompileArgs());
    }

    default <P> WhereSqlBuilder whereGt(Boolean predicate, LambdaFunction<P, ?> lambdaFunction, Supplier<Object> value) {
        if (Boolean.TRUE.equals(predicate)) {
            return whereGt(LambdaTools.getColumnName(lambdaFunction), value.get());
        }
        return new WhereSqlBuilder(Boolean.FALSE, precompileSql(), precompileArgs());
    }

    default WhereSqlBuilder whereGe(String column, Object value) {
        return where(Boolean.TRUE, column, QueryRule.GE, value);
    }

    default WhereSqlBuilder whereGe(Boolean predicate, String column, Object value) {
        return getWhereSqlBuilder(predicate, SqlTemplateTools.parsePrecompileCondition(SqlNameTools.handleName(column),
                QueryRule.GE, value));
    }

    default WhereSqlBuilder whereGe(Boolean predicate, String column, Supplier<Object> value) {
        return getWhereSqlBuilder(predicate, SqlTemplateTools.parsePrecompileCondition(SqlNameTools.handleName(column),
                QueryRule.GE, value.get()));
    }

    default <P> WhereSqlBuilder whereGe(LambdaFunction<P, ?> lambdaFunction, Object value) {
        return whereGe(LambdaTools.getColumnName(lambdaFunction), value);
    }

    default <P> WhereSqlBuilder whereGe(Boolean predicate, LambdaFunction<P, ?> lambdaFunction, Object value) {
        if (Boolean.TRUE.equals(predicate)) {
            return whereGe(LambdaTools.getColumnName(lambdaFunction), value);
        }
        return new WhereSqlBuilder(Boolean.FALSE, precompileSql(), precompileArgs());
    }

    default <P> WhereSqlBuilder whereGe(Boolean predicate, LambdaFunction<P, ?> lambdaFunction, Supplier<Object> value) {
        if (Boolean.TRUE.equals(predicate)) {
            return whereGe(LambdaTools.getColumnName(lambdaFunction), value.get());
        }
        return new WhereSqlBuilder(Boolean.FALSE, precompileSql(), precompileArgs());
    }

    default WhereSqlBuilder whereLt(String column, Object value) {
        return where(Boolean.TRUE, column, QueryRule.LT, value);
    }

    default WhereSqlBuilder whereLt(Boolean predicate, String column, Object value) {
        return getWhereSqlBuilder(predicate, SqlTemplateTools.parsePrecompileCondition(SqlNameTools.handleName(column),
                QueryRule.LT, value));
    }

    default WhereSqlBuilder whereLt(Boolean predicate, String column, Supplier<Object> value) {
        return getWhereSqlBuilder(predicate, SqlTemplateTools.parsePrecompileCondition(SqlNameTools.handleName(column),
                QueryRule.LT, value.get()));
    }

    default <P> WhereSqlBuilder whereLt(LambdaFunction<P, ?> lambdaFunction, Object value) {
        return whereLt(LambdaTools.getColumnName(lambdaFunction), value);
    }

    default <P> WhereSqlBuilder whereLt(Boolean predicate, LambdaFunction<P, ?> lambdaFunction, Object value) {
        if (Boolean.TRUE.equals(predicate)) {
            return whereLt(LambdaTools.getColumnName(lambdaFunction), value);
        }
        return new WhereSqlBuilder(Boolean.FALSE, precompileSql(), precompileArgs());
    }

    default <P> WhereSqlBuilder whereLt(Boolean predicate, LambdaFunction<P, ?> lambdaFunction, Supplier<Object> value) {
        if (Boolean.TRUE.equals(predicate)) {
            return whereLt(LambdaTools.getColumnName(lambdaFunction), value.get());
        }
        return new WhereSqlBuilder(Boolean.FALSE, precompileSql(), precompileArgs());
    }

    default WhereSqlBuilder whereLe(String column, Object value) {
        return where(Boolean.TRUE, column, QueryRule.LE, value);
    }

    default WhereSqlBuilder whereLe(Boolean predicate, String column, Object value) {
        return getWhereSqlBuilder(predicate, SqlTemplateTools.parsePrecompileCondition(SqlNameTools.handleName(column),
                QueryRule.LE, value));
    }

    default WhereSqlBuilder whereLe(Boolean predicate, String column, Supplier<Object> value) {
        return getWhereSqlBuilder(predicate, SqlTemplateTools.parsePrecompileCondition(SqlNameTools.handleName(column),
                QueryRule.LE, value.get()));
    }

    default <P> WhereSqlBuilder whereLe(LambdaFunction<P, ?> lambdaFunction, Object value) {
        return whereLe(LambdaTools.getColumnName(lambdaFunction), value);
    }

    default <P> WhereSqlBuilder whereLe(Boolean predicate, LambdaFunction<P, ?> lambdaFunction, Object value) {
        if (Boolean.TRUE.equals(predicate)) {
            return whereLe(LambdaTools.getColumnName(lambdaFunction), value);
        }
        return new WhereSqlBuilder(Boolean.FALSE, precompileSql(), precompileArgs());
    }

    default <P> WhereSqlBuilder whereLe(Boolean predicate, LambdaFunction<P, ?> lambdaFunction, Supplier<Object> value) {
        if (Boolean.TRUE.equals(predicate)) {
            return whereLe(LambdaTools.getColumnName(lambdaFunction), value.get());
        }
        return new WhereSqlBuilder(Boolean.FALSE, precompileSql(), precompileArgs());
    }

    default WhereSqlBuilder whereNe(String column, Object value) {
        return where(Boolean.TRUE, column, QueryRule.NE, value);
    }

    default WhereSqlBuilder whereNe(Boolean predicate, String column, Object value) {
        return getWhereSqlBuilder(predicate, SqlTemplateTools.parsePrecompileCondition(SqlNameTools.handleName(column),
                QueryRule.NE, value));
    }

    default WhereSqlBuilder whereNe(Boolean predicate, String column, Supplier<Object> value) {
        return getWhereSqlBuilder(predicate, SqlTemplateTools.parsePrecompileCondition(SqlNameTools.handleName(column),
                QueryRule.NE, value.get()));
    }

    default <P> WhereSqlBuilder whereNe(LambdaFunction<P, ?> lambdaFunction, Object value) {
        return whereNe(LambdaTools.getColumnName(lambdaFunction), value);
    }

    default <P> WhereSqlBuilder whereNe(Boolean predicate, LambdaFunction<P, ?> lambdaFunction, Object value) {
        if (Boolean.TRUE.equals(predicate)) {
            return whereNe(LambdaTools.getColumnName(lambdaFunction), value);
        }
        return new WhereSqlBuilder(Boolean.FALSE, precompileSql(), precompileArgs());
    }

    default <P> WhereSqlBuilder whereNe(Boolean predicate, LambdaFunction<P, ?> lambdaFunction, Supplier<Object> value) {
        if (Boolean.TRUE.equals(predicate)) {
            return whereNe(LambdaTools.getColumnName(lambdaFunction), value.get());
        }
        return new WhereSqlBuilder(Boolean.FALSE, precompileSql(), precompileArgs());
    }

    default WhereSqlBuilder whereLike(String column, Object value) {
        return where(Boolean.TRUE, column, QueryRule.LIKE, value);
    }

    default WhereSqlBuilder whereLike(Boolean predicate, String column, Object value) {
        return getWhereSqlBuilder(predicate, SqlTemplateTools.parsePrecompileCondition(SqlNameTools.handleName(column),
                QueryRule.LIKE, value));
    }

    default WhereSqlBuilder whereLike(Boolean predicate, String column, Supplier<Object> value) {
        return getWhereSqlBuilder(predicate, SqlTemplateTools.parsePrecompileCondition(SqlNameTools.handleName(column),
                QueryRule.LIKE, value.get()));
    }

    default <P> WhereSqlBuilder whereLike(LambdaFunction<P, ?> lambdaFunction, Object value) {
        return whereLike(LambdaTools.getColumnName(lambdaFunction), value);
    }

    default <P> WhereSqlBuilder whereLike(Boolean predicate, LambdaFunction<P, ?> lambdaFunction, Object value) {
        if (Boolean.TRUE.equals(predicate)) {
            return whereLike(LambdaTools.getColumnName(lambdaFunction), value);
        }
        return new WhereSqlBuilder(Boolean.FALSE, precompileSql(), precompileArgs());
    }

    default <P> WhereSqlBuilder whereLike(Boolean predicate, LambdaFunction<P, ?> lambdaFunction, Supplier<Object> value) {
        if (Boolean.TRUE.equals(predicate)) {
            return whereLike(LambdaTools.getColumnName(lambdaFunction), value.get());
        }
        return new WhereSqlBuilder(Boolean.FALSE, precompileSql(), precompileArgs());
    }

    default WhereSqlBuilder whereNotLike(String column, Object value) {
        return where(Boolean.TRUE, column, QueryRule.NOT_LEFT_LIKE, value);
    }

    default WhereSqlBuilder whereNotLike(Boolean predicate, String column, Object value) {
        return getWhereSqlBuilder(predicate, SqlTemplateTools.parsePrecompileCondition(SqlNameTools.handleName(column),
                QueryRule.NOT_LEFT_LIKE, value));
    }

    default WhereSqlBuilder whereNotLike(Boolean predicate, String column, Supplier<Object> value) {
        return getWhereSqlBuilder(predicate, SqlTemplateTools.parsePrecompileCondition(SqlNameTools.handleName(column),
                QueryRule.NOT_LEFT_LIKE, value.get()));
    }

    default <P> WhereSqlBuilder whereNotLike(LambdaFunction<P, ?> lambdaFunction, Object value) {
        return whereNotLike(LambdaTools.getColumnName(lambdaFunction), value);
    }

    default <P> WhereSqlBuilder whereNotLike(Boolean predicate, LambdaFunction<P, ?> lambdaFunction, Object value) {
        if (Boolean.TRUE.equals(predicate)) {
            return whereNotLike(LambdaTools.getColumnName(lambdaFunction), value);
        }
        return new WhereSqlBuilder(Boolean.FALSE, precompileSql(), precompileArgs());
    }

    default <P> WhereSqlBuilder whereNotLike(Boolean predicate, LambdaFunction<P, ?> lambdaFunction, Supplier<Object> value) {
        if (Boolean.TRUE.equals(predicate)) {
            return whereNotLike(LambdaTools.getColumnName(lambdaFunction), value.get());
        }
        return new WhereSqlBuilder(Boolean.FALSE, precompileSql(), precompileArgs());
    }

    default WhereSqlBuilder whereLLike(String column, Object value) {
        return where(Boolean.TRUE, column, QueryRule.LEFT_LIKE, value);
    }

    default WhereSqlBuilder whereLLike(Boolean predicate, String column, Object value) {
        return getWhereSqlBuilder(predicate, SqlTemplateTools.parsePrecompileCondition(SqlNameTools.handleName(column),
                QueryRule.LEFT_LIKE, value));
    }

    default WhereSqlBuilder whereLLike(Boolean predicate, String column, Supplier<Object> value) {
        return getWhereSqlBuilder(predicate, SqlTemplateTools.parsePrecompileCondition(SqlNameTools.handleName(column),
                QueryRule.LEFT_LIKE, value.get()));
    }

    default <P> WhereSqlBuilder whereLLike(LambdaFunction<P, ?> lambdaFunction, Object value) {
        return whereLLike(LambdaTools.getColumnName(lambdaFunction), value);
    }

    default <P> WhereSqlBuilder whereLLike(Boolean predicate, LambdaFunction<P, ?> lambdaFunction, Object value) {
        if (Boolean.TRUE.equals(predicate)) {
            return whereLLike(LambdaTools.getColumnName(lambdaFunction), value);
        }
        return new WhereSqlBuilder(Boolean.FALSE, precompileSql(), precompileArgs());
    }

    default <P> WhereSqlBuilder whereLLike(Boolean predicate, LambdaFunction<P, ?> lambdaFunction, Supplier<Object> value) {
        if (Boolean.TRUE.equals(predicate)) {
            return whereLLike(LambdaTools.getColumnName(lambdaFunction), value.get());
        }
        return new WhereSqlBuilder(Boolean.FALSE, precompileSql(), precompileArgs());
    }

    default WhereSqlBuilder whereNotLLike(String column, Object value) {
        return where(Boolean.TRUE, column, QueryRule.NOT_LEFT_LIKE, value);
    }

    default WhereSqlBuilder whereNotLLike(Boolean predicate, String column, Object value) {
        return getWhereSqlBuilder(predicate, SqlTemplateTools.parsePrecompileCondition(SqlNameTools.handleName(column),
                QueryRule.NOT_LEFT_LIKE, value));
    }

    default WhereSqlBuilder whereNotLLike(Boolean predicate, String column, Supplier<Object> value) {
        return getWhereSqlBuilder(predicate, SqlTemplateTools.parsePrecompileCondition(SqlNameTools.handleName(column),
                QueryRule.NOT_LEFT_LIKE, value.get()));
    }

    default <P> WhereSqlBuilder whereNotLLike(LambdaFunction<P, ?> lambdaFunction, Object value) {
        return whereNotLLike(LambdaTools.getColumnName(lambdaFunction), value);
    }

    default <P> WhereSqlBuilder whereNotLLike(Boolean predicate, LambdaFunction<P, ?> lambdaFunction, Object value) {
        if (Boolean.TRUE.equals(predicate)) {
            return whereNotLLike(LambdaTools.getColumnName(lambdaFunction), value);
        }
        return new WhereSqlBuilder(Boolean.FALSE, precompileSql(), precompileArgs());
    }

    default <P> WhereSqlBuilder whereNotLLike(Boolean predicate, LambdaFunction<P, ?> lambdaFunction, Supplier<Object> value) {
        if (Boolean.TRUE.equals(predicate)) {
            return whereNotLLike(LambdaTools.getColumnName(lambdaFunction), value.get());
        }
        return new WhereSqlBuilder(Boolean.FALSE, precompileSql(), precompileArgs());
    }

    default WhereSqlBuilder whereRLike(String column, Object value) {
        return where(Boolean.TRUE, column, QueryRule.RIGHT_LIKE, value);
    }

    default WhereSqlBuilder whereRLike(Boolean predicate, String column, Object value) {
        return getWhereSqlBuilder(predicate, SqlTemplateTools.parsePrecompileCondition(SqlNameTools.handleName(column),
                QueryRule.RIGHT_LIKE, value));
    }

    default WhereSqlBuilder whereRLike(Boolean predicate, String column, Supplier<Object> value) {
        return getWhereSqlBuilder(predicate, SqlTemplateTools.parsePrecompileCondition(SqlNameTools.handleName(column),
                QueryRule.RIGHT_LIKE, value.get()));
    }

    default <P> WhereSqlBuilder whereRLike(LambdaFunction<P, ?> lambdaFunction, Object value) {
        return whereRLike(LambdaTools.getColumnName(lambdaFunction), value);
    }

    default <P> WhereSqlBuilder whereRLike(Boolean predicate, LambdaFunction<P, ?> lambdaFunction, Object value) {
        if (Boolean.TRUE.equals(predicate)) {
            return whereRLike(LambdaTools.getColumnName(lambdaFunction), value);
        }
        return new WhereSqlBuilder(Boolean.FALSE, precompileSql(), precompileArgs());
    }

    default <P> WhereSqlBuilder whereRLike(Boolean predicate, LambdaFunction<P, ?> lambdaFunction, Supplier<Object> value) {
        if (Boolean.TRUE.equals(predicate)) {
            return whereRLike(LambdaTools.getColumnName(lambdaFunction), value.get());
        }
        return new WhereSqlBuilder(Boolean.FALSE, precompileSql(), precompileArgs());
    }

    default WhereSqlBuilder whereNotRLike(String column, Object... values) {
        return where(Boolean.TRUE, column, QueryRule.NOT_RIGHT_LIKE, values);
    }

    default WhereSqlBuilder whereNotRLike(Boolean predicate, String column, Object... values) {
        return getWhereSqlBuilder(predicate, SqlTemplateTools.parsePrecompileCondition(SqlNameTools.handleName(column),
                QueryRule.NOT_RIGHT_LIKE, values));
    }

    default WhereSqlBuilder whereNotRLike(Boolean predicate, String column, Supplier<Object> values) {
        return getWhereSqlBuilder(predicate, SqlTemplateTools.parsePrecompileCondition(SqlNameTools.handleName(column),
                QueryRule.NOT_RIGHT_LIKE, values.get()));
    }

    default <P> WhereSqlBuilder whereNotRLike(LambdaFunction<P, ?> lambdaFunction, Object value) {
        return whereNotRLike(LambdaTools.getColumnName(lambdaFunction), value);
    }

    default <P> WhereSqlBuilder whereNotRLike(Boolean predicate, LambdaFunction<P, ?> lambdaFunction, Object value) {
        if (Boolean.TRUE.equals(predicate)) {
            return whereNotRLike(LambdaTools.getColumnName(lambdaFunction), value);
        }
        return new WhereSqlBuilder(Boolean.FALSE, precompileSql(), precompileArgs());
    }

    default <P> WhereSqlBuilder whereNotRLike(Boolean predicate, LambdaFunction<P, ?> lambdaFunction, Supplier<Object> value) {
        if (Boolean.TRUE.equals(predicate)) {
            return whereNotRLike(LambdaTools.getColumnName(lambdaFunction), value.get());
        }
        return new WhereSqlBuilder(Boolean.FALSE, precompileSql(), precompileArgs());
    }

    default WhereSqlBuilder whereIn(String column, Object... values) {
        return where(Boolean.TRUE, column, QueryRule.IN, values);
    }

    default WhereSqlBuilder whereIn(Boolean predicate, String column, Object... values) {
        return getWhereSqlBuilder(predicate, SqlTemplateTools.parsePrecompileCondition(SqlNameTools.handleName(column),
                QueryRule.IN, values));
    }

    default WhereSqlBuilder whereIn(Boolean predicate, String column, Supplier<Object[]> values) {
        return getWhereSqlBuilder(predicate, SqlTemplateTools.parsePrecompileCondition(SqlNameTools.handleName(column),
                QueryRule.IN, values.get()));
    }

    default <P> WhereSqlBuilder whereIn(LambdaFunction<P, ?> lambdaFunction, Object... values) {
        return whereIn(LambdaTools.getColumnName(lambdaFunction), values);
    }

    default <P> WhereSqlBuilder whereIn(Boolean predicate, LambdaFunction<P, ?> lambdaFunction, Object... value) {
        if (Boolean.TRUE.equals(predicate)) {
            return whereIn(LambdaTools.getColumnName(lambdaFunction), value);
        }
        return new WhereSqlBuilder(Boolean.FALSE, precompileSql(), precompileArgs());
    }

    default <P> WhereSqlBuilder whereIn(Boolean predicate, LambdaFunction<P, ?> lambdaFunction, Supplier<Object[]> value) {
        if (Boolean.TRUE.equals(predicate)) {
            return whereIn(LambdaTools.getColumnName(lambdaFunction), value.get());
        }
        return new WhereSqlBuilder(Boolean.FALSE, precompileSql(), precompileArgs());
    }

    default WhereSqlBuilder whereNotIn(String column, Object... values) {
        return where(Boolean.TRUE, column, QueryRule.NOT_IN, values);
    }

    default WhereSqlBuilder whereNotIn(Boolean predicate, String column, Object... values) {
        return getWhereSqlBuilder(predicate, SqlTemplateTools.parsePrecompileCondition(SqlNameTools.handleName(column),
                QueryRule.NOT_IN, values));
    }

    default WhereSqlBuilder whereNotIn(Boolean predicate, String column, Supplier<Object[]> values) {
        return getWhereSqlBuilder(predicate, SqlTemplateTools.parsePrecompileCondition(SqlNameTools.handleName(column),
                QueryRule.NOT_IN, values.get()));
    }

    default <P> WhereSqlBuilder whereNotIn(LambdaFunction<P, ?> lambdaFunction, Object... values) {
        return whereNotIn(LambdaTools.getColumnName(lambdaFunction), values);
    }

    default <P> WhereSqlBuilder whereNotIn(Boolean predicate, LambdaFunction<P, ?> lambdaFunction, Object... value) {
        if (Boolean.TRUE.equals(predicate)) {
            return whereNotIn(LambdaTools.getColumnName(lambdaFunction), value);
        }
        return new WhereSqlBuilder(Boolean.FALSE, precompileSql(), precompileArgs());
    }

    default <P> WhereSqlBuilder whereNotIn(Boolean predicate, LambdaFunction<P, ?> lambdaFunction, Supplier<Object[]> value) {
        if (Boolean.TRUE.equals(predicate)) {
            return whereNotIn(LambdaTools.getColumnName(lambdaFunction), value.get());
        }
        return new WhereSqlBuilder(Boolean.FALSE, precompileSql(), precompileArgs());
    }

    default WhereSqlBuilder whereBetween(String column, Object... values) {
        return where(Boolean.TRUE, column, QueryRule.BETWEEN_AND, values);
    }

    default WhereSqlBuilder whereBetween(Boolean predicate, String column, Object... values) {
        return getWhereSqlBuilder(predicate, SqlTemplateTools.parsePrecompileCondition(SqlNameTools.handleName(column),
                QueryRule.BETWEEN_AND, values));
    }

    default WhereSqlBuilder whereBetween(Boolean predicate, String column, Supplier<Object> value1, Supplier<Object> value2) {
        return getWhereSqlBuilder(predicate, SqlTemplateTools.parsePrecompileCondition(SqlNameTools.handleName(column),
                QueryRule.BETWEEN_AND, value1, value2));
    }

    default WhereSqlBuilder whereBetween(Boolean predicate, String column, Supplier<Object[]> values) {
        return getWhereSqlBuilder(predicate, SqlTemplateTools.parsePrecompileCondition(SqlNameTools.handleName(column),
                QueryRule.BETWEEN_AND, values.get()));
    }

    default <P> WhereSqlBuilder whereBetween(LambdaFunction<P, ?> lambdaFunction, Object... values) {
        return whereBetween(LambdaTools.getColumnName(lambdaFunction), values);
    }

    default <P> WhereSqlBuilder whereBetween(Boolean predicate, LambdaFunction<P, ?> lambdaFunction, Object... values) {
        if (Boolean.TRUE.equals(predicate)) {
            return whereBetween(LambdaTools.getColumnName(lambdaFunction), values);
        }
        return new WhereSqlBuilder(Boolean.FALSE, precompileSql(), precompileArgs());
    }

    default <P> WhereSqlBuilder whereBetween(Boolean predicate, LambdaFunction<P, ?> lambdaFunction,
                                             Supplier<Object> value1, Supplier<Object> value2) {
        if (Boolean.TRUE.equals(predicate)) {
            return whereBetween(LambdaTools.getColumnName(lambdaFunction), value1.get(), value2.get());
        }
        return new WhereSqlBuilder(Boolean.FALSE, precompileSql(), precompileArgs());
    }

    default <P> WhereSqlBuilder whereBetween(Boolean predicate, LambdaFunction<P, ?> lambdaFunction,
                                             Supplier<Object[]> value) {
        if (Boolean.TRUE.equals(predicate)) {
            return whereBetween(LambdaTools.getColumnName(lambdaFunction), value.get());
        }
        return new WhereSqlBuilder(Boolean.FALSE, precompileSql(), precompileArgs());
    }

    default WhereSqlBuilder whereNotBetween(String column, Object... values) {
        return where(Boolean.TRUE, column, QueryRule.NOT_BETWEEN_AND, values);
    }

    default WhereSqlBuilder whereNotBetween(Boolean predicate, String column, Object... values) {
        return getWhereSqlBuilder(predicate, SqlTemplateTools.parsePrecompileCondition(SqlNameTools.handleName(column),
                QueryRule.NOT_BETWEEN_AND, values));
    }

    default WhereSqlBuilder whereNotBetween(Boolean predicate, String column, Supplier<Object> value1,
                                            Supplier<Object> value2) {
        return getWhereSqlBuilder(predicate, SqlTemplateTools.parsePrecompileCondition(SqlNameTools.handleName(column),
                QueryRule.NOT_BETWEEN_AND, value1.get(), value2.get()));
    }

    default WhereSqlBuilder whereNotBetween(Boolean predicate, String column, Supplier<Object[]> values) {
        return getWhereSqlBuilder(predicate, SqlTemplateTools.parsePrecompileCondition(SqlNameTools.handleName(column),
                QueryRule.NOT_BETWEEN_AND, values.get()));
    }

    default <P> WhereSqlBuilder whereNotBetween(LambdaFunction<P, ?> lambdaFunction, Object... values) {
        return whereNotBetween(LambdaTools.getColumnName(lambdaFunction), values);
    }

    default <P> WhereSqlBuilder whereNotBetween(Boolean predicate, LambdaFunction<P, ?> lambdaFunction, Object... values) {
        if (Boolean.TRUE.equals(predicate)) {
            return whereNotBetween(LambdaTools.getColumnName(lambdaFunction), values);
        }
        return new WhereSqlBuilder(Boolean.FALSE, precompileSql(), precompileArgs());
    }

    default <P> WhereSqlBuilder whereNotBetween(Boolean predicate, LambdaFunction<P, ?> lambdaFunction,
                                                Supplier<Object> value1, Supplier<Object> value2) {
        if (Boolean.TRUE.equals(predicate)) {
            return whereNotBetween(LambdaTools.getColumnName(lambdaFunction), value1.get(), value2.get());
        }
        return new WhereSqlBuilder(Boolean.FALSE, precompileSql(), precompileArgs());
    }

    default <P> WhereSqlBuilder whereNotBetween(Boolean predicate, LambdaFunction<P, ?> lambdaFunction,
                                                Supplier<Object[]> value) {
        if (Boolean.TRUE.equals(predicate)) {
            return whereNotBetween(LambdaTools.getColumnName(lambdaFunction), value.get());
        }
        return new WhereSqlBuilder(Boolean.FALSE, precompileSql(), precompileArgs());
    }

    default WhereSqlBuilder whereNull(String column) {
        return where(Boolean.TRUE, column, QueryRule.IS_NULL);
    }

    default <P> WhereSqlBuilder whereNull(LambdaFunction<P, ?> lambdaFunction) {
        return whereNull(LambdaTools.getColumnName(lambdaFunction));
    }

    default <P> WhereSqlBuilder whereNull(Boolean predicate, LambdaFunction<P, ?> lambdaFunction) {
        if (Boolean.TRUE.equals(predicate)) {
            return whereNull(LambdaTools.getColumnName(lambdaFunction));
        }
        return new WhereSqlBuilder(Boolean.FALSE, precompileSql(), precompileArgs());
    }

    default WhereSqlBuilder whereNull(Boolean predicate, String column) {
        if (Boolean.TRUE.equals(predicate)) {
            return where(Boolean.TRUE, column, QueryRule.IS_NULL);
        }
        return new WhereSqlBuilder(Boolean.FALSE, precompileSql(), precompileArgs());
    }

    default WhereSqlBuilder whereNotNull(String column) {
        return where(Boolean.TRUE, column, QueryRule.NOT_NULL);
    }

    default WhereSqlBuilder whereNotNull(Boolean predicate, String column) {
        if (Boolean.TRUE.equals(predicate)) {
            return where(Boolean.TRUE, column, QueryRule.NOT_NULL);
        }
        return new WhereSqlBuilder(Boolean.FALSE, precompileSql(), precompileArgs());
    }

    default <P> WhereSqlBuilder whereNotNull(LambdaFunction<P, ?> lambdaFunction) {
        return whereNotNull(LambdaTools.getColumnName(lambdaFunction));
    }

    default <P> WhereSqlBuilder whereNotNull(Boolean predicate, LambdaFunction<P, ?> lambdaFunction) {
        if (Boolean.TRUE.equals(predicate)) {
            return whereNotNull(LambdaTools.getColumnName(lambdaFunction));
        }
        return new WhereSqlBuilder(Boolean.FALSE, precompileSql(), precompileArgs());
    }

    default WhereSqlBuilder whereExists(Object... builder) {
        return where(Boolean.TRUE, CommonConstant.EMPTY_STRING, QueryRule.EXISTS, builder);
    }

    default WhereSqlBuilder whereExists(Boolean predicate, Object... builder) {
        return getWhereSqlBuilder(predicate, SqlTemplateTools.parsePrecompileCondition(null,
                QueryRule.EXISTS, builder));
    }

    default WhereSqlBuilder whereExists(Boolean predicate, Supplier<Object> builder) {
        return getWhereSqlBuilder(predicate, SqlTemplateTools.parsePrecompileCondition(null,
                QueryRule.EXISTS, builder));
    }

    default WhereSqlBuilder whereNotExists(Object... builder) {
        return where(Boolean.TRUE, CommonConstant.EMPTY_STRING, QueryRule.NOT_EXISTS, builder);
    }

    default WhereSqlBuilder whereNotExists(Boolean predicate, Object... builder) {
        return getWhereSqlBuilder(predicate, SqlTemplateTools.parsePrecompileCondition(null,
                QueryRule.NOT_EXISTS, builder));
    }

    default WhereSqlBuilder whereNotExists(Boolean predicate, Supplier<Object> builder) {
        return getWhereSqlBuilder(predicate, SqlTemplateTools.parsePrecompileCondition(null,
                QueryRule.NOT_EXISTS, builder));
    }

    @NotNull
    private WhereSqlBuilder getWhereSqlBuilder(Boolean predicate, Tuple2<String, Object[]> stringTuple2) {
        if (Boolean.TRUE.equals(predicate)) {
            return new WhereSqlBuilder(Boolean.TRUE, precompileSql(), precompileArgs(), stringTuple2.t1, stringTuple2.t2);
        }
        return new WhereSqlBuilder(Boolean.FALSE, precompileSql(), precompileArgs());
    }

}
