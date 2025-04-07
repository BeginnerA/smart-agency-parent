package tt.smart.agency.sql.builder.sql.dql;

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
 * JOIN 链接构造器
 * </p>
 *
 * @author MC_Yang
 * @version V1.0
 **/
public interface JoinOnSqlBuilderRoute extends SqlBuilderRoute {

    default JoinOnSqlBuilder on(JoinOnSqlBuilder JoinOnSqlBuilder) {
        return on(Boolean.TRUE, JoinOnSqlBuilder);
    }

    default JoinOnSqlBuilder on(Boolean predicate, JoinOnSqlBuilder JoinOnSqlBuilder) {
        if (Boolean.TRUE.equals(predicate)) {
            return new JoinOnSqlBuilder(Boolean.TRUE, precompileSql(), precompileArgs(), JoinOnSqlBuilder);
        }
        return new JoinOnSqlBuilder(Boolean.FALSE, precompileSql(), precompileArgs());
    }

    default JoinOnSqlBuilder on(Boolean predicate, Supplier<JoinOnSqlBuilder> JoinOnSqlBuilder) {
        if (Boolean.TRUE.equals(predicate)) {
            return new JoinOnSqlBuilder(Boolean.TRUE, precompileSql(), precompileArgs(), JoinOnSqlBuilder.get());
        }
        return new JoinOnSqlBuilder(Boolean.FALSE, precompileSql(), precompileArgs());
    }

    default JoinOnSqlBuilder on(Object... queryCriteria) {
        return on(Boolean.TRUE, queryCriteria);
    }

    default JoinOnSqlBuilder on(Boolean predicate, Object... queryCriteria) {
        return new JoinOnSqlBuilder(predicate, precompileSql(), precompileArgs(), queryCriteria);
    }

    default JoinOnSqlBuilder on(String condition, Object... params) {
        return on(Boolean.TRUE, condition, params);
    }

    default JoinOnSqlBuilder on(Boolean predicate, String condition, Object... params) {
        return new JoinOnSqlBuilder(predicate, precompileSql(), precompileArgs(), condition, params);
    }

    default JoinOnSqlBuilder on(Boolean predicate, String condition, Supplier<Object[]> params) {
        return new JoinOnSqlBuilder(predicate, precompileSql(), precompileArgs(), condition, params);
    }

    default JoinOnSqlBuilder on(String column, QueryRule option, Object... values) {
        return on(Boolean.TRUE, column, option, values);
    }

    default JoinOnSqlBuilder on(Boolean predicate, String column, QueryRule option, Object... values) {
        return handleJoinOnSql(option, predicate, column, () -> values);
    }

    default JoinOnSqlBuilder on(Boolean predicate, String column, QueryRule option, Supplier<Object[]> values) {
        return handleJoinOnSql(option, predicate, column, values);
    }

    default <P> JoinOnSqlBuilder on(LambdaFunction<P, ?> lambdaFunction, QueryRule option, Object... values) {
        return on(LambdaTools.getColumnName(lambdaFunction), option, values);
    }

    default <P> JoinOnSqlBuilder on(Boolean predicate, LambdaFunction<P, ?> lambdaFunction, QueryRule option, Object... values) {
        if (Boolean.TRUE.equals(predicate)) {
            return on(LambdaTools.getColumnName(lambdaFunction), option, values);
        }
        return new JoinOnSqlBuilder(Boolean.FALSE, precompileSql(), precompileArgs());
    }

    default <P> JoinOnSqlBuilder on(Boolean predicate, LambdaFunction<P, ?> lambdaFunction, QueryRule option, Supplier<Object[]> values) {
        if (Boolean.TRUE.equals(predicate)) {
            return on(LambdaTools.getColumnName(lambdaFunction), option, values.get());
        }
        return new JoinOnSqlBuilder(Boolean.FALSE, precompileSql(), precompileArgs());
    }

    default JoinOnSqlBuilder onEq(String column, Object value) {
        return on(Boolean.TRUE, column, QueryRule.EQ, value);
    }

    default JoinOnSqlBuilder onEq(Boolean predicate, String column, Object value) {
        return handleJoinOnSql(QueryRule.EQ, predicate, column, () -> value);
    }

    default JoinOnSqlBuilder onEq(Boolean predicate, String column, Supplier<Object> value) {
        return handleJoinOnSql(QueryRule.EQ, predicate, column, value);
    }

