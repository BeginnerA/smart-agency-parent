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
 * HAVING SQL 构造器
 * </p>
 *
 * @author MC_Yang
 * @version V1.0
 **/
public interface HavingSqlBuilderRoute extends SqlBuilderRoute {

    default HavingSqlBuilder having(HavingSqlBuilder HavingSqlBuilder) {
        return having(Boolean.TRUE, HavingSqlBuilder);
    }

    default HavingSqlBuilder having(Boolean predicate, HavingSqlBuilder HavingSqlBuilder) {
        if (Boolean.TRUE.equals(predicate)) {
            return new HavingSqlBuilder(Boolean.TRUE, precompileSql(), precompileArgs(), HavingSqlBuilder);
        }
        return new HavingSqlBuilder(Boolean.FALSE, precompileSql(), precompileArgs());
    }

    default HavingSqlBuilder having(Boolean predicate, Supplier<HavingSqlBuilder> HavingSqlBuilder) {
        if (Boolean.TRUE.equals(predicate)) {
            return new HavingSqlBuilder(Boolean.TRUE, precompileSql(), precompileArgs(), HavingSqlBuilder.get());
        }
        return new HavingSqlBuilder(Boolean.FALSE, precompileSql(), precompileArgs());
    }

    default HavingSqlBuilder having(Object... queryCriteria) {
        return having(Boolean.TRUE, queryCriteria);
    }

    default HavingSqlBuilder having(Boolean predicate, Object... queryCriteria) {
        return new HavingSqlBuilder(predicate, precompileSql(), precompileArgs(), queryCriteria);
    }

    default HavingSqlBuilder having(String condition, Object... params) {
        return having(Boolean.TRUE, condition, params);
    }

    default HavingSqlBuilder having(Boolean predicate, String condition, Object... params) {
        return new HavingSqlBuilder(predicate, precompileSql(), precompileArgs(), condition, params);
    }

    default HavingSqlBuilder having(Boolean predicate, String condition, Supplier<Object[]> params) {
        return new HavingSqlBuilder(predicate, precompileSql(), precompileArgs(), condition, params);
    }

    default HavingSqlBuilder having(String column, QueryRule option, Object... values) {
        return having(Boolean.TRUE, column, option, values);
    }

    default HavingSqlBuilder having(Boolean predicate, String column, QueryRule option, Object... values) {
        return handleJoinOnSql(option, predicate, column, () -> values);
    }

    default HavingSqlBuilder having(Boolean predicate, String column, QueryRule option, Supplier<Object[]> values) {
        return handleJoinOnSql(option, predicate, column, values);
    }

    default <P> HavingSqlBuilder having(LambdaFunction<P, ?> lambdaFunction, QueryRule option, Object... values) {
        return having(LambdaTools.getColumnName(lambdaFunction), option, values);
    }

    default <P> HavingSqlBuilder having(Boolean predicate, LambdaFunction<P, ?> lambdaFunction, QueryRule option, Object... values) {
        if (Boolean.TRUE.equals(predicate)) {
            return having(LambdaTools.getColumnName(lambdaFunction), option, values);
        }
        return new HavingSqlBuilder(Boolean.FALSE, precompileSql(), precompileArgs());
    }

    default <P> HavingSqlBuilder having(Boolean predicate, LambdaFunction<P, ?> lambdaFunction, QueryRule option, Supplier<Object[]> values) {
        if (Boolean.TRUE.equals(predicate)) {
            return having(LambdaTools.getColumnName(lambdaFunction), option, values.get());
        }
        return new HavingSqlBuilder(Boolean.FALSE, precompileSql(), precompileArgs());
    }

    default HavingSqlBuilder havingEq(String column, Object value) {
        return having(Boolean.TRUE, column, QueryRule.EQ, value);
    }

    default HavingSqlBuilder havingEq(Boolean predicate, String column, Object value) {
        return handleJoinOnSql(QueryRule.EQ, predicate, column, () -> value);
    }

    default HavingSqlBuilder havingEq(Boolean predicate, String column, Supplier<Object> value) {
        return handleJoinOnSql(QueryRule.EQ, predicate, column, value);
    }

    default <P> HavingSqlBuilder havingEq(LambdaFunction<P, ?> lambdaFunction, Object value) {
        return havingEq(LambdaTools.getColumnName(lambdaFunction), value);
    }