    default <P> JoinOnSqlBuilder onEq(LambdaFunction<P, ?> lambdaFunction, Object value) {
        return onEq(LambdaTools.getColumnName(lambdaFunction), value);
    }

    default <P> JoinOnSqlBuilder onEq(Boolean predicate, LambdaFunction<P, ?> lambdaFunction, Object value) {
        if (Boolean.TRUE.equals(predicate)) {
            return onEq(LambdaTools.getColumnName(lambdaFunction), value);
        }
        return new JoinOnSqlBuilder(Boolean.FALSE, precompileSql(), precompileArgs());
    }

    default <P> JoinOnSqlBuilder onEq(Boolean predicate, LambdaFunction<P, ?> lambdaFunction, Supplier<Object> value) {
        if (Boolean.TRUE.equals(predicate)) {
            return onEq(LambdaTools.getColumnName(lambdaFunction), value.get());
        }
        return new JoinOnSqlBuilder(Boolean.FALSE, precompileSql(), precompileArgs());
    }

    default JoinOnSqlBuilder onGt(String column, Object value) {
        return on(Boolean.TRUE, column, QueryRule.GT, value);
    }

    default JoinOnSqlBuilder onGt(Boolean predicate, String column, Object value) {
        return handleJoinOnSql(QueryRule.GT, predicate, column, () -> value);
    }

    default JoinOnSqlBuilder onGt(Boolean predicate, String column, Supplier<Object> value) {
        return handleJoinOnSql(QueryRule.GT, predicate, column, value);
    }

    default <P> JoinOnSqlBuilder onGt(LambdaFunction<P, ?> lambdaFunction, Object value) {
        return onGt(LambdaTools.getColumnName(lambdaFunction), value);
    }

    default <P> JoinOnSqlBuilder onGt(Boolean predicate, LambdaFunction<P, ?> lambdaFunction, Object value) {
        if (Boolean.TRUE.equals(predicate)) {
            return onGt(LambdaTools.getColumnName(lambdaFunction), value);
        }
        return new JoinOnSqlBuilder(Boolean.FALSE, precompileSql(), precompileArgs());
    }

    default <P> JoinOnSqlBuilder onGt(Boolean predicate, LambdaFunction<P, ?> lambdaFunction, Supplier<Object> value) {
        if (Boolean.TRUE.equals(predicate)) {
            return onGt(LambdaTools.getColumnName(lambdaFunction), value.get());
        }
        return new JoinOnSqlBuilder(Boolean.FALSE, precompileSql(), precompileArgs());
    }

    default JoinOnSqlBuilder onGe(String column, Object value) {
        return on(Boolean.TRUE, column, QueryRule.GE, value);
    }

    default JoinOnSqlBuilder onGe(Boolean predicate, String column, Object value) {
        return handleJoinOnSql(QueryRule.GE, predicate, column, () -> value);
    }

    default JoinOnSqlBuilder onGe(Boolean predicate, String column, Supplier<Object> value) {
        return handleJoinOnSql(QueryRule.GE, predicate, column, value);
    }

    default <P> JoinOnSqlBuilder onGe(LambdaFunction<P, ?> lambdaFunction, Object value) {
        return onGe(LambdaTools.getColumnName(lambdaFunction), value);
    }

    default <P> JoinOnSqlBuilder onGe(Boolean predicate, LambdaFunction<P, ?> lambdaFunction, Object value) {
        if (Boolean.TRUE.equals(predicate)) {
            return onGe(LambdaTools.getColumnName(lambdaFunction), value);
        }
        return new JoinOnSqlBuilder(Boolean.FALSE, precompileSql(), precompileArgs());
    }

    default <P> JoinOnSqlBuilder onGe(Boolean predicate, LambdaFunction<P, ?> lambdaFunction, Supplier<Object> value) {
        if (Boolean.TRUE.equals(predicate)) {
            return onGe(LambdaTools.getColumnName(lambdaFunction), value.get());
        }
        return new JoinOnSqlBuilder(Boolean.FALSE, precompileSql(), precompileArgs());
    }

    default JoinOnSqlBuilder onLt(String column, Object value) {
        return on(Boolean.TRUE, column, QueryRule.LT, value);
    }

    default JoinOnSqlBuilder onLt(Boolean predicate, String column, Object value) {
        return handleJoinOnSql(QueryRule.LT, predicate, column, () -> value);
    }

    default JoinOnSqlBuilder onLt(Boolean predicate, String column, Supplier<Object> value) {
        return handleJoinOnSql(QueryRule.LT, predicate, column, value);
    }

    default <P> JoinOnSqlBuilder onLt(LambdaFunction<P, ?> lambdaFunction, Object value) {
        return onLt(LambdaTools.getColumnName(lambdaFunction), value);
    }

    default <P> JoinOnSqlBuilder onLt(Boolean predicate, LambdaFunction<P, ?> lambdaFunction, Object value) {
        if (Boolean.TRUE.equals(predicate)) {
            return onLt(LambdaTools.getColumnName(lambdaFunction), value);
        }
        return new JoinOnSqlBuilder(Boolean.FALSE, precompileSql(), precompileArgs());
    }

    default <P> JoinOnSqlBuilder onLt(Boolean predicate, LambdaFunction<P, ?> lambdaFunction, Supplier<Object> value) {
        if (Boolean.TRUE.equals(predicate)) {
            return onLt(LambdaTools.getColumnName(lambdaFunction), value.get());
        }
        return new JoinOnSqlBuilder(Boolean.FALSE, precompileSql(), precompileArgs());
    }

    default JoinOnSqlBuilder onLe(String column, Object value) {
        return on(Boolean.TRUE, column, QueryRule.LE, value);
    }

    default JoinOnSqlBuilder onLe(Boolean predicate, String column, Object value) {
        return handleJoinOnSql(QueryRule.LE, predicate, column, () -> value);
    }

    default JoinOnSqlBuilder onLe(Boolean predicate, String column, Supplier<Object> value) {
        return handleJoinOnSql(QueryRule.LE, predicate, column, value);
    }

    default <P> JoinOnSqlBuilder onLe(LambdaFunction<P, ?> lambdaFunction, Object value) {
        return onLe(LambdaTools.getColumnName(lambdaFunction), value);
    }

    default <P> JoinOnSqlBuilder onLe(Boolean predicate, LambdaFunction<P, ?> lambdaFunction, Object value) {
        if (Boolean.TRUE.equals(predicate)) {
            return onLe(LambdaTools.getColumnName(lambdaFunction), value);
        }
        return new JoinOnSqlBuilder(Boolean.FALSE, precompileSql(), precompileArgs());
    }

    default <P> JoinOnSqlBuilder onLe(Boolean predicate, LambdaFunction<P, ?> lambdaFunction, Supplier<Object> value) {
        if (Boolean.TRUE.equals(predicate)) {
            return onLe(LambdaTools.getColumnName(lambdaFunction), value.get());
        }
        return new JoinOnSqlBuilder(Boolean.FALSE, precompileSql(), precompileArgs());
    }

    default JoinOnSqlBuilder onNe(String column, Object value) {
        return on(Boolean.TRUE, column, QueryRule.NE, value);
    }

    default JoinOnSqlBuilder onNe(Boolean predicate, String column, Object value) {
        return handleJoinOnSql(QueryRule.NE, predicate, column, () -> value);
    }

    default JoinOnSqlBuilder onNe(Boolean predicate, String column, Supplier<Object> value) {
        return handleJoinOnSql(QueryRule.NE, predicate, column, value);
    }

    default <P> JoinOnSqlBuilder onNe(LambdaFunction<P, ?> lambdaFunction, Object value) {
        return onNe(LambdaTools.getColumnName(lambdaFunction), value);
    }

    default <P> JoinOnSqlBuilder onNe(Boolean predicate, LambdaFunction<P, ?> lambdaFunction, Object value) {
        if (Boolean.TRUE.equals(predicate)) {
            return onNe(LambdaTools.getColumnName(lambdaFunction), value);
        }
        return new JoinOnSqlBuilder(Boolean.FALSE, precompileSql(), precompileArgs());
    }

    default <P> JoinOnSqlBuilder onNe(Boolean predicate, LambdaFunction<P, ?> lambdaFunction, Supplier<Object> value) {
        if (Boolean.TRUE.equals(predicate)) {
            return onNe(LambdaTools.getColumnName(lambdaFunction), value.get());
        }
        return new JoinOnSqlBuilder(Boolean.FALSE, precompileSql(), precompileArgs());
    }

    default JoinOnSqlBuilder onLike(String column, Object value) {
        return on(Boolean.TRUE, column, QueryRule.LIKE, value);
    }

    default JoinOnSqlBuilder onLike(Boolean predicate, String column, Object value) {
        return handleJoinOnSql(QueryRule.LIKE, predicate, column, () -> value);
    }