    default <P> HavingSqlBuilder havingEq(Boolean predicate, LambdaFunction<P, ?> lambdaFunction, Object value) {
        if (Boolean.TRUE.equals(predicate)) {
            return havingEq(LambdaTools.getColumnName(lambdaFunction), value);
        }
        return new HavingSqlBuilder(Boolean.FALSE, precompileSql(), precompileArgs());
    }

    default <P> HavingSqlBuilder havingEq(Boolean predicate, LambdaFunction<P, ?> lambdaFunction, Supplier<Object> value) {
        if (Boolean.TRUE.equals(predicate)) {
            return havingEq(LambdaTools.getColumnName(lambdaFunction), value.get());
        }
        return new HavingSqlBuilder(Boolean.FALSE, precompileSql(), precompileArgs());
    }

    default HavingSqlBuilder havingGt(String column, Object value) {
        return having(Boolean.TRUE, column, QueryRule.GT, value);
    }

    default HavingSqlBuilder havingGt(Boolean predicate, String column, Object value) {
        return handleJoinOnSql(QueryRule.GT, predicate, column, () -> value);
    }

    default HavingSqlBuilder havingGt(Boolean predicate, String column, Supplier<Object> value) {
        return handleJoinOnSql(QueryRule.GT, predicate, column, value);
    }

    default <P> HavingSqlBuilder havingGt(LambdaFunction<P, ?> lambdaFunction, Object value) {
        return havingGt(LambdaTools.getColumnName(lambdaFunction), value);
    }

    default <P> HavingSqlBuilder havingGt(Boolean predicate, LambdaFunction<P, ?> lambdaFunction, Object value) {
        if (Boolean.TRUE.equals(predicate)) {
            return havingGt(LambdaTools.getColumnName(lambdaFunction), value);
        }
        return new HavingSqlBuilder(Boolean.FALSE, precompileSql(), precompileArgs());
    }

    default <P> HavingSqlBuilder havingGt(Boolean predicate, LambdaFunction<P, ?> lambdaFunction, Supplier<Object> value) {
        if (Boolean.TRUE.equals(predicate)) {
            return havingGt(LambdaTools.getColumnName(lambdaFunction), value.get());
        }
        return new HavingSqlBuilder(Boolean.FALSE, precompileSql(), precompileArgs());
    }

    default HavingSqlBuilder havingGe(String column, Object value) {
        return having(Boolean.TRUE, column, QueryRule.GE, value);
    }

    default HavingSqlBuilder havingGe(Boolean predicate, String column, Object value) {
        return handleJoinOnSql(QueryRule.GE, predicate, column, () -> value);
    }

    default HavingSqlBuilder havingGe(Boolean predicate, String column, Supplier<Object> value) {
        return handleJoinOnSql(QueryRule.GE, predicate, column, value);
    }

    default <P> HavingSqlBuilder havingGe(LambdaFunction<P, ?> lambdaFunction, Object value) {
        return havingGe(LambdaTools.getColumnName(lambdaFunction), value);
    }

    default <P> HavingSqlBuilder havingGe(Boolean predicate, LambdaFunction<P, ?> lambdaFunction, Object value) {
        if (Boolean.TRUE.equals(predicate)) {
            return havingGe(LambdaTools.getColumnName(lambdaFunction), value);
        }
        return new HavingSqlBuilder(Boolean.FALSE, precompileSql(), precompileArgs());
    }

    default <P> HavingSqlBuilder havingGe(Boolean predicate, LambdaFunction<P, ?> lambdaFunction, Supplier<Object> value) {
        if (Boolean.TRUE.equals(predicate)) {
            return havingGe(LambdaTools.getColumnName(lambdaFunction), value.get());
        }
        return new HavingSqlBuilder(Boolean.FALSE, precompileSql(), precompileArgs());
    }

    default HavingSqlBuilder havingLt(String column, Object value) {
        return having(Boolean.TRUE, column, QueryRule.LT, value);
    }

    default HavingSqlBuilder havingLt(Boolean predicate, String column, Object value) {
        return handleJoinOnSql(QueryRule.LT, predicate, column, () -> value);
    }

    default HavingSqlBuilder havingLt(Boolean predicate, String column, Supplier<Object> value) {
        return handleJoinOnSql(QueryRule.LT, predicate, column, value);
    }