    default JoinOnSqlBuilder onLike(Boolean predicate, String column, Supplier<Object> value) {
        return handleJoinOnSql(QueryRule.LIKE, predicate, column, value);
    }

    default <P> JoinOnSqlBuilder onLike(LambdaFunction<P, ?> lambdaFunction, Object value) {
        return onLike(LambdaTools.getColumnName(lambdaFunction), value);
    }

    default <P> JoinOnSqlBuilder onLike(Boolean predicate, LambdaFunction<P, ?> lambdaFunction, Object value) {
        if (Boolean.TRUE.equals(predicate)) {
            return onLike(LambdaTools.getColumnName(lambdaFunction), value);
        }
        return new JoinOnSqlBuilder(Boolean.FALSE, precompileSql(), precompileArgs());
    }

    default <P> JoinOnSqlBuilder onLike(Boolean predicate, LambdaFunction<P, ?> lambdaFunction, Supplier<Object> value) {
        if (Boolean.TRUE.equals(predicate)) {
            return onLike(LambdaTools.getColumnName(lambdaFunction), value.get());
        }
        return new JoinOnSqlBuilder(Boolean.FALSE, precompileSql(), precompileArgs());
    }

    default JoinOnSqlBuilder onNotLike(String column, Object value) {
        return on(Boolean.TRUE, column, QueryRule.NOT_LIKE, value);
    }

    default JoinOnSqlBuilder onNotLike(Boolean predicate, String column, Object value) {
        return handleJoinOnSql(QueryRule.NOT_LIKE, predicate, column, () -> value);
    }

    default JoinOnSqlBuilder onNotLike(Boolean predicate, String column, Supplier<Object> value) {
        return handleJoinOnSql(QueryRule.NOT_LIKE, predicate, column, value);
    }

    default <P> JoinOnSqlBuilder onNotLike(LambdaFunction<P, ?> lambdaFunction, Object value) {
        return onNotLike(LambdaTools.getColumnName(lambdaFunction), value);
    }

    default <P> JoinOnSqlBuilder onNotLike(Boolean predicate, LambdaFunction<P, ?> lambdaFunction, Object value) {
        if (Boolean.TRUE.equals(predicate)) {
            return onNotLike(LambdaTools.getColumnName(lambdaFunction), value);
        }
        return new JoinOnSqlBuilder(Boolean.FALSE, precompileSql(), precompileArgs());
    }

    default <P> JoinOnSqlBuilder onNotLike(Boolean predicate, LambdaFunction<P, ?> lambdaFunction, Supplier<Object> value) {
        if (Boolean.TRUE.equals(predicate)) {
            return onNotLike(LambdaTools.getColumnName(lambdaFunction), value.get());
        }
        return new JoinOnSqlBuilder(Boolean.FALSE, precompileSql(), precompileArgs());
    }

    default JoinOnSqlBuilder onLLike(String column, Object value) {
        return on(Boolean.TRUE, column, QueryRule.LEFT_LIKE, value);
    }

    default JoinOnSqlBuilder onLLike(Boolean predicate, String column, Object value) {
        return handleJoinOnSql(QueryRule.LEFT_LIKE, predicate, column, () -> value);
    }

    default JoinOnSqlBuilder onLLike(Boolean predicate, String column, Supplier<Object> value) {
        return handleJoinOnSql(QueryRule.LEFT_LIKE, predicate, column, value);
    }

    default <P> JoinOnSqlBuilder onLLike(LambdaFunction<P, ?> lambdaFunction, Object value) {
        return onLLike(LambdaTools.getColumnName(lambdaFunction), value);
    }

    default <P> JoinOnSqlBuilder onLLike(Boolean predicate, LambdaFunction<P, ?> lambdaFunction, Object value) {
        if (Boolean.TRUE.equals(predicate)) {
            return onLLike(LambdaTools.getColumnName(lambdaFunction), value);
        }
        return new JoinOnSqlBuilder(Boolean.FALSE, precompileSql(), precompileArgs());
    }

    default <P> JoinOnSqlBuilder onLLike(Boolean predicate, LambdaFunction<P, ?> lambdaFunction, Supplier<Object> value) {
        if (Boolean.TRUE.equals(predicate)) {
            return onLLike(LambdaTools.getColumnName(lambdaFunction), value.get());
        }
        return new JoinOnSqlBuilder(Boolean.FALSE, precompileSql(), precompileArgs());
    }

    default JoinOnSqlBuilder onNotLLike(String column, Object value) {
        return on(Boolean.TRUE, column, QueryRule.NOT_LEFT_LIKE, value);
    }

    default JoinOnSqlBuilder onNotLLike(Boolean predicate, String column, Object value) {
        return handleJoinOnSql(QueryRule.NOT_LEFT_LIKE, predicate, column, () -> value);
    }

    default JoinOnSqlBuilder onNotLLike(Boolean predicate, String column, Supplier<Object> value) {
        return handleJoinOnSql(QueryRule.NOT_LEFT_LIKE, predicate, column, value);
    }

    default <P> JoinOnSqlBuilder onNotLLike(LambdaFunction<P, ?> lambdaFunction, Object value) {
        return onNotLLike(LambdaTools.getColumnName(lambdaFunction), value);
    }

    default <P> JoinOnSqlBuilder onNotLLike(Boolean predicate, LambdaFunction<P, ?> lambdaFunction, Object value) {
        if (Boolean.TRUE.equals(predicate)) {
            return onNotLLike(LambdaTools.getColumnName(lambdaFunction), value);
        }
        return new JoinOnSqlBuilder(Boolean.FALSE, precompileSql(), precompileArgs());
    }

    default <P> JoinOnSqlBuilder onNotLLike(Boolean predicate, LambdaFunction<P, ?> lambdaFunction, Supplier<Object> value) {
        if (Boolean.TRUE.equals(predicate)) {
            return onNotLLike(LambdaTools.getColumnName(lambdaFunction), value.get());
        }
        return new JoinOnSqlBuilder(Boolean.FALSE, precompileSql(), precompileArgs());
    }

    default JoinOnSqlBuilder onRLike(String column, Object value) {
        return on(Boolean.TRUE, column, QueryRule.RIGHT_LIKE, value);
    }

    default JoinOnSqlBuilder onRLike(Boolean predicate, String column, Object value) {
        return handleJoinOnSql(QueryRule.RIGHT_LIKE, predicate, column, () -> value);
    }

    default JoinOnSqlBuilder onRLike(Boolean predicate, String column, Supplier<Object> value) {
        return handleJoinOnSql(QueryRule.RIGHT_LIKE, predicate, column, value);
    }

    default <P> JoinOnSqlBuilder onRLike(LambdaFunction<P, ?> lambdaFunction, Object value) {
        return onRLike(LambdaTools.getColumnName(lambdaFunction), value);
    }

    default <P> JoinOnSqlBuilder onRLike(Boolean predicate, LambdaFunction<P, ?> lambdaFunction, Object value) {
        if (Boolean.TRUE.equals(predicate)) {
            return onRLike(LambdaTools.getColumnName(lambdaFunction), value);
        }
        return new JoinOnSqlBuilder(Boolean.FALSE, precompileSql(), precompileArgs());
    }

    default <P> JoinOnSqlBuilder onRLike(Boolean predicate, LambdaFunction<P, ?> lambdaFunction, Supplier<Object> value) {
        if (Boolean.TRUE.equals(predicate)) {
            return onRLike(LambdaTools.getColumnName(lambdaFunction), value.get());
        }
        return new JoinOnSqlBuilder(Boolean.FALSE, precompileSql(), precompileArgs());
    }

    default JoinOnSqlBuilder onNotRLike(String column, Object... values) {
        return on(Boolean.TRUE, column, QueryRule.NOT_RIGHT_LIKE, values);
    }

    default JoinOnSqlBuilder onNotRLike(Boolean predicate, String column, Object... values) {
        return handleJoinOnSql(QueryRule.NOT_RIGHT_LIKE, predicate, column, () -> values);
    }

    default JoinOnSqlBuilder onNotRLike(Boolean predicate, String column, Supplier<Object> values) {
        return handleJoinOnSql(QueryRule.NOT_RIGHT_LIKE, predicate, column, values);
    }

    default <P> JoinOnSqlBuilder onNotRLike(LambdaFunction<P, ?> lambdaFunction, Object value) {
        return onNotRLike(LambdaTools.getColumnName(lambdaFunction), value);
    }

    default <P> JoinOnSqlBuilder onNotRLike(Boolean predicate, LambdaFunction<P, ?> lambdaFunction, Object value) {
        if (Boolean.TRUE.equals(predicate)) {
            return onNotRLike(LambdaTools.getColumnName(lambdaFunction), value);
        }
        return new JoinOnSqlBuilder(Boolean.FALSE, precompileSql(), precompileArgs());
    }