    default <P> HavingSqlBuilder havingLt(LambdaFunction<P, ?> lambdaFunction, Object value) {
        return havingLt(LambdaTools.getColumnName(lambdaFunction), value);
    }

    default <P> HavingSqlBuilder havingLt(Boolean predicate, LambdaFunction<P, ?> lambdaFunction, Object value) {
        if (Boolean.TRUE.equals(predicate)) {
            return havingLt(LambdaTools.getColumnName(lambdaFunction), value);
        }
        return new HavingSqlBuilder(Boolean.FALSE, precompileSql(), precompileArgs());
    }

    default <P> HavingSqlBuilder havingLt(Boolean predicate, LambdaFunction<P, ?> lambdaFunction, Supplier<Object> value) {
        if (Boolean.TRUE.equals(predicate)) {
            return havingLt(LambdaTools.getColumnName(lambdaFunction), value.get());
        }
        return new HavingSqlBuilder(Boolean.FALSE, precompileSql(), precompileArgs());
    }

    default HavingSqlBuilder havingLe(String column, Object value) {
        return having(Boolean.TRUE, column, QueryRule.LE, value);
    }

    default HavingSqlBuilder havingLe(Boolean predicate, String column, Object value) {
        return handleJoinOnSql(QueryRule.LE, predicate, column, () -> value);
    }

    default HavingSqlBuilder havingLe(Boolean predicate, String column, Supplier<Object> value) {
        return handleJoinOnSql(QueryRule.LE, predicate, column, value);
    }

    default <P> HavingSqlBuilder havingLe(LambdaFunction<P, ?> lambdaFunction, Object value) {
        return havingLe(LambdaTools.getColumnName(lambdaFunction), value);
    }

    default <P> HavingSqlBuilder havingLe(Boolean predicate, LambdaFunction<P, ?> lambdaFunction, Object value) {
        if (Boolean.TRUE.equals(predicate)) {
            return havingLe(LambdaTools.getColumnName(lambdaFunction), value);
        }
        return new HavingSqlBuilder(Boolean.FALSE, precompileSql(), precompileArgs());
    }

    default <P> HavingSqlBuilder havingLe(Boolean predicate, LambdaFunction<P, ?> lambdaFunction, Supplier<Object> value) {
        if (Boolean.TRUE.equals(predicate)) {
            return havingLe(LambdaTools.getColumnName(lambdaFunction), value.get());
        }
        return new HavingSqlBuilder(Boolean.FALSE, precompileSql(), precompileArgs());
    }

    default HavingSqlBuilder havingNe(String column, Object value) {
        return having(Boolean.TRUE, column, QueryRule.NE, value);
    }

    default HavingSqlBuilder havingNe(Boolean predicate, String column, Object value) {
        return handleJoinOnSql(QueryRule.NE, predicate, column, () -> value);
    }

    default HavingSqlBuilder havingNe(Boolean predicate, String column, Supplier<Object> value) {
        return handleJoinOnSql(QueryRule.NE, predicate, column, value);
    }

    default <P> HavingSqlBuilder havingNe(LambdaFunction<P, ?> lambdaFunction, Object value) {
        return havingNe(LambdaTools.getColumnName(lambdaFunction), value);
    }

    default <P> HavingSqlBuilder havingNe(Boolean predicate, LambdaFunction<P, ?> lambdaFunction, Object value) {
        if (Boolean.TRUE.equals(predicate)) {
            return havingNe(LambdaTools.getColumnName(lambdaFunction), value);
        }
        return new HavingSqlBuilder(Boolean.FALSE, precompileSql(), precompileArgs());
    }

    default <P> HavingSqlBuilder havingNe(Boolean predicate, LambdaFunction<P, ?> lambdaFunction, Supplier<Object> value) {
        if (Boolean.TRUE.equals(predicate)) {
            return havingNe(LambdaTools.getColumnName(lambdaFunction), value.get());
        }
        return new HavingSqlBuilder(Boolean.FALSE, precompileSql(), precompileArgs());
    }

    default HavingSqlBuilder havingLike(String column, Object value) {
        return having(Boolean.TRUE, column, QueryRule.LIKE, value);
    }

    default HavingSqlBuilder havingLike(Boolean predicate, String column, Object value) {
        return handleJoinOnSql(QueryRule.NOT_LIKE, predicate, column, () -> value);
    }