    default <P> JoinOnSqlBuilder onNotRLike(Boolean predicate, LambdaFunction<P, ?> lambdaFunction, Supplier<Object> value) {
        if (Boolean.TRUE.equals(predicate)) {
            return onNotRLike(LambdaTools.getColumnName(lambdaFunction), value.get());
        }
        return new JoinOnSqlBuilder(Boolean.FALSE, precompileSql(), precompileArgs());
    }

    default JoinOnSqlBuilder onIn(String column, Object... values) {
        return on(Boolean.TRUE, column, QueryRule.IN, values);
    }

    default JoinOnSqlBuilder onIn(Boolean predicate, String column, Object... values) {
        return handleJoinOnSql(QueryRule.IN, predicate, column, () -> values);
    }

    default JoinOnSqlBuilder onIn(Boolean predicate, String column, Supplier<Object[]> values) {
        return handleJoinOnSql(QueryRule.IN, predicate, column, values);
    }

    default <P> JoinOnSqlBuilder onIn(LambdaFunction<P, ?> lambdaFunction, Object... values) {
        return onIn(LambdaTools.getColumnName(lambdaFunction), values);
    }

    default <P> JoinOnSqlBuilder onIn(Boolean predicate, LambdaFunction<P, ?> lambdaFunction, Object... value) {
        if (Boolean.TRUE.equals(predicate)) {
            return onIn(LambdaTools.getColumnName(lambdaFunction), value);
        }
        return new JoinOnSqlBuilder(Boolean.FALSE, precompileSql(), precompileArgs());
    }

    default <P> JoinOnSqlBuilder onIn(Boolean predicate, LambdaFunction<P, ?> lambdaFunction, Supplier<Object[]> value) {
        if (Boolean.TRUE.equals(predicate)) {
            return onIn(LambdaTools.getColumnName(lambdaFunction), value.get());
        }
        return new JoinOnSqlBuilder(Boolean.FALSE, precompileSql(), precompileArgs());
    }

    default JoinOnSqlBuilder onNotIn(String column, Object... values) {
        return on(Boolean.TRUE, column, QueryRule.NOT_IN, values);
    }

    default JoinOnSqlBuilder onNotIn(Boolean predicate, String column, Object... values) {
        return handleJoinOnSql(QueryRule.NOT_IN, predicate, column, () -> values);
    }

    default JoinOnSqlBuilder onNotIn(Boolean predicate, String column, Supplier<Object[]> values) {
        return handleJoinOnSql(QueryRule.NOT_IN, predicate, column, values);
    }

    default <P> JoinOnSqlBuilder onNotIn(LambdaFunction<P, ?> lambdaFunction, Object... values) {
        return onNotIn(LambdaTools.getColumnName(lambdaFunction), values);
    }

    default <P> JoinOnSqlBuilder onNotIn(Boolean predicate, LambdaFunction<P, ?> lambdaFunction, Object... value) {
        if (Boolean.TRUE.equals(predicate)) {
            return onNotIn(LambdaTools.getColumnName(lambdaFunction), value);
        }
        return new JoinOnSqlBuilder(Boolean.FALSE, precompileSql(), precompileArgs());
    }

    default <P> JoinOnSqlBuilder onNotIn(Boolean predicate, LambdaFunction<P, ?> lambdaFunction, Supplier<Object[]> value) {
        if (Boolean.TRUE.equals(predicate)) {
            return onNotIn(LambdaTools.getColumnName(lambdaFunction), value.get());
        }
        return new JoinOnSqlBuilder(Boolean.FALSE, precompileSql(), precompileArgs());
    }

    default JoinOnSqlBuilder onBetween(String column, Object... values) {
        return on(Boolean.TRUE, column, QueryRule.BETWEEN_AND, values);
    }

    default JoinOnSqlBuilder onBetween(Boolean predicate, String column, Object... values) {
        return handleJoinOnSql(QueryRule.BETWEEN_AND, predicate, column, () -> values);
    }

    default JoinOnSqlBuilder onBetween(Boolean predicate, String column, Supplier<Object> value1, Supplier<Object> value2) {
        if (Boolean.TRUE.equals(predicate)) {
            Tuple2<String, Object[]> pt = SqlTemplateTools.parsePrecompileCondition(SqlNameTools.handleName(column), QueryRule.BETWEEN_AND, value1.get(), value2.get());
            return new JoinOnSqlBuilder(Boolean.TRUE, precompileSql(), precompileArgs(), pt.t1, pt.t2);
        }
        return new JoinOnSqlBuilder(Boolean.FALSE, precompileSql(), precompileArgs());
    }

    default JoinOnSqlBuilder onBetween(Boolean predicate, String column, Supplier<Object[]> values) {
        return handleJoinOnSql(QueryRule.BETWEEN_AND, predicate, column, values);
    }

    default <P> JoinOnSqlBuilder onBetween(LambdaFunction<P, ?> lambdaFunction, Object... values) {
        return onBetween(LambdaTools.getColumnName(lambdaFunction), values);
    }

    default <P> JoinOnSqlBuilder onBetween(Boolean predicate, LambdaFunction<P, ?> lambdaFunction, Object... values) {
        if (Boolean.TRUE.equals(predicate)) {
            return onBetween(LambdaTools.getColumnName(lambdaFunction), values);
        }
        return new JoinOnSqlBuilder(Boolean.FALSE, precompileSql(), precompileArgs());
    }

    default <P> JoinOnSqlBuilder onBetween(Boolean predicate, LambdaFunction<P, ?> lambdaFunction, Supplier<Object> value1, Supplier<Object> value2) {
        if (Boolean.TRUE.equals(predicate)) {
            return onBetween(LambdaTools.getColumnName(lambdaFunction), value1.get(), value2.get());
        }
        return new JoinOnSqlBuilder(Boolean.FALSE, precompileSql(), precompileArgs());
    }

    default <P> JoinOnSqlBuilder onBetween(Boolean predicate, LambdaFunction<P, ?> lambdaFunction, Supplier<Object[]> value) {
        if (Boolean.TRUE.equals(predicate)) {
            return onBetween(LambdaTools.getColumnName(lambdaFunction), value.get());
        }
        return new JoinOnSqlBuilder(Boolean.FALSE, precompileSql(), precompileArgs());
    }

    default JoinOnSqlBuilder onNotBetween(String column, Object... values) {
        return on(Boolean.TRUE, column, QueryRule.NOT_BETWEEN_AND, values);
    }

    default JoinOnSqlBuilder onNotBetween(Boolean predicate, String column, Object... values) {
        return handleJoinOnSql(QueryRule.NOT_BETWEEN_AND, predicate, column, () -> values);
    }

    default JoinOnSqlBuilder onNotBetween(Boolean predicate, String column, Supplier<Object> value1, Supplier<Object> value2) {
        if (Boolean.TRUE.equals(predicate)) {
            Tuple2<String, Object[]> pt = SqlTemplateTools.parsePrecompileCondition(SqlNameTools.handleName(column), QueryRule.NOT_BETWEEN_AND, value1.get(), value2.get());
            return new JoinOnSqlBuilder(Boolean.TRUE, precompileSql(), precompileArgs(), pt.t1, pt.t2);
        }
        return new JoinOnSqlBuilder(Boolean.FALSE, precompileSql(), precompileArgs());
    }

    default JoinOnSqlBuilder onNotBetween(Boolean predicate, String column, Supplier<Object[]> values) {
        return handleJoinOnSql(QueryRule.NOT_BETWEEN_AND, predicate, column, values);
    }

    default <P> JoinOnSqlBuilder onNotBetween(LambdaFunction<P, ?> lambdaFunction, Object... values) {
        return onNotBetween(LambdaTools.getColumnName(lambdaFunction), values);
    }

    default <P> JoinOnSqlBuilder onNotBetween(Boolean predicate, LambdaFunction<P, ?> lambdaFunction, Object... values) {
        if (Boolean.TRUE.equals(predicate)) {
            return onNotBetween(LambdaTools.getColumnName(lambdaFunction), values);
        }
        return new JoinOnSqlBuilder(Boolean.FALSE, precompileSql(), precompileArgs());
    }

    default <P> JoinOnSqlBuilder onNotBetween(Boolean predicate, LambdaFunction<P, ?> lambdaFunction, Supplier<Object> value1, Supplier<Object> value2) {
        if (Boolean.TRUE.equals(predicate)) {
            return onNotBetween(LambdaTools.getColumnName(lambdaFunction), value1.get(), value2.get());
        }
        return new JoinOnSqlBuilder(Boolean.FALSE, precompileSql(), precompileArgs());
    }