    default HavingSqlBuilder havingLike(Boolean predicate, String column, Supplier<Object> value) {
        return handleJoinOnSql(QueryRule.LIKE, predicate, column, value);
    }

    default <P> HavingSqlBuilder havingLike(LambdaFunction<P, ?> lambdaFunction, Object value) {
        return havingLike(LambdaTools.getColumnName(lambdaFunction), value);
    }

    default <P> HavingSqlBuilder havingLike(Boolean predicate, LambdaFunction<P, ?> lambdaFunction, Object value) {
        if (Boolean.TRUE.equals(predicate)) {
            return havingLike(LambdaTools.getColumnName(lambdaFunction), value);
        }
        return new HavingSqlBuilder(Boolean.FALSE, precompileSql(), precompileArgs());
    }

    default <P> HavingSqlBuilder havingLike(Boolean predicate, LambdaFunction<P, ?> lambdaFunction, Supplier<Object> value) {
        if (Boolean.TRUE.equals(predicate)) {
            return havingLike(LambdaTools.getColumnName(lambdaFunction), value.get());
        }
        return new HavingSqlBuilder(Boolean.FALSE, precompileSql(), precompileArgs());
    }

    default HavingSqlBuilder havingNotLike(String column, Object value) {
        return having(Boolean.TRUE, column, QueryRule.NOT_LIKE, value);
    }

    default HavingSqlBuilder havingNotLike(Boolean predicate, String column, Object value) {
        return handleJoinOnSql(QueryRule.NOT_LIKE, predicate, column, () -> value);
    }

    default HavingSqlBuilder havingNotLike(Boolean predicate, String column, Supplier<Object> value) {
        return handleJoinOnSql(QueryRule.NOT_LIKE, predicate, column, value);
    }

    default <P> HavingSqlBuilder havingNotLike(LambdaFunction<P, ?> lambdaFunction, Object value) {
        return havingNotLike(LambdaTools.getColumnName(lambdaFunction), value);
    }

    default <P> HavingSqlBuilder havingNotLike(Boolean predicate, LambdaFunction<P, ?> lambdaFunction, Object value) {
        if (Boolean.TRUE.equals(predicate)) {
            return havingNotLike(LambdaTools.getColumnName(lambdaFunction), value);
        }
        return new HavingSqlBuilder(Boolean.FALSE, precompileSql(), precompileArgs());
    }

    default <P> HavingSqlBuilder havingNotLike(Boolean predicate, LambdaFunction<P, ?> lambdaFunction, Supplier<Object> value) {
        if (Boolean.TRUE.equals(predicate)) {
            return havingNotLike(LambdaTools.getColumnName(lambdaFunction), value.get());
        }
        return new HavingSqlBuilder(Boolean.FALSE, precompileSql(), precompileArgs());
    }

    default HavingSqlBuilder havingLLike(String column, Object value) {
        return having(Boolean.TRUE, column, QueryRule.LEFT_LIKE, value);
    }

    default HavingSqlBuilder havingLLike(Boolean predicate, String column, Object value) {
        return handleJoinOnSql(QueryRule.LEFT_LIKE, predicate, column, () -> value);
    }

    default HavingSqlBuilder havingLLike(Boolean predicate, String column, Supplier<Object> value) {
        return handleJoinOnSql(QueryRule.LEFT_LIKE, predicate, column, value);
    }

    default <P> HavingSqlBuilder havingLLike(LambdaFunction<P, ?> lambdaFunction, Object value) {
        return havingLLike(LambdaTools.getColumnName(lambdaFunction), value);
    }

    default <P> HavingSqlBuilder havingLLike(Boolean predicate, LambdaFunction<P, ?> lambdaFunction, Object value) {
        if (Boolean.TRUE.equals(predicate)) {
            return havingLLike(LambdaTools.getColumnName(lambdaFunction), value);
        }
        return new HavingSqlBuilder(Boolean.FALSE, precompileSql(), precompileArgs());
    }

    default <P> HavingSqlBuilder havingLLike(Boolean predicate, LambdaFunction<P, ?> lambdaFunction, Supplier<Object> value) {
        if (Boolean.TRUE.equals(predicate)) {
            return havingLLike(LambdaTools.getColumnName(lambdaFunction), value.get());
        }
        return new HavingSqlBuilder(Boolean.FALSE, precompileSql(), precompileArgs());
    }