    default <P> JoinOnSqlBuilder onNotBetween(Boolean predicate, LambdaFunction<P, ?> lambdaFunction, Supplier<Object[]> value) {
        if (Boolean.TRUE.equals(predicate)) {
            return onNotBetween(LambdaTools.getColumnName(lambdaFunction), value.get());
        }
        return new JoinOnSqlBuilder(Boolean.FALSE, precompileSql(), precompileArgs());
    }

    default JoinOnSqlBuilder onNull(String column) {
        return on(Boolean.TRUE, column, QueryRule.IS_NULL);
    }

    default <P> JoinOnSqlBuilder onNull(LambdaFunction<P, ?> lambdaFunction) {
        return onNull(LambdaTools.getColumnName(lambdaFunction));
    }

    default <P> JoinOnSqlBuilder onNull(Boolean predicate, LambdaFunction<P, ?> lambdaFunction) {
        if (Boolean.TRUE.equals(predicate)) {
            return onNull(LambdaTools.getColumnName(lambdaFunction));
        }
        return new JoinOnSqlBuilder(Boolean.FALSE, precompileSql(), precompileArgs());
    }

    default JoinOnSqlBuilder onNull(Boolean predicate, String column) {
        if (Boolean.TRUE.equals(predicate)) {
            return on(Boolean.TRUE, column, QueryRule.IS_NULL);
        }
        return new JoinOnSqlBuilder(Boolean.FALSE, precompileSql(), precompileArgs());
    }

    default JoinOnSqlBuilder onNotNull(String column) {
        return on(Boolean.TRUE, column, QueryRule.NOT_NULL);
    }

    default JoinOnSqlBuilder onNotNull(Boolean predicate, String column) {
        if (Boolean.TRUE.equals(predicate)) {
            return on(Boolean.TRUE, column, QueryRule.NOT_NULL);
        }
        return new JoinOnSqlBuilder(Boolean.FALSE, precompileSql(), precompileArgs());
    }

    default <P> JoinOnSqlBuilder onNotNull(LambdaFunction<P, ?> lambdaFunction) {
        return onNotNull(LambdaTools.getColumnName(lambdaFunction));
    }

    default <P> JoinOnSqlBuilder onNotNull(Boolean predicate, LambdaFunction<P, ?> lambdaFunction) {
        if (Boolean.TRUE.equals(predicate)) {
            return onNotNull(LambdaTools.getColumnName(lambdaFunction));
        }
        return new JoinOnSqlBuilder(Boolean.FALSE, precompileSql(), precompileArgs());
    }

    default JoinOnSqlBuilder onExists(Object... builder) {
        return on(Boolean.TRUE, CommonConstant.EMPTY_STRING, QueryRule.EXISTS, builder);
    }

    default JoinOnSqlBuilder onExists(Boolean predicate, Object... builder) {
        return handleJoinOnSql(QueryRule.EXISTS, predicate, null, () -> builder);
    }

    default JoinOnSqlBuilder onExists(Boolean predicate, Supplier<Object> builder) {
        return handleJoinOnSql(QueryRule.EXISTS, predicate, null, builder);
    }

    default JoinOnSqlBuilder onNotExists(Object... builder) {
        return on(Boolean.TRUE, CommonConstant.EMPTY_STRING, QueryRule.NOT_EXISTS, builder);
    }

    default JoinOnSqlBuilder onNotExists(Boolean predicate, Object... builder) {
        return handleJoinOnSql(QueryRule.NOT_EXISTS, predicate, null, () -> builder);
    }

    default JoinOnSqlBuilder onNotExists(Boolean predicate, Supplier<Object> builder) {
        return handleJoinOnSql(QueryRule.NOT_EXISTS, predicate, null, builder);
    }

    private <T> JoinOnSqlBuilder handleJoinOnSql(QueryRule queryRule, Boolean predicate, String column, Supplier<T> paramSupplier) {
        if (!Boolean.TRUE.equals(predicate)) {
            return new JoinOnSqlBuilder(Boolean.FALSE, precompileSql(), precompileArgs());
        }

        Tuple2<String, Object[]> pt = SqlTemplateTools.parsePrecompileCondition(column, queryRule, paramSupplier);
        return new JoinOnSqlBuilder(Boolean.TRUE, precompileSql(), precompileArgs(), pt.t1, pt.t2);
    }

}