    default HavingSqlBuilder havingNotLLike(String column, Object value) {
        return having(Boolean.TRUE, column, QueryRule.NOT_LEFT_LIKE, value);
    }

    default HavingSqlBuilder havingNotLLike(Boolean predicate, String column, Object value) {
        return handleJoinOnSql(QueryRule.NOT_LEFT_LIKE, predicate, column, () -> value);
    }

    default HavingSqlBuilder havingNotLLike(Boolean predicate, String column, Supplier<Object> value) {
        return handleJoinOnSql(QueryRule.NOT_LEFT_LIKE, predicate, column, value);
    }

    default <P> HavingSqlBuilder havingNotLLike(LambdaFunction<P, ?> lambdaFunction, Object value) {
        return havingNotLLike(LambdaTools.getColumnName(lambdaFunction), value);
    }

    default <P> HavingSqlBuilder havingNotLLike(Boolean predicate, LambdaFunction<P, ?> lambdaFunction, Object value) {
        if (Boolean.TRUE.equals(predicate)) {
            return havingNotLLike(LambdaTools.getColumnName(lambdaFunction), value);
        }
        return new HavingSqlBuilder(Boolean.FALSE, precompileSql(), precompileArgs());
    }

    default <P> HavingSqlBuilder havingNotLLike(Boolean predicate, LambdaFunction<P, ?> lambdaFunction, Supplier<Object> value) {
        if (Boolean.TRUE.equals(predicate)) {
            return havingNotLLike(LambdaTools.getColumnName(lambdaFunction), value.get());
        }
        return new HavingSqlBuilder(Boolean.FALSE, precompileSql(), precompileArgs());
    }

    default HavingSqlBuilder havingRLike(String column, Object value) {
        return having(Boolean.TRUE, column, QueryRule.RIGHT_LIKE, value);
    }

    default HavingSqlBuilder havingRLike(Boolean predicate, String column, Object value) {
        return handleJoinOnSql(QueryRule.RIGHT_LIKE, predicate, column, () -> value);
    }

    default HavingSqlBuilder havingRLike(Boolean predicate, String column, Supplier<Object> value) {
        return handleJoinOnSql(QueryRule.RIGHT_LIKE, predicate, column, value);
    }

    default <P> HavingSqlBuilder havingRLike(LambdaFunction<P, ?> lambdaFunction, Object value) {
        return havingRLike(LambdaTools.getColumnName(lambdaFunction), value);
    }

    default <P> HavingSqlBuilder havingRLike(Boolean predicate, LambdaFunction<P, ?> lambdaFunction, Object value) {
        if (Boolean.TRUE.equals(predicate)) {
            return havingRLike(LambdaTools.getColumnName(lambdaFunction), value);
        }
        return new HavingSqlBuilder(Boolean.FALSE, precompileSql(), precompileArgs());
    }

    default <P> HavingSqlBuilder havingRLike(Boolean predicate, LambdaFunction<P, ?> lambdaFunction, Supplier<Object> value) {
        if (Boolean.TRUE.equals(predicate)) {
            return havingRLike(LambdaTools.getColumnName(lambdaFunction), value.get());
        }
        return new HavingSqlBuilder(Boolean.FALSE, precompileSql(), precompileArgs());
    }

    default HavingSqlBuilder havingNotRLike(String column, Object... values) {
        return having(Boolean.TRUE, column, QueryRule.NOT_RIGHT_LIKE, values);
    }

    default HavingSqlBuilder havingNotRLike(Boolean predicate, String column, Object... values) {
        return handleJoinOnSql(QueryRule.NOT_RIGHT_LIKE, predicate, column, () -> values);
    }

    default HavingSqlBuilder havingNotRLike(Boolean predicate, String column, Supplier<Object> values) {
        return handleJoinOnSql(QueryRule.NOT_RIGHT_LIKE, predicate, column, values);
    }

    default <P> HavingSqlBuilder havingNotRLike(LambdaFunction<P, ?> lambdaFunction, Object value) {
        return havingNotRLike(LambdaTools.getColumnName(lambdaFunction), value);
    }

    default <P> HavingSqlBuilder havingNotRLike(Boolean predicate, LambdaFunction<P, ?> lambdaFunction, Object value) {
        if (Boolean.TRUE.equals(predicate)) {
            return havingNotRLike(LambdaTools.getColumnName(lambdaFunction), value);
        }
        return new HavingSqlBuilder(Boolean.FALSE, precompileSql(), precompileArgs());
    }

    default <P> HavingSqlBuilder havingNotRLike(Boolean predicate, LambdaFunction<P, ?> lambdaFunction, Supplier<Object> value) {
        if (Boolean.TRUE.equals(predicate)) {
            return havingNotRLike(LambdaTools.getColumnName(lambdaFunction), value.get());
        }
        return new HavingSqlBuilder(Boolean.FALSE, precompileSql(), precompileArgs());
    }

    default HavingSqlBuilder havingIn(String column, Object... values) {
        return having(Boolean.TRUE, column, QueryRule.IN, values);
    }

    default HavingSqlBuilder havingIn(Boolean predicate, String column, Object... values) {
        return handleJoinOnSql(QueryRule.NOT_IN, predicate, column, () -> values);
    }

    default HavingSqlBuilder havingIn(Boolean predicate, String column, Supplier<Object[]> values) {
        return handleJoinOnSql(QueryRule.IN, predicate, column, values);
    }

    default <P> HavingSqlBuilder havingIn(LambdaFunction<P, ?> lambdaFunction, Object... values) {
        return havingIn(LambdaTools.getColumnName(lambdaFunction), values);
    }

    default <P> HavingSqlBuilder havingIn(Boolean predicate, LambdaFunction<P, ?> lambdaFunction, Object... value) {
        if (Boolean.TRUE.equals(predicate)) {
            return havingIn(LambdaTools.getColumnName(lambdaFunction), value);
        }
        return new HavingSqlBuilder(Boolean.FALSE, precompileSql(), precompileArgs());
    }

    default <P> HavingSqlBuilder havingIn(Boolean predicate, LambdaFunction<P, ?> lambdaFunction, Supplier<Object[]> value) {
        if (Boolean.TRUE.equals(predicate)) {
            return havingIn(LambdaTools.getColumnName(lambdaFunction), value.get());
        }
        return new HavingSqlBuilder(Boolean.FALSE, precompileSql(), precompileArgs());
    }

    default HavingSqlBuilder havingNotIn(String column, Object... values) {
        return having(Boolean.TRUE, column, QueryRule.NOT_IN, values);
    }

    default HavingSqlBuilder havingNotIn(Boolean predicate, String column, Object... values) {
        return handleJoinOnSql(QueryRule.NOT_IN, predicate, column, () -> values);
    }

    default HavingSqlBuilder havingNotIn(Boolean predicate, String column, Supplier<Object[]> values) {
        return handleJoinOnSql(QueryRule.NOT_IN, predicate, column, values);
    }

    default <P> HavingSqlBuilder havingNotIn(LambdaFunction<P, ?> lambdaFunction, Object... values) {
        return havingNotIn(LambdaTools.getColumnName(lambdaFunction), values);
    }

    default <P> HavingSqlBuilder havingNotIn(Boolean predicate, LambdaFunction<P, ?> lambdaFunction, Object... value) {
        if (Boolean.TRUE.equals(predicate)) {
            return havingNotIn(LambdaTools.getColumnName(lambdaFunction), value);
        }
        return new HavingSqlBuilder(Boolean.FALSE, precompileSql(), precompileArgs());
    }

    default <P> HavingSqlBuilder havingNotIn(Boolean predicate, LambdaFunction<P, ?> lambdaFunction, Supplier<Object[]> value) {
        if (Boolean.TRUE.equals(predicate)) {
            return havingNotIn(LambdaTools.getColumnName(lambdaFunction), value.get());
        }
        return new HavingSqlBuilder(Boolean.FALSE, precompileSql(), precompileArgs());
    }

    default HavingSqlBuilder havingBetween(String column, Object... values) {
        return having(Boolean.TRUE, column, QueryRule.BETWEEN_AND, values);
    }

    default HavingSqlBuilder havingBetween(Boolean predicate, String column, Object... values) {
        return handleJoinOnSql(QueryRule.BETWEEN_AND, predicate, column, () -> values);
    }

    default HavingSqlBuilder havingBetween(Boolean predicate, String column, Supplier<Object> value1, Supplier<Object> value2) {
        if (Boolean.TRUE.equals(predicate)) {
            Tuple2<String, Object[]> pt = SqlTemplateTools.parsePrecompileCondition(SqlNameTools.handleName(column), QueryRule.BETWEEN_AND, value1.get(), value2.get());
            return new HavingSqlBuilder(Boolean.TRUE, precompileSql(), precompileArgs(), pt.t1, pt.t2);
        }
        return new HavingSqlBuilder(Boolean.FALSE, precompileSql(), precompileArgs());
    }

    default HavingSqlBuilder havingBetween(Boolean predicate, String column, Supplier<Object[]> values) {
        return handleJoinOnSql(QueryRule.BETWEEN_AND, predicate, column, values);
    }

    default <P> HavingSqlBuilder havingBetween(LambdaFunction<P, ?> lambdaFunction, Object... values) {
        return havingBetween(LambdaTools.getColumnName(lambdaFunction), values);
    }

    default <P> HavingSqlBuilder havingBetween(Boolean predicate, LambdaFunction<P, ?> lambdaFunction, Object... values) {
        if (Boolean.TRUE.equals(predicate)) {
            return havingBetween(LambdaTools.getColumnName(lambdaFunction), values);
        }
        return new HavingSqlBuilder(Boolean.FALSE, precompileSql(), precompileArgs());
    }

    default <P> HavingSqlBuilder havingBetween(Boolean predicate, LambdaFunction<P, ?> lambdaFunction, Supplier<Object> value1, Supplier<Object> value2) {
        if (Boolean.TRUE.equals(predicate)) {
            return havingBetween(LambdaTools.getColumnName(lambdaFunction), value1.get(), value2.get());
        }
        return new HavingSqlBuilder(Boolean.FALSE, precompileSql(), precompileArgs());
    }

    default <P> HavingSqlBuilder havingBetween(Boolean predicate, LambdaFunction<P, ?> lambdaFunction, Supplier<Object[]> value) {
        if (Boolean.TRUE.equals(predicate)) {
            return havingBetween(LambdaTools.getColumnName(lambdaFunction), value.get());
        }
        return new HavingSqlBuilder(Boolean.FALSE, precompileSql(), precompileArgs());
    }

    default HavingSqlBuilder havingNotBetween(String column, Object... values) {
        return having(Boolean.TRUE, column, QueryRule.NOT_BETWEEN_AND, values);
    }

    default HavingSqlBuilder havingNotBetween(Boolean predicate, String column, Object... values) {
        return handleJoinOnSql(QueryRule.NOT_BETWEEN_AND, predicate, column, () -> values);
    }

    default HavingSqlBuilder havingNotBetween(Boolean predicate, String column, Supplier<Object> value1, Supplier<Object> value2) {
        if (Boolean.TRUE.equals(predicate)) {
            Tuple2<String, Object[]> pt = SqlTemplateTools.parsePrecompileCondition(SqlNameTools.handleName(column), QueryRule.NOT_BETWEEN_AND, value1.get(), value2.get());
            return new HavingSqlBuilder(Boolean.TRUE, precompileSql(), precompileArgs(), pt.t1, pt.t2);
        }
        return new HavingSqlBuilder(Boolean.FALSE, precompileSql(), precompileArgs());
    }

    default HavingSqlBuilder havingNotBetween(Boolean predicate, String column, Supplier<Object[]> values) {
        return handleJoinOnSql(QueryRule.NOT_BETWEEN_AND, predicate, column, values);
    }

    default <P> HavingSqlBuilder havingNotBetween(LambdaFunction<P, ?> lambdaFunction, Object... values) {
        return havingNotBetween(LambdaTools.getColumnName(lambdaFunction), values);
    }

    default <P> HavingSqlBuilder havingNotBetween(Boolean predicate, LambdaFunction<P, ?> lambdaFunction, Object... values) {
        if (Boolean.TRUE.equals(predicate)) {
            return havingNotBetween(LambdaTools.getColumnName(lambdaFunction), values);
        }
        return new HavingSqlBuilder(Boolean.FALSE, precompileSql(), precompileArgs());
    }

    default <P> HavingSqlBuilder havingNotBetween(Boolean predicate, LambdaFunction<P, ?> lambdaFunction, Supplier<Object> value1, Supplier<Object> value2) {
        if (Boolean.TRUE.equals(predicate)) {
            return havingNotBetween(LambdaTools.getColumnName(lambdaFunction), value1.get(), value2.get());
        }
        return new HavingSqlBuilder(Boolean.FALSE, precompileSql(), precompileArgs());
    }

    default <P> HavingSqlBuilder havingNotBetween(Boolean predicate, LambdaFunction<P, ?> lambdaFunction, Supplier<Object[]> value) {
        if (Boolean.TRUE.equals(predicate)) {
            return havingNotBetween(LambdaTools.getColumnName(lambdaFunction), value.get());
        }
        return new HavingSqlBuilder(Boolean.FALSE, precompileSql(), precompileArgs());
    }

    default HavingSqlBuilder havingNull(String column) {
        return having(Boolean.TRUE, column, QueryRule.IS_NULL);
    }

    default <P> HavingSqlBuilder havingNull(LambdaFunction<P, ?> lambdaFunction) {
        return havingNull(LambdaTools.getColumnName(lambdaFunction));
    }

    default <P> HavingSqlBuilder havingNull(Boolean predicate, LambdaFunction<P, ?> lambdaFunction) {
        if (Boolean.TRUE.equals(predicate)) {
            return havingNull(LambdaTools.getColumnName(lambdaFunction));
        }
        return new HavingSqlBuilder(Boolean.FALSE, precompileSql(), precompileArgs());
    }

    default HavingSqlBuilder havingNull(Boolean predicate, String column) {
        if (Boolean.TRUE.equals(predicate)) {
            return having(Boolean.TRUE, column, QueryRule.IS_NULL);
        }
        return new HavingSqlBuilder(Boolean.FALSE, precompileSql(), precompileArgs());
    }

    default HavingSqlBuilder havingNotNull(String column) {
        return having(Boolean.TRUE, column, QueryRule.NOT_NULL);
    }

    default HavingSqlBuilder havingNotNull(Boolean predicate, String column) {
        if (Boolean.TRUE.equals(predicate)) {
            return having(Boolean.TRUE, column, QueryRule.NOT_NULL);
        }
        return new HavingSqlBuilder(Boolean.FALSE, precompileSql(), precompileArgs());
    }

    default <P> HavingSqlBuilder havingNotNull(LambdaFunction<P, ?> lambdaFunction) {
        return havingNotNull(LambdaTools.getColumnName(lambdaFunction));
    }

    default <P> HavingSqlBuilder havingNotNull(Boolean predicate, LambdaFunction<P, ?> lambdaFunction) {
        if (Boolean.TRUE.equals(predicate)) {
            return havingNotNull(LambdaTools.getColumnName(lambdaFunction));
        }
        return new HavingSqlBuilder(Boolean.FALSE, precompileSql(), precompileArgs());
    }

    default HavingSqlBuilder havingExists(Object... builder) {
        return having(Boolean.TRUE, CommonConstant.EMPTY_STRING, QueryRule.EXISTS, builder);
    }

    default HavingSqlBuilder havingExists(Boolean predicate, Object... builder) {
        return handleJoinOnSql(QueryRule.EXISTS, predicate, null, () -> builder);
    }

    default HavingSqlBuilder havingExists(Boolean predicate, Supplier<Object> builder) {
        return handleJoinOnSql(QueryRule.EXISTS, predicate, null, builder);
    }

    default HavingSqlBuilder havingNotExists(Object... builder) {
        return having(Boolean.TRUE, CommonConstant.EMPTY_STRING, QueryRule.NOT_EXISTS, builder);
    }

    default HavingSqlBuilder havingNotExists(Boolean predicate, Object... builder) {
        return handleJoinOnSql(QueryRule.NOT_EXISTS, predicate, null, () -> builder);
    }

    default HavingSqlBuilder havingNotExists(Boolean predicate, Supplier<Object> builder) {
        return handleJoinOnSql(QueryRule.NOT_EXISTS, predicate, null, builder);
    }

    private <T> HavingSqlBuilder handleJoinOnSql(QueryRule queryRule, Boolean predicate, String column, Supplier<T> paramSupplier) {
        if (!Boolean.TRUE.equals(predicate)) {
            return new HavingSqlBuilder(Boolean.FALSE, precompileSql(), precompileArgs());
        }

        Tuple2<String, Object[]> pt = SqlTemplateTools.parsePrecompileCondition(column, queryRule, paramSupplier);
        return new HavingSqlBuilder(Boolean.TRUE, precompileSql(), precompileArgs(), pt.t1, pt.t2);
    }

}
