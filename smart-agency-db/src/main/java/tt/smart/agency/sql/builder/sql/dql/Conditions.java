package tt.smart.agency.sql.builder.sql.dql;

import tt.smart.agency.sql.builder.sql.LambdaFunction;
import tt.smart.agency.sql.builder.sql.Tuple2;
import tt.smart.agency.sql.constant.CommonConstant;
import tt.smart.agency.sql.constant.QueryRule;
import tt.smart.agency.sql.tools.LambdaTools;
import tt.smart.agency.sql.tools.SqlNameTools;
import tt.smart.agency.sql.tools.SqlTemplateTools;

import java.util.function.Supplier;

/**
 * <p>
 * 条件
 * </p>
 *
 * @author MC_Yang
 * @version V1.0
 **/
public class Conditions {

    private Conditions() {
    }

    public static WhereSqlBuilder where(Object... queryCriteria) {
        return where(Boolean.TRUE, queryCriteria);
    }

    public static WhereSqlBuilder where(Boolean predicate, Object... queryCriteria) {
        return new WhereSqlBuilder(predicate, null, queryCriteria);
    }

    public static WhereSqlBuilder where(String condition, Object... params) {
        return where(Boolean.TRUE, condition, params);
    }

    public static WhereSqlBuilder where(Boolean predicate, String condition, Object... params) {
        return new WhereSqlBuilder(predicate, null, CommonConstant.EMPTY_OBJECT_ARRAY, condition, params);
    }

    public static WhereSqlBuilder where(Boolean predicate, String condition, Supplier<Object[]> params) {
        return new WhereSqlBuilder(predicate, null, CommonConstant.EMPTY_OBJECT_ARRAY, condition, params);
    }

    public static WhereSqlBuilder where(String column, QueryRule option, Object... params) {
        return where(Boolean.TRUE, column, option, params);
    }

    public static WhereSqlBuilder where(Boolean predicate, String column, QueryRule option, Object... params) {
        if (predicate) {
            Tuple2<String, Object[]> pt = SqlTemplateTools.parsePrecompileCondition(SqlNameTools.handleName(column), option, params);
            return new WhereSqlBuilder(Boolean.TRUE, null, CommonConstant.EMPTY_OBJECT_ARRAY, pt.t1, pt.t2);
        }
        return new WhereSqlBuilder(Boolean.FALSE, null, CommonConstant.EMPTY_OBJECT_ARRAY);
    }

    public static WhereSqlBuilder where(Boolean predicate, String column, QueryRule option, Supplier<Object[]> params) {
        if (predicate) {
            Tuple2<String, Object[]> pt = SqlTemplateTools.parsePrecompileCondition(SqlNameTools.handleName(column), option, params.get());
            return new WhereSqlBuilder(Boolean.TRUE, null, CommonConstant.EMPTY_OBJECT_ARRAY, pt.t1, pt.t2);
        }
        return new WhereSqlBuilder(Boolean.FALSE, null, CommonConstant.EMPTY_OBJECT_ARRAY);
    }

    public static <P> WhereSqlBuilder where(LambdaFunction<P, ?> lambdaFunction, QueryRule option, Object... params) {
        return where(LambdaTools.getColumnName(lambdaFunction), option, params);
    }

    public static <P> WhereSqlBuilder where(Boolean predicate, LambdaFunction<P, ?> lambdaFunction, QueryRule option, Object... params) {
        if (Boolean.TRUE.equals(predicate)) {
            return where(LambdaTools.getColumnName(lambdaFunction), option, params);
        }
        return new WhereSqlBuilder(Boolean.FALSE, null, CommonConstant.EMPTY_OBJECT_ARRAY);
    }

    public static <P> WhereSqlBuilder where(Boolean predicate, LambdaFunction<P, ?> lambdaFunction, QueryRule option, Supplier<Object[]> params) {
        if (Boolean.TRUE.equals(predicate)) {
            return where(LambdaTools.getColumnName(lambdaFunction), option, params.get());
        }
        return new WhereSqlBuilder(Boolean.FALSE, null, CommonConstant.EMPTY_OBJECT_ARRAY);
    }

    public static WhereSqlBuilder whereEq(String column, Object param) {
        return where(Boolean.TRUE, column, QueryRule.EQ, param);
    }

    public static WhereSqlBuilder whereEq(Boolean predicate, String column, Object param) {
        if (predicate) {
            Tuple2<String, Object[]> pt = SqlTemplateTools.parsePrecompileCondition(SqlNameTools.handleName(column), QueryRule.EQ, param);
            return new WhereSqlBuilder(Boolean.TRUE, null, CommonConstant.EMPTY_OBJECT_ARRAY, pt.t1, pt.t2);
        }
        return new WhereSqlBuilder(Boolean.FALSE, null, CommonConstant.EMPTY_OBJECT_ARRAY);
    }

    public static WhereSqlBuilder whereEq(Boolean predicate, String column, Supplier<Object> param) {
        if (predicate) {
            Tuple2<String, Object[]> pt = SqlTemplateTools.parsePrecompileCondition(SqlNameTools.handleName(column), QueryRule.EQ, param.get());
            return new WhereSqlBuilder(Boolean.TRUE, null, CommonConstant.EMPTY_OBJECT_ARRAY, pt.t1, pt.t2);
        }
        return new WhereSqlBuilder(Boolean.FALSE, null, CommonConstant.EMPTY_OBJECT_ARRAY);
    }

    public static <P> WhereSqlBuilder whereEq(LambdaFunction<P, ?> lambdaFunction, Object param) {
        return whereEq(LambdaTools.getColumnName(lambdaFunction), param);
    }

    public static <P> WhereSqlBuilder whereEq(Boolean predicate, LambdaFunction<P, ?> lambdaFunction, Object param) {
        if (Boolean.TRUE.equals(predicate)) {
            return whereEq(LambdaTools.getColumnName(lambdaFunction), param);
        }
        return new WhereSqlBuilder(Boolean.FALSE, null, CommonConstant.EMPTY_OBJECT_ARRAY);
    }

    public static <P> WhereSqlBuilder whereEq(Boolean predicate, LambdaFunction<P, ?> lambdaFunction, Supplier<Object> param) {
        if (Boolean.TRUE.equals(predicate)) {
            return whereEq(LambdaTools.getColumnName(lambdaFunction), param.get());
        }
        return new WhereSqlBuilder(Boolean.FALSE, null, CommonConstant.EMPTY_OBJECT_ARRAY);
    }

    public static WhereSqlBuilder whereNe(String column, Object param) {
        return where(Boolean.TRUE, column, QueryRule.NE, param);
    }

    public static WhereSqlBuilder whereNe(Boolean predicate, String column, Object param) {
        if (predicate) {
            Tuple2<String, Object[]> pt = SqlTemplateTools.parsePrecompileCondition(SqlNameTools.handleName(column), QueryRule.NE, param);
            return new WhereSqlBuilder(Boolean.TRUE, null, CommonConstant.EMPTY_OBJECT_ARRAY, pt.t1, pt.t2);
        }
        return new WhereSqlBuilder(Boolean.FALSE, null, CommonConstant.EMPTY_OBJECT_ARRAY);
    }

    public static WhereSqlBuilder whereNe(Boolean predicate, String column, Supplier<Object> param) {
        if (predicate) {
            Tuple2<String, Object[]> pt = SqlTemplateTools.parsePrecompileCondition(SqlNameTools.handleName(column), QueryRule.NE, param.get());
            return new WhereSqlBuilder(Boolean.TRUE, null, CommonConstant.EMPTY_OBJECT_ARRAY, pt.t1, pt.t2);
        }
        return new WhereSqlBuilder(Boolean.FALSE, null, CommonConstant.EMPTY_OBJECT_ARRAY);
    }

    public static <P> WhereSqlBuilder whereNe(LambdaFunction<P, ?> lambdaFunction, Object param) {
        return whereNe(LambdaTools.getColumnName(lambdaFunction), param);
    }

    public static <P> WhereSqlBuilder whereNe(Boolean predicate, LambdaFunction<P, ?> lambdaFunction, Object param) {
        if (Boolean.TRUE.equals(predicate)) {
            return whereNe(LambdaTools.getColumnName(lambdaFunction), param);
        }
        return new WhereSqlBuilder(Boolean.FALSE, null, CommonConstant.EMPTY_OBJECT_ARRAY);
    }

    public static <P> WhereSqlBuilder whereNe(Boolean predicate, LambdaFunction<P, ?> lambdaFunction, Supplier<Object> param) {
        if (Boolean.TRUE.equals(predicate)) {
            return whereNe(LambdaTools.getColumnName(lambdaFunction), param.get());
        }
        return new WhereSqlBuilder(Boolean.FALSE, null, CommonConstant.EMPTY_OBJECT_ARRAY);
    }

    public static WhereSqlBuilder whereGt(String column, Object param) {
        return where(Boolean.TRUE, column, QueryRule.GT, param);
    }

    public static WhereSqlBuilder whereGt(Boolean predicate, String column, Object param) {
        if (predicate) {
            Tuple2<String, Object[]> pt = SqlTemplateTools.parsePrecompileCondition(SqlNameTools.handleName(column), QueryRule.GT, param);
            return new WhereSqlBuilder(Boolean.TRUE, null, CommonConstant.EMPTY_OBJECT_ARRAY, pt.t1, pt.t2);
        }
        return new WhereSqlBuilder(Boolean.FALSE, null, CommonConstant.EMPTY_OBJECT_ARRAY);
    }

    public static WhereSqlBuilder whereGt(Boolean predicate, String column, Supplier<Object> param) {
        if (predicate) {
            Tuple2<String, Object[]> pt = SqlTemplateTools.parsePrecompileCondition(SqlNameTools.handleName(column), QueryRule.GT, param.get());
            return new WhereSqlBuilder(Boolean.TRUE, null, CommonConstant.EMPTY_OBJECT_ARRAY, pt.t1, pt.t2);
        }
        return new WhereSqlBuilder(Boolean.FALSE, null, CommonConstant.EMPTY_OBJECT_ARRAY);
    }

    public static <P> WhereSqlBuilder whereGt(LambdaFunction<P, ?> lambdaFunction, Object param) {
        return whereGt(LambdaTools.getColumnName(lambdaFunction), param);
    }

    public static <P> WhereSqlBuilder whereGt(Boolean predicate, LambdaFunction<P, ?> lambdaFunction, Object param) {
        if (Boolean.TRUE.equals(predicate)) {
            return whereGt(LambdaTools.getColumnName(lambdaFunction), param);
        }
        return new WhereSqlBuilder(Boolean.FALSE, null, CommonConstant.EMPTY_OBJECT_ARRAY);
    }

    public static <P> WhereSqlBuilder whereGt(Boolean predicate, LambdaFunction<P, ?> lambdaFunction, Supplier<Object> param) {
        if (Boolean.TRUE.equals(predicate)) {
            return whereGt(LambdaTools.getColumnName(lambdaFunction), param.get());
        }
        return new WhereSqlBuilder(Boolean.FALSE, null, CommonConstant.EMPTY_OBJECT_ARRAY);
    }

    public static WhereSqlBuilder whereGe(String column, Object param) {
        return where(Boolean.TRUE, column, QueryRule.GE, param);
    }

    public static WhereSqlBuilder whereGe(Boolean predicate, String column, Object param) {
        if (predicate) {
            Tuple2<String, Object[]> pt = SqlTemplateTools.parsePrecompileCondition(SqlNameTools.handleName(column), QueryRule.GE, param);
            return new WhereSqlBuilder(Boolean.TRUE, null, CommonConstant.EMPTY_OBJECT_ARRAY, pt.t1, pt.t2);
        }
        return new WhereSqlBuilder(Boolean.FALSE, null, CommonConstant.EMPTY_OBJECT_ARRAY);
    }

    public static WhereSqlBuilder whereGe(Boolean predicate, String column, Supplier<Object> param) {
        if (predicate) {
            Tuple2<String, Object[]> pt = SqlTemplateTools.parsePrecompileCondition(SqlNameTools.handleName(column), QueryRule.GE, param.get());
            return new WhereSqlBuilder(Boolean.TRUE, null, CommonConstant.EMPTY_OBJECT_ARRAY, pt.t1, pt.t2);
        }
        return new WhereSqlBuilder(Boolean.FALSE, null, CommonConstant.EMPTY_OBJECT_ARRAY);
    }

    public static <P> WhereSqlBuilder whereGe(LambdaFunction<P, ?> lambdaFunction, Object param) {
        return whereGe(LambdaTools.getColumnName(lambdaFunction), param);
    }

    public static <P> WhereSqlBuilder whereGe(Boolean predicate, LambdaFunction<P, ?> lambdaFunction, Object param) {
        if (Boolean.TRUE.equals(predicate)) {
            return whereGe(LambdaTools.getColumnName(lambdaFunction), param);
        }
        return new WhereSqlBuilder(Boolean.FALSE, null, CommonConstant.EMPTY_OBJECT_ARRAY);
    }

    public static <P> WhereSqlBuilder whereGe(Boolean predicate, LambdaFunction<P, ?> lambdaFunction, Supplier<Object> param) {
        if (Boolean.TRUE.equals(predicate)) {
            return whereGe(LambdaTools.getColumnName(lambdaFunction), param.get());
        }
        return new WhereSqlBuilder(Boolean.FALSE, null, CommonConstant.EMPTY_OBJECT_ARRAY);
    }


    public static WhereSqlBuilder whereLt(String column, Object param) {
        return where(Boolean.TRUE, column, QueryRule.LT, param);
    }

    public static WhereSqlBuilder whereLt(Boolean predicate, String column, Object param) {
        if (predicate) {
            Tuple2<String, Object[]> pt = SqlTemplateTools.parsePrecompileCondition(SqlNameTools.handleName(column), QueryRule.LT, param);
            return new WhereSqlBuilder(Boolean.TRUE, null, CommonConstant.EMPTY_OBJECT_ARRAY, pt.t1, pt.t2);
        }
        return new WhereSqlBuilder(Boolean.FALSE, null, CommonConstant.EMPTY_OBJECT_ARRAY);
    }

    public static WhereSqlBuilder whereLt(Boolean predicate, String column, Supplier<Object> param) {
        if (predicate) {
            Tuple2<String, Object[]> pt = SqlTemplateTools.parsePrecompileCondition(SqlNameTools.handleName(column), QueryRule.LT, param.get());
            return new WhereSqlBuilder(Boolean.TRUE, null, CommonConstant.EMPTY_OBJECT_ARRAY, pt.t1, pt.t2);
        }
        return new WhereSqlBuilder(Boolean.FALSE, null, CommonConstant.EMPTY_OBJECT_ARRAY);
    }

    public static <P> WhereSqlBuilder whereLt(LambdaFunction<P, ?> lambdaFunction, Object param) {
        return whereLt(LambdaTools.getColumnName(lambdaFunction), param);
    }

    public static <P> WhereSqlBuilder whereLt(Boolean predicate, LambdaFunction<P, ?> lambdaFunction, Object param) {
        if (Boolean.TRUE.equals(predicate)) {
            return whereLt(LambdaTools.getColumnName(lambdaFunction), param);
        }
        return new WhereSqlBuilder(Boolean.FALSE, null, CommonConstant.EMPTY_OBJECT_ARRAY);
    }

    public static <P> WhereSqlBuilder whereLt(Boolean predicate, LambdaFunction<P, ?> lambdaFunction, Supplier<Object> param) {
        if (Boolean.TRUE.equals(predicate)) {
            return whereLt(LambdaTools.getColumnName(lambdaFunction), param.get());
        }
        return new WhereSqlBuilder(Boolean.FALSE, null, CommonConstant.EMPTY_OBJECT_ARRAY);
    }

    public static WhereSqlBuilder whereLe(String column, Object param) {
        return where(Boolean.TRUE, column, QueryRule.LE, param);
    }

    public static WhereSqlBuilder whereLe(Boolean predicate, String column, Object param) {
        if (predicate) {
            Tuple2<String, Object[]> pt = SqlTemplateTools.parsePrecompileCondition(SqlNameTools.handleName(column), QueryRule.LE, param);
            return new WhereSqlBuilder(Boolean.TRUE, null, CommonConstant.EMPTY_OBJECT_ARRAY, pt.t1, pt.t2);
        }
        return new WhereSqlBuilder(Boolean.FALSE, null, CommonConstant.EMPTY_OBJECT_ARRAY);
    }

    public static WhereSqlBuilder whereLe(Boolean predicate, String column, Supplier<Object> param) {
        if (predicate) {
            Tuple2<String, Object[]> pt = SqlTemplateTools.parsePrecompileCondition(SqlNameTools.handleName(column), QueryRule.LE, param.get());
            return new WhereSqlBuilder(Boolean.TRUE, null, CommonConstant.EMPTY_OBJECT_ARRAY, pt.t1, pt.t2);
        }
        return new WhereSqlBuilder(Boolean.FALSE, null, CommonConstant.EMPTY_OBJECT_ARRAY);
    }

    public static <P> WhereSqlBuilder whereLe(LambdaFunction<P, ?> lambdaFunction, Object param) {
        return whereLe(LambdaTools.getColumnName(lambdaFunction), param);
    }

    public static <P> WhereSqlBuilder whereLe(Boolean predicate, LambdaFunction<P, ?> lambdaFunction, Object param) {
        if (Boolean.TRUE.equals(predicate)) {
            return whereLe(LambdaTools.getColumnName(lambdaFunction), param);
        }
        return new WhereSqlBuilder(Boolean.FALSE, null, CommonConstant.EMPTY_OBJECT_ARRAY);
    }

    public static <P> WhereSqlBuilder whereLe(Boolean predicate, LambdaFunction<P, ?> lambdaFunction, Supplier<Object> param) {
        if (Boolean.TRUE.equals(predicate)) {
            return whereLe(LambdaTools.getColumnName(lambdaFunction), param.get());
        }
        return new WhereSqlBuilder(Boolean.FALSE, null, CommonConstant.EMPTY_OBJECT_ARRAY);
    }

    public static WhereSqlBuilder whereLike(String column, Object param) {
        return where(Boolean.TRUE, column, QueryRule.LIKE, param);
    }

    public static WhereSqlBuilder whereLike(Boolean predicate, String column, Object param) {
        if (predicate) {
            Tuple2<String, Object[]> pt = SqlTemplateTools.parsePrecompileCondition(SqlNameTools.handleName(column), QueryRule.LIKE, param);
            return new WhereSqlBuilder(Boolean.TRUE, null, CommonConstant.EMPTY_OBJECT_ARRAY, pt.t1, pt.t2);
        }
        return new WhereSqlBuilder(Boolean.FALSE, null, CommonConstant.EMPTY_OBJECT_ARRAY);
    }

    public static WhereSqlBuilder whereLike(Boolean predicate, String column, Supplier<Object> param) {
        if (predicate) {
            Tuple2<String, Object[]> pt = SqlTemplateTools.parsePrecompileCondition(SqlNameTools.handleName(column), QueryRule.LIKE, param.get());
            return new WhereSqlBuilder(Boolean.TRUE, null, CommonConstant.EMPTY_OBJECT_ARRAY, pt.t1, pt.t2);
        }
        return new WhereSqlBuilder(Boolean.FALSE, null, CommonConstant.EMPTY_OBJECT_ARRAY);
    }

    public static <P> WhereSqlBuilder whereLike(LambdaFunction<P, ?> lambdaFunction, Object param) {
        return whereLike(LambdaTools.getColumnName(lambdaFunction), param);
    }

    public static <P> WhereSqlBuilder whereLike(Boolean predicate, LambdaFunction<P, ?> lambdaFunction, Object param) {
        if (Boolean.TRUE.equals(predicate)) {
            return whereLike(LambdaTools.getColumnName(lambdaFunction), param);
        }
        return new WhereSqlBuilder(Boolean.FALSE, null, CommonConstant.EMPTY_OBJECT_ARRAY);
    }

    public static <P> WhereSqlBuilder whereLike(Boolean predicate, LambdaFunction<P, ?> lambdaFunction, Supplier<Object> param) {
        if (Boolean.TRUE.equals(predicate)) {
            return whereLike(LambdaTools.getColumnName(lambdaFunction), param.get());
        }
        return new WhereSqlBuilder(Boolean.FALSE, null, CommonConstant.EMPTY_OBJECT_ARRAY);
    }


    public static WhereSqlBuilder whereNotLike(String column, Object param) {
        return where(Boolean.TRUE, column, QueryRule.NOT_LIKE, param);
    }

    public static WhereSqlBuilder whereNotLike(Boolean predicate, String column, Object param) {
        if (predicate) {
            Tuple2<String, Object[]> pt = SqlTemplateTools.parsePrecompileCondition(SqlNameTools.handleName(column), QueryRule.NOT_LIKE, param);
            return new WhereSqlBuilder(Boolean.TRUE, null, CommonConstant.EMPTY_OBJECT_ARRAY, pt.t1, pt.t2);
        }
        return new WhereSqlBuilder(Boolean.FALSE, null, CommonConstant.EMPTY_OBJECT_ARRAY);
    }

    public static WhereSqlBuilder whereNotLike(Boolean predicate, String column, Supplier<Object> param) {
        if (predicate) {
            Tuple2<String, Object[]> pt = SqlTemplateTools.parsePrecompileCondition(SqlNameTools.handleName(column), QueryRule.NOT_LIKE, param.get());
            return new WhereSqlBuilder(Boolean.TRUE, null, CommonConstant.EMPTY_OBJECT_ARRAY, pt.t1, pt.t2);
        }
        return new WhereSqlBuilder(Boolean.FALSE, null, CommonConstant.EMPTY_OBJECT_ARRAY);
    }

    public static <P> WhereSqlBuilder whereNotLike(LambdaFunction<P, ?> lambdaFunction, Object param) {
        return whereNotLike(LambdaTools.getColumnName(lambdaFunction), param);
    }

    public static <P> WhereSqlBuilder whereNotLike(Boolean predicate, LambdaFunction<P, ?> lambdaFunction, Object param) {
        if (Boolean.TRUE.equals(predicate)) {
            return whereNotLike(LambdaTools.getColumnName(lambdaFunction), param);
        }
        return new WhereSqlBuilder(Boolean.FALSE, null, CommonConstant.EMPTY_OBJECT_ARRAY);
    }

    public static <P> WhereSqlBuilder whereNotLike(Boolean predicate, LambdaFunction<P, ?> lambdaFunction, Supplier<Object> param) {
        if (Boolean.TRUE.equals(predicate)) {
            return whereNotLike(LambdaTools.getColumnName(lambdaFunction), param.get());
        }
        return new WhereSqlBuilder(Boolean.FALSE, null, CommonConstant.EMPTY_OBJECT_ARRAY);
    }

    public static WhereSqlBuilder whereLLike(String column, Object param) {
        return where(Boolean.TRUE, column, QueryRule.LEFT_LIKE, param);
    }

    public static WhereSqlBuilder whereLLike(Boolean predicate, String column, Object param) {
        if (predicate) {
            Tuple2<String, Object[]> pt = SqlTemplateTools.parsePrecompileCondition(SqlNameTools.handleName(column), QueryRule.LEFT_LIKE, param);
            return new WhereSqlBuilder(Boolean.TRUE, null, CommonConstant.EMPTY_OBJECT_ARRAY, pt.t1, pt.t2);
        }
        return new WhereSqlBuilder(Boolean.FALSE, null, CommonConstant.EMPTY_OBJECT_ARRAY);
    }

    public static WhereSqlBuilder whereLLike(Boolean predicate, String column, Supplier<Object> param) {
        if (predicate) {
            Tuple2<String, Object[]> pt = SqlTemplateTools.parsePrecompileCondition(SqlNameTools.handleName(column), QueryRule.LEFT_LIKE, param.get());
            return new WhereSqlBuilder(Boolean.TRUE, null, CommonConstant.EMPTY_OBJECT_ARRAY, pt.t1, pt.t2);
        }
        return new WhereSqlBuilder(Boolean.FALSE, null, CommonConstant.EMPTY_OBJECT_ARRAY);
    }

    public static <P> WhereSqlBuilder whereLLike(LambdaFunction<P, ?> lambdaFunction, Object param) {
        return whereLLike(LambdaTools.getColumnName(lambdaFunction), param);
    }

    public static <P> WhereSqlBuilder whereLLike(Boolean predicate, LambdaFunction<P, ?> lambdaFunction, Object param) {
        if (Boolean.TRUE.equals(predicate)) {
            return whereLLike(LambdaTools.getColumnName(lambdaFunction), param);
        }
        return new WhereSqlBuilder(Boolean.FALSE, null, CommonConstant.EMPTY_OBJECT_ARRAY);
    }

    public static <P> WhereSqlBuilder whereLLike(Boolean predicate, LambdaFunction<P, ?> lambdaFunction, Supplier<Object> param) {
        if (Boolean.TRUE.equals(predicate)) {
            return whereLLike(LambdaTools.getColumnName(lambdaFunction), param.get());
        }
        return new WhereSqlBuilder(Boolean.FALSE, null, CommonConstant.EMPTY_OBJECT_ARRAY);
    }

    public static WhereSqlBuilder whereNotLLike(String column, Object param) {
        return where(Boolean.TRUE, column, QueryRule.NOT_LEFT_LIKE, param);
    }

    public static WhereSqlBuilder whereNotLLike(Boolean predicate, String column, Object param) {
        if (predicate) {
            Tuple2<String, Object[]> pt = SqlTemplateTools.parsePrecompileCondition(SqlNameTools.handleName(column), QueryRule.NOT_LEFT_LIKE, param);
            return new WhereSqlBuilder(Boolean.TRUE, null, CommonConstant.EMPTY_OBJECT_ARRAY, pt.t1, pt.t2);
        }
        return new WhereSqlBuilder(Boolean.FALSE, null, CommonConstant.EMPTY_OBJECT_ARRAY);
    }

    public static WhereSqlBuilder whereNotLLike(Boolean predicate, String column, Supplier<Object> param) {
        if (predicate) {
            Tuple2<String, Object[]> pt = SqlTemplateTools.parsePrecompileCondition(SqlNameTools.handleName(column), QueryRule.NOT_LEFT_LIKE, param.get());
            return new WhereSqlBuilder(Boolean.TRUE, null, CommonConstant.EMPTY_OBJECT_ARRAY, pt.t1, pt.t2);
        }
        return new WhereSqlBuilder(Boolean.FALSE, null, CommonConstant.EMPTY_OBJECT_ARRAY);
    }

    public static <P> WhereSqlBuilder whereNotLLike(LambdaFunction<P, ?> lambdaFunction, Object param) {
        return whereNotLLike(LambdaTools.getColumnName(lambdaFunction), param);
    }

    public static <P> WhereSqlBuilder whereNotLLike(Boolean predicate, LambdaFunction<P, ?> lambdaFunction, Object param) {
        if (Boolean.TRUE.equals(predicate)) {
            return whereNotLLike(LambdaTools.getColumnName(lambdaFunction), param);
        }
        return new WhereSqlBuilder(Boolean.FALSE, null, CommonConstant.EMPTY_OBJECT_ARRAY);
    }

    public static <P> WhereSqlBuilder whereNotLLike(Boolean predicate, LambdaFunction<P, ?> lambdaFunction, Supplier<Object> param) {
        if (Boolean.TRUE.equals(predicate)) {
            return whereNotLLike(LambdaTools.getColumnName(lambdaFunction), param.get());
        }
        return new WhereSqlBuilder(Boolean.FALSE, null, CommonConstant.EMPTY_OBJECT_ARRAY);
    }

    public static WhereSqlBuilder whereRLike(String column, Object param) {
        return where(Boolean.TRUE, column, QueryRule.RIGHT_LIKE, param);
    }

    public static WhereSqlBuilder whereRLike(Boolean predicate, String column, Object param) {
        if (predicate) {
            Tuple2<String, Object[]> pt = SqlTemplateTools.parsePrecompileCondition(SqlNameTools.handleName(column), QueryRule.RIGHT_LIKE, param);
            return new WhereSqlBuilder(Boolean.TRUE, null, CommonConstant.EMPTY_OBJECT_ARRAY, pt.t1, pt.t2);
        }
        return new WhereSqlBuilder(Boolean.FALSE, null, CommonConstant.EMPTY_OBJECT_ARRAY);
    }

    public static WhereSqlBuilder whereRLike(Boolean predicate, String column, Supplier<Object> param) {
        if (predicate) {
            Tuple2<String, Object[]> pt = SqlTemplateTools.parsePrecompileCondition(SqlNameTools.handleName(column), QueryRule.RIGHT_LIKE, param.get());
            return new WhereSqlBuilder(Boolean.TRUE, null, CommonConstant.EMPTY_OBJECT_ARRAY, pt.t1, pt.t2);
        }
        return new WhereSqlBuilder(Boolean.FALSE, null, CommonConstant.EMPTY_OBJECT_ARRAY);
    }

    public static <P> WhereSqlBuilder whereRLike(LambdaFunction<P, ?> lambdaFunction, Object param) {
        return whereRLike(LambdaTools.getColumnName(lambdaFunction), param);
    }

    public static <P> WhereSqlBuilder whereRLike(Boolean predicate, LambdaFunction<P, ?> lambdaFunction, Object param) {
        if (Boolean.TRUE.equals(predicate)) {
            return whereRLike(LambdaTools.getColumnName(lambdaFunction), param);
        }
        return new WhereSqlBuilder(Boolean.FALSE, null, CommonConstant.EMPTY_OBJECT_ARRAY);
    }

    public static <P> WhereSqlBuilder whereRLike(Boolean predicate, LambdaFunction<P, ?> lambdaFunction, Supplier<Object> param) {
        if (Boolean.TRUE.equals(predicate)) {
            return whereRLike(LambdaTools.getColumnName(lambdaFunction), param.get());
        }
        return new WhereSqlBuilder(Boolean.FALSE, null, CommonConstant.EMPTY_OBJECT_ARRAY);
    }

    public static WhereSqlBuilder whereNotRLike(String column, Object param) {
        return where(Boolean.TRUE, column, QueryRule.NOT_RIGHT_LIKE, param);
    }

    public static WhereSqlBuilder whereNotRLike(Boolean predicate, String column, Object param) {
        if (predicate) {
            Tuple2<String, Object[]> pt = SqlTemplateTools.parsePrecompileCondition(SqlNameTools.handleName(column), QueryRule.NOT_RIGHT_LIKE, param);
            return new WhereSqlBuilder(Boolean.TRUE, null, CommonConstant.EMPTY_OBJECT_ARRAY, pt.t1, pt.t2);
        }
        return new WhereSqlBuilder(Boolean.FALSE, null, CommonConstant.EMPTY_OBJECT_ARRAY);
    }

    public static WhereSqlBuilder whereNotRLike(Boolean predicate, String column, Supplier<Object> param) {
        if (predicate) {
            Tuple2<String, Object[]> pt = SqlTemplateTools.parsePrecompileCondition(SqlNameTools.handleName(column), QueryRule.NOT_RIGHT_LIKE, param.get());
            return new WhereSqlBuilder(Boolean.TRUE, null, CommonConstant.EMPTY_OBJECT_ARRAY, pt.t1, pt.t2);
        }
        return new WhereSqlBuilder(Boolean.FALSE, null, CommonConstant.EMPTY_OBJECT_ARRAY);
    }

    public static <P> WhereSqlBuilder whereNotRLike(LambdaFunction<P, ?> lambdaFunction, Object param) {
        return whereNotRLike(LambdaTools.getColumnName(lambdaFunction), param);
    }

    public static <P> WhereSqlBuilder whereNotRLike(Boolean predicate, LambdaFunction<P, ?> lambdaFunction, Object param) {
        if (Boolean.TRUE.equals(predicate)) {
            return whereNotRLike(LambdaTools.getColumnName(lambdaFunction), param);
        }
        return new WhereSqlBuilder(Boolean.FALSE, null, CommonConstant.EMPTY_OBJECT_ARRAY);
    }

    public static <P> WhereSqlBuilder whereNotRLike(Boolean predicate, LambdaFunction<P, ?> lambdaFunction, Supplier<Object> param) {
        if (Boolean.TRUE.equals(predicate)) {
            return whereNotRLike(LambdaTools.getColumnName(lambdaFunction), param.get());
        }
        return new WhereSqlBuilder(Boolean.FALSE, null, CommonConstant.EMPTY_OBJECT_ARRAY);
    }

    public static WhereSqlBuilder whereIn(String column, Object... params) {
        return where(Boolean.TRUE, column, QueryRule.IN, params);
    }

    public static WhereSqlBuilder whereIn(Boolean predicate, String column, Object... params) {
        if (predicate) {
            Tuple2<String, Object[]> pt = SqlTemplateTools.parsePrecompileCondition(SqlNameTools.handleName(column), QueryRule.IN, params);
            return new WhereSqlBuilder(Boolean.TRUE, null, CommonConstant.EMPTY_OBJECT_ARRAY, pt.t1, pt.t2);
        }
        return new WhereSqlBuilder(Boolean.FALSE, null, CommonConstant.EMPTY_OBJECT_ARRAY);
    }

    public static WhereSqlBuilder whereIn(Boolean predicate, String column, Supplier<Object[]> params) {
        if (predicate) {
            Tuple2<String, Object[]> pt = SqlTemplateTools.parsePrecompileCondition(SqlNameTools.handleName(column), QueryRule.IN, params.get());
            return new WhereSqlBuilder(Boolean.TRUE, null, CommonConstant.EMPTY_OBJECT_ARRAY, pt.t1, pt.t2);
        }
        return new WhereSqlBuilder(Boolean.FALSE, null, CommonConstant.EMPTY_OBJECT_ARRAY);
    }

    public static <P> WhereSqlBuilder whereIn(LambdaFunction<P, ?> lambdaFunction, Object... params) {
        return whereIn(LambdaTools.getColumnName(lambdaFunction), params);
    }

    public static <P> WhereSqlBuilder whereIn(Boolean predicate, LambdaFunction<P, ?> lambdaFunction, Object... params) {
        if (Boolean.TRUE.equals(predicate)) {
            return whereIn(LambdaTools.getColumnName(lambdaFunction), params);
        }
        return new WhereSqlBuilder(Boolean.FALSE, null, CommonConstant.EMPTY_OBJECT_ARRAY);
    }

    public static <P> WhereSqlBuilder whereIn(Boolean predicate, LambdaFunction<P, ?> lambdaFunction, Supplier<Object[]> params) {
        if (Boolean.TRUE.equals(predicate)) {
            return whereIn(LambdaTools.getColumnName(lambdaFunction), params.get());
        }
        return new WhereSqlBuilder(Boolean.FALSE, null, CommonConstant.EMPTY_OBJECT_ARRAY);
    }

    public static WhereSqlBuilder whereNotIn(String column, Object... params) {
        return where(Boolean.TRUE, column, QueryRule.NOT_IN, params);
    }

    public static WhereSqlBuilder whereNotIn(Boolean predicate, String column, Object... params) {
        if (predicate) {
            Tuple2<String, Object[]> pt = SqlTemplateTools.parsePrecompileCondition(SqlNameTools.handleName(column), QueryRule.NOT_IN, params);
            return new WhereSqlBuilder(Boolean.TRUE, null, CommonConstant.EMPTY_OBJECT_ARRAY, pt.t1, pt.t2);
        }
        return new WhereSqlBuilder(Boolean.FALSE, null, CommonConstant.EMPTY_OBJECT_ARRAY);
    }

    public static WhereSqlBuilder whereNotIn(Boolean predicate, String column, Supplier<Object[]> params) {
        if (predicate) {
            Tuple2<String, Object[]> pt = SqlTemplateTools.parsePrecompileCondition(SqlNameTools.handleName(column), QueryRule.NOT_IN, params.get());
            return new WhereSqlBuilder(Boolean.TRUE, null, CommonConstant.EMPTY_OBJECT_ARRAY, pt.t1, pt.t2);
        }
        return new WhereSqlBuilder(Boolean.FALSE, null, CommonConstant.EMPTY_OBJECT_ARRAY);
    }

    public static <P> WhereSqlBuilder whereNotIn(LambdaFunction<P, ?> lambdaFunction, Object... params) {
        return whereNotIn(LambdaTools.getColumnName(lambdaFunction), params);
    }

    public static <P> WhereSqlBuilder whereNotIn(Boolean predicate, LambdaFunction<P, ?> lambdaFunction, Object... params) {
        if (Boolean.TRUE.equals(predicate)) {
            return whereNotIn(LambdaTools.getColumnName(lambdaFunction), params);
        }
        return new WhereSqlBuilder(Boolean.FALSE, null, CommonConstant.EMPTY_OBJECT_ARRAY);
    }

    public static <P> WhereSqlBuilder whereNotIn(Boolean predicate, LambdaFunction<P, ?> lambdaFunction, Supplier<Object[]> params) {
        if (Boolean.TRUE.equals(predicate)) {
            return whereNotIn(LambdaTools.getColumnName(lambdaFunction), params.get());
        }
        return new WhereSqlBuilder(Boolean.FALSE, null, CommonConstant.EMPTY_OBJECT_ARRAY);
    }

    public static WhereSqlBuilder whereBetween(String column, Object... params) {
        return where(Boolean.TRUE, column, QueryRule.BETWEEN_AND, params);
    }

    public static WhereSqlBuilder whereBetween(Boolean predicate, String column, Object... params) {
        if (predicate) {
            Tuple2<String, Object[]> pt = SqlTemplateTools.parsePrecompileCondition(SqlNameTools.handleName(column), QueryRule.BETWEEN_AND, params);
            return new WhereSqlBuilder(Boolean.TRUE, null, CommonConstant.EMPTY_OBJECT_ARRAY, pt.t1, pt.t2);
        }
        return new WhereSqlBuilder(Boolean.FALSE, null, CommonConstant.EMPTY_OBJECT_ARRAY);
    }

    public static WhereSqlBuilder whereBetween(Boolean predicate, String column, Supplier<Object> param1, Supplier<Object> param2) {
        if (predicate) {
            Tuple2<String, Object[]> pt = SqlTemplateTools.parsePrecompileCondition(SqlNameTools.handleName(column), QueryRule.BETWEEN_AND, param1.get(), param2.get());
            return new WhereSqlBuilder(Boolean.TRUE, null, CommonConstant.EMPTY_OBJECT_ARRAY, pt.t1, pt.t2);
        }
        return new WhereSqlBuilder(Boolean.FALSE, null, CommonConstant.EMPTY_OBJECT_ARRAY);
    }

    public static WhereSqlBuilder whereBetween(Boolean predicate, String column, Supplier<Object[]> params) {
        if (predicate) {
            Tuple2<String, Object[]> pt = SqlTemplateTools.parsePrecompileCondition(SqlNameTools.handleName(column), QueryRule.BETWEEN_AND, params.get());
            return new WhereSqlBuilder(Boolean.TRUE, null, CommonConstant.EMPTY_OBJECT_ARRAY, pt.t1, pt.t2);
        }
        return new WhereSqlBuilder(Boolean.FALSE, null, CommonConstant.EMPTY_OBJECT_ARRAY);
    }

    public static <P> WhereSqlBuilder whereBetween(LambdaFunction<P, ?> lambdaFunction, Object... params) {
        return whereBetween(LambdaTools.getColumnName(lambdaFunction), params);
    }

    public static <P> WhereSqlBuilder whereBetween(Boolean predicate, LambdaFunction<P, ?> lambdaFunction, Object... params) {
        if (Boolean.TRUE.equals(predicate)) {
            return whereBetween(LambdaTools.getColumnName(lambdaFunction), params);
        }
        return new WhereSqlBuilder(Boolean.FALSE, null, CommonConstant.EMPTY_OBJECT_ARRAY);
    }

    public static <P> WhereSqlBuilder whereBetween(Boolean predicate, LambdaFunction<P, ?> lambdaFunction, Supplier<Object> param1, Supplier<Object> param2) {
        if (Boolean.TRUE.equals(predicate)) {
            return whereBetween(LambdaTools.getColumnName(lambdaFunction), param1.get(), param2.get());
        }
        return new WhereSqlBuilder(Boolean.FALSE, null, CommonConstant.EMPTY_OBJECT_ARRAY);
    }

    public static <P> WhereSqlBuilder whereBetween(Boolean predicate, LambdaFunction<P, ?> lambdaFunction, Supplier<Object[]> params) {
        if (Boolean.TRUE.equals(predicate)) {
            return whereBetween(LambdaTools.getColumnName(lambdaFunction), params.get());
        }
        return new WhereSqlBuilder(Boolean.FALSE, null, CommonConstant.EMPTY_OBJECT_ARRAY);
    }

    public static WhereSqlBuilder whereNotBetween(String column, Object... params) {
        return where(Boolean.TRUE, column, QueryRule.NOT_BETWEEN_AND, params);
    }

    public static WhereSqlBuilder whereNotBetween(Boolean predicate, String column, Object... params) {
        if (predicate) {
            Tuple2<String, Object[]> pt = SqlTemplateTools.parsePrecompileCondition(SqlNameTools.handleName(column), QueryRule.NOT_BETWEEN_AND, params);
            return new WhereSqlBuilder(Boolean.TRUE, null, CommonConstant.EMPTY_OBJECT_ARRAY, pt.t1, pt.t2);
        }
        return new WhereSqlBuilder(Boolean.FALSE, null, CommonConstant.EMPTY_OBJECT_ARRAY);
    }

    public static WhereSqlBuilder whereNotBetween(Boolean predicate, String column, Supplier<Object> param1, Supplier<Object> param2) {
        if (predicate) {
            Tuple2<String, Object[]> pt = SqlTemplateTools.parsePrecompileCondition(SqlNameTools.handleName(column), QueryRule.NOT_BETWEEN_AND, param1.get(), param2.get());
            return new WhereSqlBuilder(Boolean.TRUE, null, CommonConstant.EMPTY_OBJECT_ARRAY, pt.t1, pt.t2);
        }
        return new WhereSqlBuilder(Boolean.FALSE, null, CommonConstant.EMPTY_OBJECT_ARRAY);
    }

    public static WhereSqlBuilder whereNotBetween(Boolean predicate, String column, Supplier<Object[]> params) {
        if (predicate) {
            Tuple2<String, Object[]> pt = SqlTemplateTools.parsePrecompileCondition(SqlNameTools.handleName(column), QueryRule.NOT_BETWEEN_AND, params.get());
            return new WhereSqlBuilder(Boolean.TRUE, null, CommonConstant.EMPTY_OBJECT_ARRAY, pt.t1, pt.t2);
        }
        return new WhereSqlBuilder(Boolean.FALSE, null, CommonConstant.EMPTY_OBJECT_ARRAY);
    }

    public static <P> WhereSqlBuilder whereNotBetween(LambdaFunction<P, ?> lambdaFunction, Object... params) {
        return whereNotBetween(LambdaTools.getColumnName(lambdaFunction), params);
    }

    public static <P> WhereSqlBuilder whereNotBetween(Boolean predicate, LambdaFunction<P, ?> lambdaFunction, Object... params) {
        if (Boolean.TRUE.equals(predicate)) {
            return whereNotBetween(LambdaTools.getColumnName(lambdaFunction), params);
        }
        return new WhereSqlBuilder(Boolean.FALSE, null, CommonConstant.EMPTY_OBJECT_ARRAY);
    }

    public static <P> WhereSqlBuilder whereNotBetween(Boolean predicate, LambdaFunction<P, ?> lambdaFunction, Supplier<Object> param1, Supplier<Object> param2) {
        if (Boolean.TRUE.equals(predicate)) {
            return whereNotBetween(LambdaTools.getColumnName(lambdaFunction), param1.get(), param2.get());
        }
        return new WhereSqlBuilder(Boolean.FALSE, null, CommonConstant.EMPTY_OBJECT_ARRAY);
    }

    public static <P> WhereSqlBuilder whereNotBetween(Boolean predicate, LambdaFunction<P, ?> lambdaFunction, Supplier<Object[]> params) {
        if (Boolean.TRUE.equals(predicate)) {
            return whereNotBetween(LambdaTools.getColumnName(lambdaFunction), params.get());
        }
        return new WhereSqlBuilder(Boolean.FALSE, null, CommonConstant.EMPTY_OBJECT_ARRAY);
    }

    public static WhereSqlBuilder whereNull(String column) {
        return where(Boolean.TRUE, column, QueryRule.IS_NULL);
    }

    public static WhereSqlBuilder whereNull(Boolean predicate, String column) {
        if (predicate) {
            Tuple2<String, Object[]> pt = SqlTemplateTools.parsePrecompileCondition(SqlNameTools.handleName(column), QueryRule.IS_NULL);
            return new WhereSqlBuilder(Boolean.TRUE, null, CommonConstant.EMPTY_OBJECT_ARRAY, pt.t1, pt.t2);
        }
        return new WhereSqlBuilder(Boolean.FALSE, null, CommonConstant.EMPTY_OBJECT_ARRAY);
    }

    public static <P> WhereSqlBuilder whereNull(LambdaFunction<P, ?> lambdaFunction) {
        return whereNull(LambdaTools.getColumnName(lambdaFunction));
    }

    public static <P> WhereSqlBuilder whereNull(Boolean predicate, LambdaFunction<P, ?> lambdaFunction) {
        if (Boolean.TRUE.equals(predicate)) {
            return whereNull(LambdaTools.getColumnName(lambdaFunction));
        }
        return new WhereSqlBuilder(Boolean.FALSE, null, CommonConstant.EMPTY_OBJECT_ARRAY);
    }

    public static WhereSqlBuilder whereIsNotNull(String column) {
        return where(Boolean.TRUE, column, QueryRule.NOT_NULL);
    }

    public static WhereSqlBuilder whereIsNotNull(Boolean predicate, String column) {
        if (predicate) {
            Tuple2<String, Object[]> pt = SqlTemplateTools.parsePrecompileCondition(SqlNameTools.handleName(column), QueryRule.NOT_NULL);
            return new WhereSqlBuilder(Boolean.TRUE, null, CommonConstant.EMPTY_OBJECT_ARRAY, pt.t1, pt.t2);
        }
        return new WhereSqlBuilder(Boolean.FALSE, null, CommonConstant.EMPTY_OBJECT_ARRAY);
    }

    public static <P> WhereSqlBuilder whereIsNotNull(LambdaFunction<P, ?> lambdaFunction) {
        return whereIsNotNull(LambdaTools.getColumnName(lambdaFunction));
    }

    public static <P> WhereSqlBuilder whereIsNotNull(Boolean predicate, LambdaFunction<P, ?> lambdaFunction) {
        if (Boolean.TRUE.equals(predicate)) {
            return whereIsNotNull(LambdaTools.getColumnName(lambdaFunction));
        }
        return new WhereSqlBuilder(Boolean.FALSE, null, CommonConstant.EMPTY_OBJECT_ARRAY);
    }

    public static WhereSqlBuilder whereExists(Object... sqlOrBuilder) {
        return where(Boolean.TRUE, CommonConstant.EMPTY_STRING, QueryRule.EXISTS, sqlOrBuilder);
    }

    public static WhereSqlBuilder whereExists(Boolean predicate, Object... sqlOrBuilder) {
        if (predicate) {
            Tuple2<String, Object[]> pt = SqlTemplateTools.parsePrecompileCondition(null, QueryRule.EXISTS, sqlOrBuilder);
            return new WhereSqlBuilder(Boolean.TRUE, null, CommonConstant.EMPTY_OBJECT_ARRAY, pt.t1, pt.t2);
        }
        return new WhereSqlBuilder(Boolean.FALSE, null, CommonConstant.EMPTY_OBJECT_ARRAY);
    }

    public static WhereSqlBuilder whereExists(Boolean predicate, Supplier<Object> sqlOrBuilder) {
        if (predicate) {
            Tuple2<String, Object[]> pt = SqlTemplateTools.parsePrecompileCondition(null, QueryRule.EXISTS, sqlOrBuilder.get());
            return new WhereSqlBuilder(Boolean.TRUE, null, CommonConstant.EMPTY_OBJECT_ARRAY, pt.t1, pt.t2);
        }
        return new WhereSqlBuilder(Boolean.FALSE, null, CommonConstant.EMPTY_OBJECT_ARRAY);
    }

    public static WhereSqlBuilder whereNotExists(Object... sqlOrBuilder) {
        return where(Boolean.TRUE, CommonConstant.EMPTY_STRING, QueryRule.NOT_EXISTS, sqlOrBuilder);
    }

    public static WhereSqlBuilder whereNotExists(Boolean predicate, Object... sqlOrBuilder) {
        if (predicate) {
            Tuple2<String, Object[]> pt = SqlTemplateTools.parsePrecompileCondition(null, QueryRule.NOT_EXISTS, sqlOrBuilder);
            return new WhereSqlBuilder(Boolean.TRUE, null, CommonConstant.EMPTY_OBJECT_ARRAY, pt.t1, pt.t2);
        }
        return new WhereSqlBuilder(Boolean.FALSE, null, CommonConstant.EMPTY_OBJECT_ARRAY);
    }

    public static WhereSqlBuilder whereNotExists(Boolean predicate, Supplier<Object> sqlOrBuilder) {
        if (predicate) {
            Tuple2<String, Object[]> pt = SqlTemplateTools.parsePrecompileCondition(null, QueryRule.NOT_EXISTS, sqlOrBuilder.get());
            return new WhereSqlBuilder(Boolean.TRUE, null, CommonConstant.EMPTY_OBJECT_ARRAY, pt.t1, pt.t2);
        }
        return new WhereSqlBuilder(Boolean.FALSE, null, CommonConstant.EMPTY_OBJECT_ARRAY);
    }

    public static JoinOnSqlBuilder on(Object... queryCriteria) {
        return on(Boolean.TRUE, queryCriteria);
    }

    public static JoinOnSqlBuilder on(Boolean predicate, Object... queryCriteria) {
        return new JoinOnSqlBuilder(predicate, null, queryCriteria);
    }

    public static JoinOnSqlBuilder on(String condition, Object... params) {
        return on(Boolean.TRUE, condition, params);
    }

    public static JoinOnSqlBuilder on(Boolean predicate, String condition, Object... params) {
        return new JoinOnSqlBuilder(predicate, null, CommonConstant.EMPTY_OBJECT_ARRAY, condition, params);
    }

    public static JoinOnSqlBuilder on(Boolean predicate, String condition, Supplier<Object[]> params) {
        return new JoinOnSqlBuilder(predicate, null, CommonConstant.EMPTY_OBJECT_ARRAY, condition, params);
    }

    public static JoinOnSqlBuilder on(String column, QueryRule option, Object... params) {
        return on(Boolean.TRUE, column, option, params);
    }

    public static JoinOnSqlBuilder on(Boolean predicate, String column, QueryRule option, Object... params) {
        if (predicate) {
            Tuple2<String, Object[]> pt = SqlTemplateTools.parsePrecompileCondition(SqlNameTools.handleName(column), option, params);
            return new JoinOnSqlBuilder(Boolean.TRUE, null, CommonConstant.EMPTY_OBJECT_ARRAY, pt.t1, pt.t2);
        }
        return new JoinOnSqlBuilder(Boolean.FALSE, null, CommonConstant.EMPTY_OBJECT_ARRAY);
    }

    public static JoinOnSqlBuilder on(Boolean predicate, String column, QueryRule option, Supplier<Object[]> params) {
        if (predicate) {
            Tuple2<String, Object[]> pt = SqlTemplateTools.parsePrecompileCondition(SqlNameTools.handleName(column), option, params.get());
            return new JoinOnSqlBuilder(Boolean.TRUE, null, CommonConstant.EMPTY_OBJECT_ARRAY, pt.t1, pt.t2);
        }
        return new JoinOnSqlBuilder(Boolean.FALSE, null, CommonConstant.EMPTY_OBJECT_ARRAY);
    }

    public static <P> JoinOnSqlBuilder on(LambdaFunction<P, ?> lambdaFunction, QueryRule option, Object... params) {
        return on(LambdaTools.getColumnName(lambdaFunction), option, params);
    }

    public static <P> JoinOnSqlBuilder on(Boolean predicate, LambdaFunction<P, ?> lambdaFunction, QueryRule option, Object... params) {
        if (Boolean.TRUE.equals(predicate)) {
            return on(LambdaTools.getColumnName(lambdaFunction), option, params);
        }
        return new JoinOnSqlBuilder(Boolean.FALSE, null, CommonConstant.EMPTY_OBJECT_ARRAY);
    }

    public static <P> JoinOnSqlBuilder on(Boolean predicate, LambdaFunction<P, ?> lambdaFunction, QueryRule option, Supplier<Object[]> params) {
        if (Boolean.TRUE.equals(predicate)) {
            return on(LambdaTools.getColumnName(lambdaFunction), option, params.get());
        }
        return new JoinOnSqlBuilder(Boolean.FALSE, null, CommonConstant.EMPTY_OBJECT_ARRAY);
    }

    public static JoinOnSqlBuilder onEq(String column, Object param) {
        return on(Boolean.TRUE, column, QueryRule.EQ, param);
    }

    public static JoinOnSqlBuilder onEq(Boolean predicate, String column, Object param) {
        if (predicate) {
            Tuple2<String, Object[]> pt = SqlTemplateTools.parsePrecompileCondition(SqlNameTools.handleName(column), QueryRule.EQ, param);
            return new JoinOnSqlBuilder(Boolean.TRUE, null, CommonConstant.EMPTY_OBJECT_ARRAY, pt.t1, pt.t2);
        }
        return new JoinOnSqlBuilder(Boolean.FALSE, null, CommonConstant.EMPTY_OBJECT_ARRAY);
    }

    public static JoinOnSqlBuilder onEq(Boolean predicate, String column, Supplier<Object> param) {
        if (predicate) {
            Tuple2<String, Object[]> pt = SqlTemplateTools.parsePrecompileCondition(SqlNameTools.handleName(column), QueryRule.EQ, param.get());
            return new JoinOnSqlBuilder(Boolean.TRUE, null, CommonConstant.EMPTY_OBJECT_ARRAY, pt.t1, pt.t2);
        }
        return new JoinOnSqlBuilder(Boolean.FALSE, null, CommonConstant.EMPTY_OBJECT_ARRAY);
    }

    public static <P> JoinOnSqlBuilder onEq(LambdaFunction<P, ?> lambdaFunction, Object param) {
        return onEq(LambdaTools.getColumnName(lambdaFunction), param);
    }

    public static <P> JoinOnSqlBuilder onEq(Boolean predicate, LambdaFunction<P, ?> lambdaFunction, Object param) {
        if (Boolean.TRUE.equals(predicate)) {
            return onEq(LambdaTools.getColumnName(lambdaFunction), param);
        }
        return new JoinOnSqlBuilder(Boolean.FALSE, null, CommonConstant.EMPTY_OBJECT_ARRAY);
    }

    public static <P> JoinOnSqlBuilder onEq(Boolean predicate, LambdaFunction<P, ?> lambdaFunction, Supplier<Object> param) {
        if (Boolean.TRUE.equals(predicate)) {
            return onEq(LambdaTools.getColumnName(lambdaFunction), param.get());
        }
        return new JoinOnSqlBuilder(Boolean.FALSE, null, CommonConstant.EMPTY_OBJECT_ARRAY);
    }

    public static JoinOnSqlBuilder onNe(String column, Object param) {
        return on(Boolean.TRUE, column, QueryRule.NE, param);
    }

    public static JoinOnSqlBuilder onNe(Boolean predicate, String column, Object param) {
        if (predicate) {
            Tuple2<String, Object[]> pt = SqlTemplateTools.parsePrecompileCondition(SqlNameTools.handleName(column), QueryRule.NE, param);
            return new JoinOnSqlBuilder(Boolean.TRUE, null, CommonConstant.EMPTY_OBJECT_ARRAY, pt.t1, pt.t2);
        }
        return new JoinOnSqlBuilder(Boolean.FALSE, null, CommonConstant.EMPTY_OBJECT_ARRAY);
    }

    public static JoinOnSqlBuilder onNe(Boolean predicate, String column, Supplier<Object> param) {
        if (predicate) {
            Tuple2<String, Object[]> pt = SqlTemplateTools.parsePrecompileCondition(SqlNameTools.handleName(column), QueryRule.NE, param.get());
            return new JoinOnSqlBuilder(Boolean.TRUE, null, CommonConstant.EMPTY_OBJECT_ARRAY, pt.t1, pt.t2);
        }
        return new JoinOnSqlBuilder(Boolean.FALSE, null, CommonConstant.EMPTY_OBJECT_ARRAY);
    }

    public static <P> JoinOnSqlBuilder onNe(LambdaFunction<P, ?> lambdaFunction, Object param) {
        return onNe(LambdaTools.getColumnName(lambdaFunction), param);
    }

    public static <P> JoinOnSqlBuilder onNe(Boolean predicate, LambdaFunction<P, ?> lambdaFunction, Object param) {
        if (Boolean.TRUE.equals(predicate)) {
            return onNe(LambdaTools.getColumnName(lambdaFunction), param);
        }
        return new JoinOnSqlBuilder(Boolean.FALSE, null, CommonConstant.EMPTY_OBJECT_ARRAY);
    }

    public static <P> JoinOnSqlBuilder onNe(Boolean predicate, LambdaFunction<P, ?> lambdaFunction, Supplier<Object> param) {
        if (Boolean.TRUE.equals(predicate)) {
            return onNe(LambdaTools.getColumnName(lambdaFunction), param.get());
        }
        return new JoinOnSqlBuilder(Boolean.FALSE, null, CommonConstant.EMPTY_OBJECT_ARRAY);
    }

    public static JoinOnSqlBuilder onGt(String column, Object param) {
        return on(Boolean.TRUE, column, QueryRule.GT, param);
    }

    public static JoinOnSqlBuilder onGt(Boolean predicate, String column, Object param) {
        if (predicate) {
            Tuple2<String, Object[]> pt = SqlTemplateTools.parsePrecompileCondition(SqlNameTools.handleName(column), QueryRule.GT, param);
            return new JoinOnSqlBuilder(Boolean.TRUE, null, CommonConstant.EMPTY_OBJECT_ARRAY, pt.t1, pt.t2);
        }
        return new JoinOnSqlBuilder(Boolean.FALSE, null, CommonConstant.EMPTY_OBJECT_ARRAY);
    }

    public static JoinOnSqlBuilder onGt(Boolean predicate, String column, Supplier<Object> param) {
        if (predicate) {
            Tuple2<String, Object[]> pt = SqlTemplateTools.parsePrecompileCondition(SqlNameTools.handleName(column), QueryRule.GT, param.get());
            return new JoinOnSqlBuilder(Boolean.TRUE, null, CommonConstant.EMPTY_OBJECT_ARRAY, pt.t1, pt.t2);
        }
        return new JoinOnSqlBuilder(Boolean.FALSE, null, CommonConstant.EMPTY_OBJECT_ARRAY);
    }

    public static <P> JoinOnSqlBuilder onGt(LambdaFunction<P, ?> lambdaFunction, Object param) {
        return onGt(LambdaTools.getColumnName(lambdaFunction), param);
    }

    public static <P> JoinOnSqlBuilder onGt(Boolean predicate, LambdaFunction<P, ?> lambdaFunction, Object param) {
        if (Boolean.TRUE.equals(predicate)) {
            return onGt(LambdaTools.getColumnName(lambdaFunction), param);
        }
        return new JoinOnSqlBuilder(Boolean.FALSE, null, CommonConstant.EMPTY_OBJECT_ARRAY);
    }

    public static <P> JoinOnSqlBuilder onGt(Boolean predicate, LambdaFunction<P, ?> lambdaFunction, Supplier<Object> param) {
        if (Boolean.TRUE.equals(predicate)) {
            return onGt(LambdaTools.getColumnName(lambdaFunction), param.get());
        }
        return new JoinOnSqlBuilder(Boolean.FALSE, null, CommonConstant.EMPTY_OBJECT_ARRAY);
    }

    public static JoinOnSqlBuilder onGe(String column, Object param) {
        return on(Boolean.TRUE, column, QueryRule.GE, param);
    }

    public static JoinOnSqlBuilder onGe(Boolean predicate, String column, Object param) {
        if (predicate) {
            Tuple2<String, Object[]> pt = SqlTemplateTools.parsePrecompileCondition(SqlNameTools.handleName(column), QueryRule.GE, param);
            return new JoinOnSqlBuilder(Boolean.TRUE, null, CommonConstant.EMPTY_OBJECT_ARRAY, pt.t1, pt.t2);
        }
        return new JoinOnSqlBuilder(Boolean.FALSE, null, CommonConstant.EMPTY_OBJECT_ARRAY);
    }

    public static JoinOnSqlBuilder onGe(Boolean predicate, String column, Supplier<Object> param) {
        if (predicate) {
            Tuple2<String, Object[]> pt = SqlTemplateTools.parsePrecompileCondition(SqlNameTools.handleName(column), QueryRule.GE, param.get());
            return new JoinOnSqlBuilder(Boolean.TRUE, null, CommonConstant.EMPTY_OBJECT_ARRAY, pt.t1, pt.t2);
        }
        return new JoinOnSqlBuilder(Boolean.FALSE, null, CommonConstant.EMPTY_OBJECT_ARRAY);
    }

    public static <P> JoinOnSqlBuilder onGe(LambdaFunction<P, ?> lambdaFunction, Object param) {
        return onGe(LambdaTools.getColumnName(lambdaFunction), param);
    }

    public static <P> JoinOnSqlBuilder onGe(Boolean predicate, LambdaFunction<P, ?> lambdaFunction, Object param) {
        if (Boolean.TRUE.equals(predicate)) {
            return onGe(LambdaTools.getColumnName(lambdaFunction), param);
        }
        return new JoinOnSqlBuilder(Boolean.FALSE, null, CommonConstant.EMPTY_OBJECT_ARRAY);
    }

    public static <P> JoinOnSqlBuilder onGe(Boolean predicate, LambdaFunction<P, ?> lambdaFunction, Supplier<Object> param) {
        if (Boolean.TRUE.equals(predicate)) {
            return onGe(LambdaTools.getColumnName(lambdaFunction), param.get());
        }
        return new JoinOnSqlBuilder(Boolean.FALSE, null, CommonConstant.EMPTY_OBJECT_ARRAY);
    }

    public static JoinOnSqlBuilder onLt(String column, Object param) {
        return on(Boolean.TRUE, column, QueryRule.LT, param);
    }

    public static JoinOnSqlBuilder onLt(Boolean predicate, String column, Object param) {
        if (predicate) {
            Tuple2<String, Object[]> pt = SqlTemplateTools.parsePrecompileCondition(SqlNameTools.handleName(column), QueryRule.LT, param);
            return new JoinOnSqlBuilder(Boolean.TRUE, null, CommonConstant.EMPTY_OBJECT_ARRAY, pt.t1, pt.t2);
        }
        return new JoinOnSqlBuilder(Boolean.FALSE, null, CommonConstant.EMPTY_OBJECT_ARRAY);
    }

    public static JoinOnSqlBuilder onLt(Boolean predicate, String column, Supplier<Object> param) {
        if (predicate) {
            Tuple2<String, Object[]> pt = SqlTemplateTools.parsePrecompileCondition(SqlNameTools.handleName(column), QueryRule.LT, param.get());
            return new JoinOnSqlBuilder(Boolean.TRUE, null, CommonConstant.EMPTY_OBJECT_ARRAY, pt.t1, pt.t2);
        }
        return new JoinOnSqlBuilder(Boolean.FALSE, null, CommonConstant.EMPTY_OBJECT_ARRAY);
    }

    public static <P> JoinOnSqlBuilder onLt(LambdaFunction<P, ?> lambdaFunction, Object param) {
        return onLt(LambdaTools.getColumnName(lambdaFunction), param);
    }

    public static <P> JoinOnSqlBuilder onLt(Boolean predicate, LambdaFunction<P, ?> lambdaFunction, Object param) {
        if (Boolean.TRUE.equals(predicate)) {
            return onLt(LambdaTools.getColumnName(lambdaFunction), param);
        }
        return new JoinOnSqlBuilder(Boolean.FALSE, null, CommonConstant.EMPTY_OBJECT_ARRAY);
    }

    public static <P> JoinOnSqlBuilder onLt(Boolean predicate, LambdaFunction<P, ?> lambdaFunction, Supplier<Object> param) {
        if (Boolean.TRUE.equals(predicate)) {
            return onLt(LambdaTools.getColumnName(lambdaFunction), param.get());
        }
        return new JoinOnSqlBuilder(Boolean.FALSE, null, CommonConstant.EMPTY_OBJECT_ARRAY);
    }

    public static JoinOnSqlBuilder onLe(String column, Object param) {
        return on(Boolean.TRUE, column, QueryRule.LE, param);
    }

    public static JoinOnSqlBuilder onLe(Boolean predicate, String column, Object param) {
        if (predicate) {
            Tuple2<String, Object[]> pt = SqlTemplateTools.parsePrecompileCondition(SqlNameTools.handleName(column), QueryRule.LE, param);
            return new JoinOnSqlBuilder(Boolean.TRUE, null, CommonConstant.EMPTY_OBJECT_ARRAY, pt.t1, pt.t2);
        }
        return new JoinOnSqlBuilder(Boolean.FALSE, null, CommonConstant.EMPTY_OBJECT_ARRAY);
    }

    public static JoinOnSqlBuilder onLe(Boolean predicate, String column, Supplier<Object> param) {
        if (predicate) {
            Tuple2<String, Object[]> pt = SqlTemplateTools.parsePrecompileCondition(SqlNameTools.handleName(column), QueryRule.LE, param.get());
            return new JoinOnSqlBuilder(Boolean.TRUE, null, CommonConstant.EMPTY_OBJECT_ARRAY, pt.t1, pt.t2);
        }
        return new JoinOnSqlBuilder(Boolean.FALSE, null, CommonConstant.EMPTY_OBJECT_ARRAY);
    }

    public static <P> JoinOnSqlBuilder onLe(LambdaFunction<P, ?> lambdaFunction, Object param) {
        return onLe(LambdaTools.getColumnName(lambdaFunction), param);
    }

    public static <P> JoinOnSqlBuilder onLe(Boolean predicate, LambdaFunction<P, ?> lambdaFunction, Object param) {
        if (Boolean.TRUE.equals(predicate)) {
            return onLe(LambdaTools.getColumnName(lambdaFunction), param);
        }
        return new JoinOnSqlBuilder(Boolean.FALSE, null, CommonConstant.EMPTY_OBJECT_ARRAY);
    }

    public static <P> JoinOnSqlBuilder onLe(Boolean predicate, LambdaFunction<P, ?> lambdaFunction, Supplier<Object> param) {
        if (Boolean.TRUE.equals(predicate)) {
            return onLe(LambdaTools.getColumnName(lambdaFunction), param.get());
        }
        return new JoinOnSqlBuilder(Boolean.FALSE, null, CommonConstant.EMPTY_OBJECT_ARRAY);
    }

    public static JoinOnSqlBuilder onLike(String column, Object param) {
        return on(Boolean.TRUE, column, QueryRule.LIKE, param);
    }

    public static JoinOnSqlBuilder onLike(Boolean predicate, String column, Object param) {
        if (predicate) {
            Tuple2<String, Object[]> pt = SqlTemplateTools.parsePrecompileCondition(SqlNameTools.handleName(column), QueryRule.LIKE, param);
            return new JoinOnSqlBuilder(Boolean.TRUE, null, CommonConstant.EMPTY_OBJECT_ARRAY, pt.t1, pt.t2);
        }
        return new JoinOnSqlBuilder(Boolean.FALSE, null, CommonConstant.EMPTY_OBJECT_ARRAY);
    }

    public static JoinOnSqlBuilder onLike(Boolean predicate, String column, Supplier<Object> param) {
        if (predicate) {
            Tuple2<String, Object[]> pt = SqlTemplateTools.parsePrecompileCondition(SqlNameTools.handleName(column), QueryRule.LIKE, param.get());
            return new JoinOnSqlBuilder(Boolean.TRUE, null, CommonConstant.EMPTY_OBJECT_ARRAY, pt.t1, pt.t2);
        }
        return new JoinOnSqlBuilder(Boolean.FALSE, null, CommonConstant.EMPTY_OBJECT_ARRAY);
    }

    public static <P> JoinOnSqlBuilder onLike(LambdaFunction<P, ?> lambdaFunction, Object param) {
        return onLike(LambdaTools.getColumnName(lambdaFunction), param);
    }

    public static <P> JoinOnSqlBuilder onLike(Boolean predicate, LambdaFunction<P, ?> lambdaFunction, Object param) {
        if (Boolean.TRUE.equals(predicate)) {
            return onLike(LambdaTools.getColumnName(lambdaFunction), param);
        }
        return new JoinOnSqlBuilder(Boolean.FALSE, null, CommonConstant.EMPTY_OBJECT_ARRAY);
    }

    public static <P> JoinOnSqlBuilder onLike(Boolean predicate, LambdaFunction<P, ?> lambdaFunction, Supplier<Object> param) {
        if (Boolean.TRUE.equals(predicate)) {
            return onLike(LambdaTools.getColumnName(lambdaFunction), param.get());
        }
        return new JoinOnSqlBuilder(Boolean.FALSE, null, CommonConstant.EMPTY_OBJECT_ARRAY);
    }

    public static JoinOnSqlBuilder onNotLike(String column, Object param) {
        return on(Boolean.TRUE, column, QueryRule.NOT_LIKE, param);
    }

    public static JoinOnSqlBuilder onNotLike(Boolean predicate, String column, Object param) {
        if (predicate) {
            Tuple2<String, Object[]> pt = SqlTemplateTools.parsePrecompileCondition(SqlNameTools.handleName(column), QueryRule.NOT_LIKE, param);
            return new JoinOnSqlBuilder(Boolean.TRUE, null, CommonConstant.EMPTY_OBJECT_ARRAY, pt.t1, pt.t2);
        }
        return new JoinOnSqlBuilder(Boolean.FALSE, null, CommonConstant.EMPTY_OBJECT_ARRAY);
    }

    public static JoinOnSqlBuilder onNotLike(Boolean predicate, String column, Supplier<Object> param) {
        if (predicate) {
            Tuple2<String, Object[]> pt = SqlTemplateTools.parsePrecompileCondition(SqlNameTools.handleName(column), QueryRule.NOT_LIKE, param.get());
            return new JoinOnSqlBuilder(Boolean.TRUE, null, CommonConstant.EMPTY_OBJECT_ARRAY, pt.t1, pt.t2);
        }
        return new JoinOnSqlBuilder(Boolean.FALSE, null, CommonConstant.EMPTY_OBJECT_ARRAY);
    }

    public static <P> JoinOnSqlBuilder onNotLike(LambdaFunction<P, ?> lambdaFunction, Object param) {
        return onNotLike(LambdaTools.getColumnName(lambdaFunction), param);
    }

    public static <P> JoinOnSqlBuilder onNotLike(Boolean predicate, LambdaFunction<P, ?> lambdaFunction, Object param) {
        if (Boolean.TRUE.equals(predicate)) {
            return onNotLike(LambdaTools.getColumnName(lambdaFunction), param);
        }
        return new JoinOnSqlBuilder(Boolean.FALSE, null, CommonConstant.EMPTY_OBJECT_ARRAY);
    }

    public static <P> JoinOnSqlBuilder onNotLike(Boolean predicate, LambdaFunction<P, ?> lambdaFunction, Supplier<Object> param) {
        if (Boolean.TRUE.equals(predicate)) {
            return onNotLike(LambdaTools.getColumnName(lambdaFunction), param.get());
        }
        return new JoinOnSqlBuilder(Boolean.FALSE, null, CommonConstant.EMPTY_OBJECT_ARRAY);
    }

    public static JoinOnSqlBuilder onLLike(String column, Object param) {
        return on(Boolean.TRUE, column, QueryRule.LEFT_LIKE, param);
    }

    public static JoinOnSqlBuilder onLLike(Boolean predicate, String column, Object param) {
        if (predicate) {
            Tuple2<String, Object[]> pt = SqlTemplateTools.parsePrecompileCondition(SqlNameTools.handleName(column), QueryRule.LEFT_LIKE, param);
            return new JoinOnSqlBuilder(Boolean.TRUE, null, CommonConstant.EMPTY_OBJECT_ARRAY, pt.t1, pt.t2);
        }
        return new JoinOnSqlBuilder(Boolean.FALSE, null, CommonConstant.EMPTY_OBJECT_ARRAY);
    }

    public static JoinOnSqlBuilder onLLike(Boolean predicate, String column, Supplier<Object> param) {
        if (predicate) {
            Tuple2<String, Object[]> pt = SqlTemplateTools.parsePrecompileCondition(SqlNameTools.handleName(column), QueryRule.LEFT_LIKE, param.get());
            return new JoinOnSqlBuilder(Boolean.TRUE, null, CommonConstant.EMPTY_OBJECT_ARRAY, pt.t1, pt.t2);
        }
        return new JoinOnSqlBuilder(Boolean.FALSE, null, CommonConstant.EMPTY_OBJECT_ARRAY);
    }

    public static <P> JoinOnSqlBuilder onLLike(LambdaFunction<P, ?> lambdaFunction, Object param) {
        return onLLike(LambdaTools.getColumnName(lambdaFunction), param);
    }

    public static <P> JoinOnSqlBuilder onLLike(Boolean predicate, LambdaFunction<P, ?> lambdaFunction, Object param) {
        if (Boolean.TRUE.equals(predicate)) {
            return onLLike(LambdaTools.getColumnName(lambdaFunction), param);
        }
        return new JoinOnSqlBuilder(Boolean.FALSE, null, CommonConstant.EMPTY_OBJECT_ARRAY);
    }

    public static <P> JoinOnSqlBuilder onLLike(Boolean predicate, LambdaFunction<P, ?> lambdaFunction, Supplier<Object> param) {
        if (Boolean.TRUE.equals(predicate)) {
            return onLLike(LambdaTools.getColumnName(lambdaFunction), param.get());
        }
        return new JoinOnSqlBuilder(Boolean.FALSE, null, CommonConstant.EMPTY_OBJECT_ARRAY);
    }

    public static JoinOnSqlBuilder onNotLLike(String column, Object param) {
        return on(Boolean.TRUE, column, QueryRule.NOT_LEFT_LIKE, param);
    }

    public static JoinOnSqlBuilder onNotLLike(Boolean predicate, String column, Object param) {
        if (predicate) {
            Tuple2<String, Object[]> pt = SqlTemplateTools.parsePrecompileCondition(SqlNameTools.handleName(column), QueryRule.NOT_LEFT_LIKE, param);
            return new JoinOnSqlBuilder(Boolean.TRUE, null, CommonConstant.EMPTY_OBJECT_ARRAY, pt.t1, pt.t2);
        }
        return new JoinOnSqlBuilder(Boolean.FALSE, null, CommonConstant.EMPTY_OBJECT_ARRAY);
    }

    public static JoinOnSqlBuilder onNotLLike(Boolean predicate, String column, Supplier<Object> param) {
        if (predicate) {
            Tuple2<String, Object[]> pt = SqlTemplateTools.parsePrecompileCondition(SqlNameTools.handleName(column), QueryRule.NOT_LEFT_LIKE, param.get());
            return new JoinOnSqlBuilder(Boolean.TRUE, null, CommonConstant.EMPTY_OBJECT_ARRAY, pt.t1, pt.t2);
        }
        return new JoinOnSqlBuilder(Boolean.FALSE, null, CommonConstant.EMPTY_OBJECT_ARRAY);
    }

    public static <P> JoinOnSqlBuilder onNotLLike(LambdaFunction<P, ?> lambdaFunction, Object param) {
        return onNotLLike(LambdaTools.getColumnName(lambdaFunction), param);
    }

    public static <P> JoinOnSqlBuilder onNotLLike(Boolean predicate, LambdaFunction<P, ?> lambdaFunction, Object param) {
        if (Boolean.TRUE.equals(predicate)) {
            return onNotLLike(LambdaTools.getColumnName(lambdaFunction), param);
        }
        return new JoinOnSqlBuilder(Boolean.FALSE, null, CommonConstant.EMPTY_OBJECT_ARRAY);
    }

    public static <P> JoinOnSqlBuilder onNotLLike(Boolean predicate, LambdaFunction<P, ?> lambdaFunction, Supplier<Object> param) {
        if (Boolean.TRUE.equals(predicate)) {
            return onNotLLike(LambdaTools.getColumnName(lambdaFunction), param.get());
        }
        return new JoinOnSqlBuilder(Boolean.FALSE, null, CommonConstant.EMPTY_OBJECT_ARRAY);
    }

    public static JoinOnSqlBuilder onRLike(String column, Object param) {
        return on(Boolean.TRUE, column, QueryRule.RIGHT_LIKE, param);
    }

    public static JoinOnSqlBuilder onRLike(Boolean predicate, String column, Object param) {
        if (predicate) {
            Tuple2<String, Object[]> pt = SqlTemplateTools.parsePrecompileCondition(SqlNameTools.handleName(column), QueryRule.RIGHT_LIKE, param);
            return new JoinOnSqlBuilder(Boolean.TRUE, null, CommonConstant.EMPTY_OBJECT_ARRAY, pt.t1, pt.t2);
        }
        return new JoinOnSqlBuilder(Boolean.FALSE, null, CommonConstant.EMPTY_OBJECT_ARRAY);
    }

    public static JoinOnSqlBuilder onRLike(Boolean predicate, String column, Supplier<Object> param) {
        if (predicate) {
            Tuple2<String, Object[]> pt = SqlTemplateTools.parsePrecompileCondition(SqlNameTools.handleName(column), QueryRule.RIGHT_LIKE, param.get());
            return new JoinOnSqlBuilder(Boolean.TRUE, null, CommonConstant.EMPTY_OBJECT_ARRAY, pt.t1, pt.t2);
        }
        return new JoinOnSqlBuilder(Boolean.FALSE, null, CommonConstant.EMPTY_OBJECT_ARRAY);
    }

    public static <P> JoinOnSqlBuilder onRLike(LambdaFunction<P, ?> lambdaFunction, Object param) {
        return onRLike(LambdaTools.getColumnName(lambdaFunction), param);
    }

    public static <P> JoinOnSqlBuilder onRLike(Boolean predicate, LambdaFunction<P, ?> lambdaFunction, Object param) {
        if (Boolean.TRUE.equals(predicate)) {
            return onRLike(LambdaTools.getColumnName(lambdaFunction), param);
        }
        return new JoinOnSqlBuilder(Boolean.FALSE, null, CommonConstant.EMPTY_OBJECT_ARRAY);
    }

    public static <P> JoinOnSqlBuilder onRLike(Boolean predicate, LambdaFunction<P, ?> lambdaFunction, Supplier<Object> param) {
        if (Boolean.TRUE.equals(predicate)) {
            return onRLike(LambdaTools.getColumnName(lambdaFunction), param.get());
        }
        return new JoinOnSqlBuilder(Boolean.FALSE, null, CommonConstant.EMPTY_OBJECT_ARRAY);
    }

    public static JoinOnSqlBuilder onNotRLike(String column, Object param) {
        return on(Boolean.TRUE, column, QueryRule.NOT_RIGHT_LIKE, param);
    }

    public static JoinOnSqlBuilder onNotRLike(Boolean predicate, String column, Object param) {
        if (predicate) {
            Tuple2<String, Object[]> pt = SqlTemplateTools.parsePrecompileCondition(SqlNameTools.handleName(column), QueryRule.NOT_RIGHT_LIKE, param);
            return new JoinOnSqlBuilder(Boolean.TRUE, null, CommonConstant.EMPTY_OBJECT_ARRAY, pt.t1, pt.t2);
        }
        return new JoinOnSqlBuilder(Boolean.FALSE, null, CommonConstant.EMPTY_OBJECT_ARRAY);
    }

    public static JoinOnSqlBuilder onNotRLike(Boolean predicate, String column, Supplier<Object> param) {
        if (predicate) {
            Tuple2<String, Object[]> pt = SqlTemplateTools.parsePrecompileCondition(SqlNameTools.handleName(column), QueryRule.NOT_RIGHT_LIKE, param.get());
            return new JoinOnSqlBuilder(Boolean.TRUE, null, CommonConstant.EMPTY_OBJECT_ARRAY, pt.t1, pt.t2);
        }
        return new JoinOnSqlBuilder(Boolean.FALSE, null, CommonConstant.EMPTY_OBJECT_ARRAY);
    }

    public static <P> JoinOnSqlBuilder onNotRLike(LambdaFunction<P, ?> lambdaFunction, Object param) {
        return onNotRLike(LambdaTools.getColumnName(lambdaFunction), param);
    }

    public static <P> JoinOnSqlBuilder onNotRLike(Boolean predicate, LambdaFunction<P, ?> lambdaFunction, Object param) {
        if (Boolean.TRUE.equals(predicate)) {
            return onNotRLike(LambdaTools.getColumnName(lambdaFunction), param);
        }
        return new JoinOnSqlBuilder(Boolean.FALSE, null, CommonConstant.EMPTY_OBJECT_ARRAY);
    }

    public static <P> JoinOnSqlBuilder onNotRLike(Boolean predicate, LambdaFunction<P, ?> lambdaFunction, Supplier<Object> param) {
        if (Boolean.TRUE.equals(predicate)) {
            return onNotRLike(LambdaTools.getColumnName(lambdaFunction), param.get());
        }
        return new JoinOnSqlBuilder(Boolean.FALSE, null, CommonConstant.EMPTY_OBJECT_ARRAY);
    }

    public static JoinOnSqlBuilder onIn(String column, Object... params) {
        return on(Boolean.TRUE, column, QueryRule.IN, params);
    }

    public static JoinOnSqlBuilder onIn(Boolean predicate, String column, Object... params) {
        if (predicate) {
            Tuple2<String, Object[]> pt = SqlTemplateTools.parsePrecompileCondition(SqlNameTools.handleName(column), QueryRule.IN, params);
            return new JoinOnSqlBuilder(Boolean.TRUE, null, CommonConstant.EMPTY_OBJECT_ARRAY, pt.t1, pt.t2);
        }
        return new JoinOnSqlBuilder(Boolean.FALSE, null, CommonConstant.EMPTY_OBJECT_ARRAY);
    }

    public static JoinOnSqlBuilder onIn(Boolean predicate, String column, Supplier<Object[]> params) {
        if (predicate) {
            Tuple2<String, Object[]> pt = SqlTemplateTools.parsePrecompileCondition(SqlNameTools.handleName(column), QueryRule.IN, params.get());
            return new JoinOnSqlBuilder(Boolean.TRUE, null, CommonConstant.EMPTY_OBJECT_ARRAY, pt.t1, pt.t2);
        }
        return new JoinOnSqlBuilder(Boolean.FALSE, null, CommonConstant.EMPTY_OBJECT_ARRAY);
    }

    public static <P> JoinOnSqlBuilder onIn(LambdaFunction<P, ?> lambdaFunction, Object... params) {
        return onIn(LambdaTools.getColumnName(lambdaFunction), params);
    }

    public static <P> JoinOnSqlBuilder onIn(Boolean predicate, LambdaFunction<P, ?> lambdaFunction, Object... params) {
        if (Boolean.TRUE.equals(predicate)) {
            return onIn(LambdaTools.getColumnName(lambdaFunction), params);
        }
        return new JoinOnSqlBuilder(Boolean.FALSE, null, CommonConstant.EMPTY_OBJECT_ARRAY);
    }

    public static <P> JoinOnSqlBuilder onIn(Boolean predicate, LambdaFunction<P, ?> lambdaFunction, Supplier<Object[]> params) {
        if (Boolean.TRUE.equals(predicate)) {
            return onIn(LambdaTools.getColumnName(lambdaFunction), params.get());
        }
        return new JoinOnSqlBuilder(Boolean.FALSE, null, CommonConstant.EMPTY_OBJECT_ARRAY);
    }

    public static JoinOnSqlBuilder onNotIn(String column, Object... params) {
        return on(Boolean.TRUE, column, QueryRule.NOT_IN, params);
    }

    public static JoinOnSqlBuilder onNotIn(Boolean predicate, String column, Object... params) {
        if (predicate) {
            Tuple2<String, Object[]> pt = SqlTemplateTools.parsePrecompileCondition(SqlNameTools.handleName(column), QueryRule.NOT_IN, params);
            return new JoinOnSqlBuilder(Boolean.TRUE, null, CommonConstant.EMPTY_OBJECT_ARRAY, pt.t1, pt.t2);
        }
        return new JoinOnSqlBuilder(Boolean.FALSE, null, CommonConstant.EMPTY_OBJECT_ARRAY);
    }

    public static JoinOnSqlBuilder onNotIn(Boolean predicate, String column, Supplier<Object[]> params) {
        if (predicate) {
            Tuple2<String, Object[]> pt = SqlTemplateTools.parsePrecompileCondition(SqlNameTools.handleName(column), QueryRule.NOT_IN, params.get());
            return new JoinOnSqlBuilder(Boolean.TRUE, null, CommonConstant.EMPTY_OBJECT_ARRAY, pt.t1, pt.t2);
        }
        return new JoinOnSqlBuilder(Boolean.FALSE, null, CommonConstant.EMPTY_OBJECT_ARRAY);
    }

    public static <P> JoinOnSqlBuilder onNotIn(LambdaFunction<P, ?> lambdaFunction, Object... params) {
        return onNotIn(LambdaTools.getColumnName(lambdaFunction), params);
    }

    public static <P> JoinOnSqlBuilder onNotIn(Boolean predicate, LambdaFunction<P, ?> lambdaFunction, Object... params) {
        if (Boolean.TRUE.equals(predicate)) {
            return onNotIn(LambdaTools.getColumnName(lambdaFunction), params);
        }
        return new JoinOnSqlBuilder(Boolean.FALSE, null, CommonConstant.EMPTY_OBJECT_ARRAY);
    }

    public static <P> JoinOnSqlBuilder onNotIn(Boolean predicate, LambdaFunction<P, ?> lambdaFunction, Supplier<Object[]> params) {
        if (Boolean.TRUE.equals(predicate)) {
            return onNotIn(LambdaTools.getColumnName(lambdaFunction), params.get());
        }
        return new JoinOnSqlBuilder(Boolean.FALSE, null, CommonConstant.EMPTY_OBJECT_ARRAY);
    }

    public static JoinOnSqlBuilder onBetween(String column, Object... params) {
        return on(Boolean.TRUE, column, QueryRule.BETWEEN_AND, params);
    }

    public static JoinOnSqlBuilder onBetween(Boolean predicate, String column, Object... params) {
        if (predicate) {
            Tuple2<String, Object[]> pt = SqlTemplateTools.parsePrecompileCondition(SqlNameTools.handleName(column), QueryRule.BETWEEN_AND, params);
            return new JoinOnSqlBuilder(Boolean.TRUE, null, CommonConstant.EMPTY_OBJECT_ARRAY, pt.t1, pt.t2);
        }
        return new JoinOnSqlBuilder(Boolean.FALSE, null, CommonConstant.EMPTY_OBJECT_ARRAY);
    }

    public static JoinOnSqlBuilder onBetween(Boolean predicate, String column, Supplier<Object> param1, Supplier<Object> param2) {
        if (predicate) {
            Tuple2<String, Object[]> pt = SqlTemplateTools.parsePrecompileCondition(SqlNameTools.handleName(column), QueryRule.BETWEEN_AND, param1.get(), param2.get());
            return new JoinOnSqlBuilder(Boolean.TRUE, null, CommonConstant.EMPTY_OBJECT_ARRAY, pt.t1, pt.t2);
        }
        return new JoinOnSqlBuilder(Boolean.FALSE, null, CommonConstant.EMPTY_OBJECT_ARRAY);
    }

    public static JoinOnSqlBuilder onBetween(Boolean predicate, String column, Supplier<Object[]> params) {
        if (predicate) {
            Tuple2<String, Object[]> pt = SqlTemplateTools.parsePrecompileCondition(SqlNameTools.handleName(column), QueryRule.BETWEEN_AND, params.get());
            return new JoinOnSqlBuilder(Boolean.TRUE, null, CommonConstant.EMPTY_OBJECT_ARRAY, pt.t1, pt.t2);
        }
        return new JoinOnSqlBuilder(Boolean.FALSE, null, CommonConstant.EMPTY_OBJECT_ARRAY);
    }

    public static <P> JoinOnSqlBuilder onBetween(LambdaFunction<P, ?> lambdaFunction, Object... params) {
        return onBetween(LambdaTools.getColumnName(lambdaFunction), params);
    }

    public static <P> JoinOnSqlBuilder onBetween(Boolean predicate, LambdaFunction<P, ?> lambdaFunction, Object... params) {
        if (Boolean.TRUE.equals(predicate)) {
            return onBetween(LambdaTools.getColumnName(lambdaFunction), params);
        }
        return new JoinOnSqlBuilder(Boolean.FALSE, null, CommonConstant.EMPTY_OBJECT_ARRAY);
    }

    public static <P> JoinOnSqlBuilder onBetween(Boolean predicate, LambdaFunction<P, ?> lambdaFunction, Supplier<Object> param1, Supplier<Object> param2) {
        if (Boolean.TRUE.equals(predicate)) {
            return onBetween(LambdaTools.getColumnName(lambdaFunction), param1.get(), param2.get());
        }
        return new JoinOnSqlBuilder(Boolean.FALSE, null, CommonConstant.EMPTY_OBJECT_ARRAY);
    }

    public static <P> JoinOnSqlBuilder onBetween(Boolean predicate, LambdaFunction<P, ?> lambdaFunction, Supplier<Object[]> params) {
        if (Boolean.TRUE.equals(predicate)) {
            return onBetween(LambdaTools.getColumnName(lambdaFunction), params.get());
        }
        return new JoinOnSqlBuilder(Boolean.FALSE, null, CommonConstant.EMPTY_OBJECT_ARRAY);
    }

    public static JoinOnSqlBuilder onNotBetween(String column, Object... params) {
        return on(Boolean.TRUE, column, QueryRule.NOT_BETWEEN_AND, params);
    }

    public static JoinOnSqlBuilder onNotBetween(Boolean predicate, String column, Object... params) {
        if (predicate) {
            Tuple2<String, Object[]> pt = SqlTemplateTools.parsePrecompileCondition(SqlNameTools.handleName(column), QueryRule.NOT_BETWEEN_AND, params);
            return new JoinOnSqlBuilder(Boolean.TRUE, null, CommonConstant.EMPTY_OBJECT_ARRAY, pt.t1, pt.t2);
        }
        return new JoinOnSqlBuilder(Boolean.FALSE, null, CommonConstant.EMPTY_OBJECT_ARRAY);
    }

    public static JoinOnSqlBuilder onNotBetween(Boolean predicate, String column, Supplier<Object> param1, Supplier<Object> param2) {
        if (predicate) {
            Tuple2<String, Object[]> pt = SqlTemplateTools.parsePrecompileCondition(SqlNameTools.handleName(column), QueryRule.NOT_BETWEEN_AND, param1.get(), param2.get());
            return new JoinOnSqlBuilder(Boolean.TRUE, null, CommonConstant.EMPTY_OBJECT_ARRAY, pt.t1, pt.t2);
        }
        return new JoinOnSqlBuilder(Boolean.FALSE, null, CommonConstant.EMPTY_OBJECT_ARRAY);
    }

    public static JoinOnSqlBuilder onNotBetween(Boolean predicate, String column, Supplier<Object[]> params) {
        if (predicate) {
            Tuple2<String, Object[]> pt = SqlTemplateTools.parsePrecompileCondition(SqlNameTools.handleName(column), QueryRule.NOT_BETWEEN_AND, params.get());
            return new JoinOnSqlBuilder(Boolean.TRUE, null, CommonConstant.EMPTY_OBJECT_ARRAY, pt.t1, pt.t2);
        }
        return new JoinOnSqlBuilder(Boolean.FALSE, null, CommonConstant.EMPTY_OBJECT_ARRAY);
    }

    public static <P> JoinOnSqlBuilder onNotBetween(LambdaFunction<P, ?> lambdaFunction, Object... params) {
        return onNotBetween(LambdaTools.getColumnName(lambdaFunction), params);
    }

    public static <P> JoinOnSqlBuilder onNotBetween(Boolean predicate, LambdaFunction<P, ?> lambdaFunction, Object... params) {
        if (Boolean.TRUE.equals(predicate)) {
            return onNotBetween(LambdaTools.getColumnName(lambdaFunction), params);
        }
        return new JoinOnSqlBuilder(Boolean.FALSE, null, CommonConstant.EMPTY_OBJECT_ARRAY);
    }

    public static <P> JoinOnSqlBuilder onNotBetween(Boolean predicate, LambdaFunction<P, ?> lambdaFunction, Supplier<Object> param1, Supplier<Object> param2) {
        if (Boolean.TRUE.equals(predicate)) {
            return onNotBetween(LambdaTools.getColumnName(lambdaFunction), param1.get(), param2.get());
        }
        return new JoinOnSqlBuilder(Boolean.FALSE, null, CommonConstant.EMPTY_OBJECT_ARRAY);
    }

    public static <P> JoinOnSqlBuilder onNotBetween(Boolean predicate, LambdaFunction<P, ?> lambdaFunction, Supplier<Object[]> params) {
        if (Boolean.TRUE.equals(predicate)) {
            return onNotBetween(LambdaTools.getColumnName(lambdaFunction), params.get());
        }
        return new JoinOnSqlBuilder(Boolean.FALSE, null, CommonConstant.EMPTY_OBJECT_ARRAY);
    }

    public static JoinOnSqlBuilder onNull(String column) {
        return on(Boolean.TRUE, column, QueryRule.IS_NULL);
    }

    public static JoinOnSqlBuilder onNull(Boolean predicate, String column) {
        if (predicate) {
            Tuple2<String, Object[]> pt = SqlTemplateTools.parsePrecompileCondition(SqlNameTools.handleName(column), QueryRule.IS_NULL);
            return new JoinOnSqlBuilder(Boolean.TRUE, null, CommonConstant.EMPTY_OBJECT_ARRAY, pt.t1, pt.t2);
        }
        return new JoinOnSqlBuilder(Boolean.FALSE, null, CommonConstant.EMPTY_OBJECT_ARRAY);
    }

    public static <P> JoinOnSqlBuilder onNull(LambdaFunction<P, ?> lambdaFunction) {
        return onNull(LambdaTools.getColumnName(lambdaFunction));
    }

    public static <P> JoinOnSqlBuilder onNull(Boolean predicate, LambdaFunction<P, ?> lambdaFunction) {
        if (Boolean.TRUE.equals(predicate)) {
            return onNull(LambdaTools.getColumnName(lambdaFunction));
        }
        return new JoinOnSqlBuilder(Boolean.FALSE, null, CommonConstant.EMPTY_OBJECT_ARRAY);
    }

    public static JoinOnSqlBuilder onIsNotNull(String column) {
        return on(Boolean.TRUE, column, QueryRule.NOT_NULL);
    }

    public static JoinOnSqlBuilder onIsNotNull(Boolean predicate, String column) {
        if (predicate) {
            Tuple2<String, Object[]> pt = SqlTemplateTools.parsePrecompileCondition(SqlNameTools.handleName(column), QueryRule.NOT_NULL);
            return new JoinOnSqlBuilder(Boolean.TRUE, null, CommonConstant.EMPTY_OBJECT_ARRAY, pt.t1, pt.t2);
        }
        return new JoinOnSqlBuilder(Boolean.FALSE, null, CommonConstant.EMPTY_OBJECT_ARRAY);
    }

    public static <P> JoinOnSqlBuilder onIsNotNull(LambdaFunction<P, ?> lambdaFunction) {
        return onIsNotNull(LambdaTools.getColumnName(lambdaFunction));
    }

    public static <P> JoinOnSqlBuilder onIsNotNull(Boolean predicate, LambdaFunction<P, ?> lambdaFunction) {
        if (Boolean.TRUE.equals(predicate)) {
            return onIsNotNull(LambdaTools.getColumnName(lambdaFunction));
        }
        return new JoinOnSqlBuilder(Boolean.FALSE, null, CommonConstant.EMPTY_OBJECT_ARRAY);
    }

    public static JoinOnSqlBuilder onExists(Object... sqlOrBuilder) {
        return on(Boolean.TRUE, CommonConstant.EMPTY_STRING, QueryRule.EXISTS, sqlOrBuilder);
    }

    public static JoinOnSqlBuilder onExists(Boolean predicate, Object... sqlOrBuilder) {
        if (predicate) {
            Tuple2<String, Object[]> pt = SqlTemplateTools.parsePrecompileCondition(null, QueryRule.EXISTS, sqlOrBuilder);
            return new JoinOnSqlBuilder(Boolean.TRUE, null, CommonConstant.EMPTY_OBJECT_ARRAY, pt.t1, pt.t2);
        }
        return new JoinOnSqlBuilder(Boolean.FALSE, null, CommonConstant.EMPTY_OBJECT_ARRAY);
    }

    public static JoinOnSqlBuilder onExists(Boolean predicate, Supplier<Object> sqlOrBuilder) {
        if (predicate) {
            Tuple2<String, Object[]> pt = SqlTemplateTools.parsePrecompileCondition(null, QueryRule.EXISTS, sqlOrBuilder.get());
            return new JoinOnSqlBuilder(Boolean.TRUE, null, CommonConstant.EMPTY_OBJECT_ARRAY, pt.t1, pt.t2);
        }
        return new JoinOnSqlBuilder(Boolean.FALSE, null, CommonConstant.EMPTY_OBJECT_ARRAY);
    }

    public static JoinOnSqlBuilder onNotExists(Object... sqlOrBuilder) {
        return on(Boolean.TRUE, CommonConstant.EMPTY_STRING, QueryRule.NOT_EXISTS, sqlOrBuilder);
    }

    public static JoinOnSqlBuilder onNotExists(Boolean predicate, Object... sqlOrBuilder) {
        if (predicate) {
            Tuple2<String, Object[]> pt = SqlTemplateTools.parsePrecompileCondition(null, QueryRule.NOT_EXISTS, sqlOrBuilder);
            return new JoinOnSqlBuilder(Boolean.TRUE, null, CommonConstant.EMPTY_OBJECT_ARRAY, pt.t1, pt.t2);
        }
        return new JoinOnSqlBuilder(Boolean.FALSE, null, CommonConstant.EMPTY_OBJECT_ARRAY);
    }

    public static JoinOnSqlBuilder onNotExists(Boolean predicate, Supplier<Object> sqlOrBuilder) {
        if (predicate) {
            Tuple2<String, Object[]> pt = SqlTemplateTools.parsePrecompileCondition(null, QueryRule.NOT_EXISTS, sqlOrBuilder.get());
            return new JoinOnSqlBuilder(Boolean.TRUE, null, CommonConstant.EMPTY_OBJECT_ARRAY, pt.t1, pt.t2);
        }
        return new JoinOnSqlBuilder(Boolean.FALSE, null, CommonConstant.EMPTY_OBJECT_ARRAY);
    }

    public static HavingSqlBuilder having(Object... queryCriteria) {
        return having(Boolean.TRUE, queryCriteria);
    }

    public static HavingSqlBuilder having(Boolean predicate, Object... queryCriteria) {
        return new HavingSqlBuilder(predicate, null, queryCriteria);
    }

    public static HavingSqlBuilder having(String condition, Object... params) {
        return having(Boolean.TRUE, condition, params);
    }

    public static HavingSqlBuilder having(Boolean predicate, String condition, Object... params) {
        return new HavingSqlBuilder(predicate, null, CommonConstant.EMPTY_OBJECT_ARRAY, condition, params);
    }

    public static HavingSqlBuilder having(Boolean predicate, String condition, Supplier<Object[]> params) {
        return new HavingSqlBuilder(predicate, null, CommonConstant.EMPTY_OBJECT_ARRAY, condition, params);
    }

    public static HavingSqlBuilder having(String column, QueryRule option, Object... params) {
        return having(Boolean.TRUE, column, option, params);
    }

    public static HavingSqlBuilder having(Boolean predicate, String column, QueryRule option, Object... params) {
        if (predicate) {
            Tuple2<String, Object[]> pt = SqlTemplateTools.parsePrecompileCondition(SqlNameTools.handleName(column), option, params);
            return new HavingSqlBuilder(Boolean.TRUE, null, CommonConstant.EMPTY_OBJECT_ARRAY, pt.t1, pt.t2);
        }
        return new HavingSqlBuilder(Boolean.FALSE, null, CommonConstant.EMPTY_OBJECT_ARRAY);
    }

    public static HavingSqlBuilder having(Boolean predicate, String column, QueryRule option, Supplier<Object[]> params) {
        if (predicate) {
            Tuple2<String, Object[]> pt = SqlTemplateTools.parsePrecompileCondition(SqlNameTools.handleName(column), option, params.get());
            return new HavingSqlBuilder(Boolean.TRUE, null, CommonConstant.EMPTY_OBJECT_ARRAY, pt.t1, pt.t2);
        }
        return new HavingSqlBuilder(Boolean.FALSE, null, CommonConstant.EMPTY_OBJECT_ARRAY);
    }

    public static <P> HavingSqlBuilder having(LambdaFunction<P, ?> lambdaFunction, QueryRule option, Object... params) {
        return having(LambdaTools.getColumnName(lambdaFunction), option, params);
    }

    public static <P> HavingSqlBuilder having(Boolean predicate, LambdaFunction<P, ?> lambdaFunction, QueryRule option, Object... params) {
        if (Boolean.TRUE.equals(predicate)) {
            return having(LambdaTools.getColumnName(lambdaFunction), option, params);
        }
        return new HavingSqlBuilder(Boolean.FALSE, null, CommonConstant.EMPTY_OBJECT_ARRAY);
    }

    public static <P> HavingSqlBuilder having(Boolean predicate, LambdaFunction<P, ?> lambdaFunction, QueryRule option, Supplier<Object[]> params) {
        if (Boolean.TRUE.equals(predicate)) {
            return having(LambdaTools.getColumnName(lambdaFunction), option, params.get());
        }
        return new HavingSqlBuilder(Boolean.FALSE, null, CommonConstant.EMPTY_OBJECT_ARRAY);
    }

    public static HavingSqlBuilder havingEq(String column, Object param) {
        return having(Boolean.TRUE, column, QueryRule.EQ, param);
    }

    public static HavingSqlBuilder havingEq(Boolean predicate, String column, Object param) {
        if (predicate) {
            Tuple2<String, Object[]> pt = SqlTemplateTools.parsePrecompileCondition(SqlNameTools.handleName(column), QueryRule.EQ, param);
            return new HavingSqlBuilder(Boolean.TRUE, null, CommonConstant.EMPTY_OBJECT_ARRAY, pt.t1, pt.t2);
        }
        return new HavingSqlBuilder(Boolean.FALSE, null, CommonConstant.EMPTY_OBJECT_ARRAY);
    }

    public static HavingSqlBuilder havingEq(Boolean predicate, String column, Supplier<Object> param) {
        if (predicate) {
            Tuple2<String, Object[]> pt = SqlTemplateTools.parsePrecompileCondition(SqlNameTools.handleName(column), QueryRule.EQ, param.get());
            return new HavingSqlBuilder(Boolean.TRUE, null, CommonConstant.EMPTY_OBJECT_ARRAY, pt.t1, pt.t2);
        }
        return new HavingSqlBuilder(Boolean.FALSE, null, CommonConstant.EMPTY_OBJECT_ARRAY);
    }

    public static <P> HavingSqlBuilder havingEq(LambdaFunction<P, ?> lambdaFunction, Object param) {
        return havingEq(LambdaTools.getColumnName(lambdaFunction), param);
    }

    public static <P> HavingSqlBuilder havingEq(Boolean predicate, LambdaFunction<P, ?> lambdaFunction, Object param) {
        if (Boolean.TRUE.equals(predicate)) {
            return havingEq(LambdaTools.getColumnName(lambdaFunction), param);
        }
        return new HavingSqlBuilder(Boolean.FALSE, null, CommonConstant.EMPTY_OBJECT_ARRAY);
    }

    public static <P> HavingSqlBuilder havingEq(Boolean predicate, LambdaFunction<P, ?> lambdaFunction, Supplier<Object> param) {
        if (Boolean.TRUE.equals(predicate)) {
            return havingEq(LambdaTools.getColumnName(lambdaFunction), param.get());
        }
        return new HavingSqlBuilder(Boolean.FALSE, null, CommonConstant.EMPTY_OBJECT_ARRAY);
    }

    public static HavingSqlBuilder havingNe(String column, Object param) {
        return having(Boolean.TRUE, column, QueryRule.NE, param);
    }

    public static HavingSqlBuilder havingNe(Boolean predicate, String column, Object param) {
        if (predicate) {
            Tuple2<String, Object[]> pt = SqlTemplateTools.parsePrecompileCondition(SqlNameTools.handleName(column), QueryRule.NE, param);
            return new HavingSqlBuilder(Boolean.TRUE, null, CommonConstant.EMPTY_OBJECT_ARRAY, pt.t1, pt.t2);
        }
        return new HavingSqlBuilder(Boolean.FALSE, null, CommonConstant.EMPTY_OBJECT_ARRAY);
    }

    public static HavingSqlBuilder havingNe(Boolean predicate, String column, Supplier<Object> param) {
        if (predicate) {
            Tuple2<String, Object[]> pt = SqlTemplateTools.parsePrecompileCondition(SqlNameTools.handleName(column), QueryRule.NE, param.get());
            return new HavingSqlBuilder(Boolean.TRUE, null, CommonConstant.EMPTY_OBJECT_ARRAY, pt.t1, pt.t2);
        }
        return new HavingSqlBuilder(Boolean.FALSE, null, CommonConstant.EMPTY_OBJECT_ARRAY);
    }

    public static <P> HavingSqlBuilder havingNe(LambdaFunction<P, ?> lambdaFunction, Object param) {
        return havingNe(LambdaTools.getColumnName(lambdaFunction), param);
    }

    public static <P> HavingSqlBuilder havingNe(Boolean predicate, LambdaFunction<P, ?> lambdaFunction, Object param) {
        if (Boolean.TRUE.equals(predicate)) {
            return havingNe(LambdaTools.getColumnName(lambdaFunction), param);
        }
        return new HavingSqlBuilder(Boolean.FALSE, null, CommonConstant.EMPTY_OBJECT_ARRAY);
    }

    public static <P> HavingSqlBuilder havingNe(Boolean predicate, LambdaFunction<P, ?> lambdaFunction, Supplier<Object> param) {
        if (Boolean.TRUE.equals(predicate)) {
            return havingNe(LambdaTools.getColumnName(lambdaFunction), param.get());
        }
        return new HavingSqlBuilder(Boolean.FALSE, null, CommonConstant.EMPTY_OBJECT_ARRAY);
    }

    public static HavingSqlBuilder havingGt(String column, Object param) {
        return having(Boolean.TRUE, column, QueryRule.GT, param);
    }

    public static HavingSqlBuilder havingGt(Boolean predicate, String column, Object param) {
        if (predicate) {
            Tuple2<String, Object[]> pt = SqlTemplateTools.parsePrecompileCondition(SqlNameTools.handleName(column), QueryRule.GT, param);
            return new HavingSqlBuilder(Boolean.TRUE, null, CommonConstant.EMPTY_OBJECT_ARRAY, pt.t1, pt.t2);
        }
        return new HavingSqlBuilder(Boolean.FALSE, null, CommonConstant.EMPTY_OBJECT_ARRAY);
    }

    public static HavingSqlBuilder havingGt(Boolean predicate, String column, Supplier<Object> param) {
        if (predicate) {
            Tuple2<String, Object[]> pt = SqlTemplateTools.parsePrecompileCondition(SqlNameTools.handleName(column), QueryRule.GT, param.get());
            return new HavingSqlBuilder(Boolean.TRUE, null, CommonConstant.EMPTY_OBJECT_ARRAY, pt.t1, pt.t2);
        }
        return new HavingSqlBuilder(Boolean.FALSE, null, CommonConstant.EMPTY_OBJECT_ARRAY);
    }

    public static <P> HavingSqlBuilder havingGt(LambdaFunction<P, ?> lambdaFunction, Object param) {
        return havingGt(LambdaTools.getColumnName(lambdaFunction), param);
    }

    public static <P> HavingSqlBuilder havingGt(Boolean predicate, LambdaFunction<P, ?> lambdaFunction, Object param) {
        if (Boolean.TRUE.equals(predicate)) {
            return havingGt(LambdaTools.getColumnName(lambdaFunction), param);
        }
        return new HavingSqlBuilder(Boolean.FALSE, null, CommonConstant.EMPTY_OBJECT_ARRAY);
    }

    public static <P> HavingSqlBuilder havingGt(Boolean predicate, LambdaFunction<P, ?> lambdaFunction, Supplier<Object> param) {
        if (Boolean.TRUE.equals(predicate)) {
            return havingGt(LambdaTools.getColumnName(lambdaFunction), param.get());
        }
        return new HavingSqlBuilder(Boolean.FALSE, null, CommonConstant.EMPTY_OBJECT_ARRAY);
    }

    public static HavingSqlBuilder havingGe(String column, Object param) {
        return having(Boolean.TRUE, column, QueryRule.GE, param);
    }

    public static HavingSqlBuilder havingGe(Boolean predicate, String column, Object param) {
        if (predicate) {
            Tuple2<String, Object[]> pt = SqlTemplateTools.parsePrecompileCondition(SqlNameTools.handleName(column), QueryRule.GE, param);
            return new HavingSqlBuilder(Boolean.TRUE, null, CommonConstant.EMPTY_OBJECT_ARRAY, pt.t1, pt.t2);
        }
        return new HavingSqlBuilder(Boolean.FALSE, null, CommonConstant.EMPTY_OBJECT_ARRAY);
    }

    public static HavingSqlBuilder havingGe(Boolean predicate, String column, Supplier<Object> param) {
        if (predicate) {
            Tuple2<String, Object[]> pt = SqlTemplateTools.parsePrecompileCondition(SqlNameTools.handleName(column), QueryRule.GE, param.get());
            return new HavingSqlBuilder(Boolean.TRUE, null, CommonConstant.EMPTY_OBJECT_ARRAY, pt.t1, pt.t2);
        }
        return new HavingSqlBuilder(Boolean.FALSE, null, CommonConstant.EMPTY_OBJECT_ARRAY);
    }

    public static <P> HavingSqlBuilder havingGe(LambdaFunction<P, ?> lambdaFunction, Object param) {
        return havingGe(LambdaTools.getColumnName(lambdaFunction), param);
    }

    public static <P> HavingSqlBuilder havingGe(Boolean predicate, LambdaFunction<P, ?> lambdaFunction, Object param) {
        if (Boolean.TRUE.equals(predicate)) {
            return havingGe(LambdaTools.getColumnName(lambdaFunction), param);
        }
        return new HavingSqlBuilder(Boolean.FALSE, null, CommonConstant.EMPTY_OBJECT_ARRAY);
    }

    public static <P> HavingSqlBuilder havingGe(Boolean predicate, LambdaFunction<P, ?> lambdaFunction, Supplier<Object> param) {
        if (Boolean.TRUE.equals(predicate)) {
            return havingGe(LambdaTools.getColumnName(lambdaFunction), param.get());
        }
        return new HavingSqlBuilder(Boolean.FALSE, null, CommonConstant.EMPTY_OBJECT_ARRAY);
    }

    public static HavingSqlBuilder havingLt(String column, Object param) {
        return having(Boolean.TRUE, column, QueryRule.LT, param);
    }

    public static HavingSqlBuilder havingLt(Boolean predicate, String column, Object param) {
        if (predicate) {
            Tuple2<String, Object[]> pt = SqlTemplateTools.parsePrecompileCondition(SqlNameTools.handleName(column), QueryRule.LT, param);
            return new HavingSqlBuilder(Boolean.TRUE, null, CommonConstant.EMPTY_OBJECT_ARRAY, pt.t1, pt.t2);
        }
        return new HavingSqlBuilder(Boolean.FALSE, null, CommonConstant.EMPTY_OBJECT_ARRAY);
    }

    public static HavingSqlBuilder havingLt(Boolean predicate, String column, Supplier<Object> param) {
        if (predicate) {
            Tuple2<String, Object[]> pt = SqlTemplateTools.parsePrecompileCondition(SqlNameTools.handleName(column), QueryRule.LT, param.get());
            return new HavingSqlBuilder(Boolean.TRUE, null, CommonConstant.EMPTY_OBJECT_ARRAY, pt.t1, pt.t2);
        }
        return new HavingSqlBuilder(Boolean.FALSE, null, CommonConstant.EMPTY_OBJECT_ARRAY);
    }

    public static <P> HavingSqlBuilder havingLt(LambdaFunction<P, ?> lambdaFunction, Object param) {
        return havingLt(LambdaTools.getColumnName(lambdaFunction), param);
    }

    public static <P> HavingSqlBuilder havingLt(Boolean predicate, LambdaFunction<P, ?> lambdaFunction, Object param) {
        if (Boolean.TRUE.equals(predicate)) {
            return havingLt(LambdaTools.getColumnName(lambdaFunction), param);
        }
        return new HavingSqlBuilder(Boolean.FALSE, null, CommonConstant.EMPTY_OBJECT_ARRAY);
    }

    public static <P> HavingSqlBuilder havingLt(Boolean predicate, LambdaFunction<P, ?> lambdaFunction, Supplier<Object> param) {
        if (Boolean.TRUE.equals(predicate)) {
            return havingLt(LambdaTools.getColumnName(lambdaFunction), param.get());
        }
        return new HavingSqlBuilder(Boolean.FALSE, null, CommonConstant.EMPTY_OBJECT_ARRAY);
    }

    public static HavingSqlBuilder havingLe(String column, Object param) {
        return having(Boolean.TRUE, column, QueryRule.LE, param);
    }

    public static HavingSqlBuilder havingLe(Boolean predicate, String column, Object param) {
        if (predicate) {
            Tuple2<String, Object[]> pt = SqlTemplateTools.parsePrecompileCondition(SqlNameTools.handleName(column), QueryRule.LE, param);
            return new HavingSqlBuilder(Boolean.TRUE, null, CommonConstant.EMPTY_OBJECT_ARRAY, pt.t1, pt.t2);
        }
        return new HavingSqlBuilder(Boolean.FALSE, null, CommonConstant.EMPTY_OBJECT_ARRAY);
    }

    public static HavingSqlBuilder havingLe(Boolean predicate, String column, Supplier<Object> param) {
        if (predicate) {
            Tuple2<String, Object[]> pt = SqlTemplateTools.parsePrecompileCondition(SqlNameTools.handleName(column), QueryRule.LE, param.get());
            return new HavingSqlBuilder(Boolean.TRUE, null, CommonConstant.EMPTY_OBJECT_ARRAY, pt.t1, pt.t2);
        }
        return new HavingSqlBuilder(Boolean.FALSE, null, CommonConstant.EMPTY_OBJECT_ARRAY);
    }

    public static <P> HavingSqlBuilder havingLe(LambdaFunction<P, ?> lambdaFunction, Object param) {
        return havingLe(LambdaTools.getColumnName(lambdaFunction), param);
    }

    public static <P> HavingSqlBuilder havingLe(Boolean predicate, LambdaFunction<P, ?> lambdaFunction, Object param) {
        if (Boolean.TRUE.equals(predicate)) {
            return havingLe(LambdaTools.getColumnName(lambdaFunction), param);
        }
        return new HavingSqlBuilder(Boolean.FALSE, null, CommonConstant.EMPTY_OBJECT_ARRAY);
    }

    public static <P> HavingSqlBuilder havingLe(Boolean predicate, LambdaFunction<P, ?> lambdaFunction, Supplier<Object> param) {
        if (Boolean.TRUE.equals(predicate)) {
            return havingLe(LambdaTools.getColumnName(lambdaFunction), param.get());
        }
        return new HavingSqlBuilder(Boolean.FALSE, null, CommonConstant.EMPTY_OBJECT_ARRAY);
    }

    public static HavingSqlBuilder havingLike(String column, Object param) {
        return having(Boolean.TRUE, column, QueryRule.LIKE, param);
    }

    public static HavingSqlBuilder havingLike(Boolean predicate, String column, Object param) {
        if (predicate) {
            Tuple2<String, Object[]> pt = SqlTemplateTools.parsePrecompileCondition(SqlNameTools.handleName(column), QueryRule.LIKE, param);
            return new HavingSqlBuilder(Boolean.TRUE, null, CommonConstant.EMPTY_OBJECT_ARRAY, pt.t1, pt.t2);
        }
        return new HavingSqlBuilder(Boolean.FALSE, null, CommonConstant.EMPTY_OBJECT_ARRAY);
    }

    public static HavingSqlBuilder havingLike(Boolean predicate, String column, Supplier<Object> param) {
        if (predicate) {
            Tuple2<String, Object[]> pt = SqlTemplateTools.parsePrecompileCondition(SqlNameTools.handleName(column), QueryRule.LIKE, param.get());
            return new HavingSqlBuilder(Boolean.TRUE, null, CommonConstant.EMPTY_OBJECT_ARRAY, pt.t1, pt.t2);
        }
        return new HavingSqlBuilder(Boolean.FALSE, null, CommonConstant.EMPTY_OBJECT_ARRAY);
    }

    public static <P> HavingSqlBuilder havingLike(LambdaFunction<P, ?> lambdaFunction, Object param) {
        return havingLike(LambdaTools.getColumnName(lambdaFunction), param);
    }

    public static <P> HavingSqlBuilder havingLike(Boolean predicate, LambdaFunction<P, ?> lambdaFunction, Object param) {
        if (Boolean.TRUE.equals(predicate)) {
            return havingLike(LambdaTools.getColumnName(lambdaFunction), param);
        }
        return new HavingSqlBuilder(Boolean.FALSE, null, CommonConstant.EMPTY_OBJECT_ARRAY);
    }

    public static <P> HavingSqlBuilder havingLike(Boolean predicate, LambdaFunction<P, ?> lambdaFunction, Supplier<Object> param) {
        if (Boolean.TRUE.equals(predicate)) {
            return havingLike(LambdaTools.getColumnName(lambdaFunction), param.get());
        }
        return new HavingSqlBuilder(Boolean.FALSE, null, CommonConstant.EMPTY_OBJECT_ARRAY);
    }

    public static HavingSqlBuilder havingNotLike(String column, Object param) {
        return having(Boolean.TRUE, column, QueryRule.NOT_LIKE, param);
    }

    public static HavingSqlBuilder havingNotLike(Boolean predicate, String column, Object param) {
        if (predicate) {
            Tuple2<String, Object[]> pt = SqlTemplateTools.parsePrecompileCondition(SqlNameTools.handleName(column), QueryRule.NOT_LIKE, param);
            return new HavingSqlBuilder(Boolean.TRUE, null, CommonConstant.EMPTY_OBJECT_ARRAY, pt.t1, pt.t2);
        }
        return new HavingSqlBuilder(Boolean.FALSE, null, CommonConstant.EMPTY_OBJECT_ARRAY);
    }

    public static HavingSqlBuilder havingNotLike(Boolean predicate, String column, Supplier<Object> param) {
        if (predicate) {
            Tuple2<String, Object[]> pt = SqlTemplateTools.parsePrecompileCondition(SqlNameTools.handleName(column), QueryRule.NOT_LIKE, param.get());
            return new HavingSqlBuilder(Boolean.TRUE, null, CommonConstant.EMPTY_OBJECT_ARRAY, pt.t1, pt.t2);
        }
        return new HavingSqlBuilder(Boolean.FALSE, null, CommonConstant.EMPTY_OBJECT_ARRAY);
    }

    public static <P> HavingSqlBuilder havingNotLike(LambdaFunction<P, ?> lambdaFunction, Object param) {
        return havingNotLike(LambdaTools.getColumnName(lambdaFunction), param);
    }

    public static <P> HavingSqlBuilder havingNotLike(Boolean predicate, LambdaFunction<P, ?> lambdaFunction, Object param) {
        if (Boolean.TRUE.equals(predicate)) {
            return havingNotLike(LambdaTools.getColumnName(lambdaFunction), param);
        }
        return new HavingSqlBuilder(Boolean.FALSE, null, CommonConstant.EMPTY_OBJECT_ARRAY);
    }

    public static <P> HavingSqlBuilder havingNotLike(Boolean predicate, LambdaFunction<P, ?> lambdaFunction, Supplier<Object> param) {
        if (Boolean.TRUE.equals(predicate)) {
            return havingNotLike(LambdaTools.getColumnName(lambdaFunction), param.get());
        }
        return new HavingSqlBuilder(Boolean.FALSE, null, CommonConstant.EMPTY_OBJECT_ARRAY);
    }

    public static HavingSqlBuilder havingLLike(String column, Object param) {
        return having(Boolean.TRUE, column, QueryRule.LEFT_LIKE, param);
    }

    public static HavingSqlBuilder havingLLike(Boolean predicate, String column, Object param) {
        if (predicate) {
            Tuple2<String, Object[]> pt = SqlTemplateTools.parsePrecompileCondition(SqlNameTools.handleName(column), QueryRule.LEFT_LIKE, param);
            return new HavingSqlBuilder(Boolean.TRUE, null, CommonConstant.EMPTY_OBJECT_ARRAY, pt.t1, pt.t2);
        }
        return new HavingSqlBuilder(Boolean.FALSE, null, CommonConstant.EMPTY_OBJECT_ARRAY);
    }

    public static HavingSqlBuilder havingLLike(Boolean predicate, String column, Supplier<Object> param) {
        if (predicate) {
            Tuple2<String, Object[]> pt = SqlTemplateTools.parsePrecompileCondition(SqlNameTools.handleName(column), QueryRule.LEFT_LIKE, param.get());
            return new HavingSqlBuilder(Boolean.TRUE, null, CommonConstant.EMPTY_OBJECT_ARRAY, pt.t1, pt.t2);
        }
        return new HavingSqlBuilder(Boolean.FALSE, null, CommonConstant.EMPTY_OBJECT_ARRAY);
    }

    public static <P> HavingSqlBuilder havingLLike(LambdaFunction<P, ?> lambdaFunction, Object param) {
        return havingLLike(LambdaTools.getColumnName(lambdaFunction), param);
    }

    public static <P> HavingSqlBuilder havingLLike(Boolean predicate, LambdaFunction<P, ?> lambdaFunction, Object param) {
        if (Boolean.TRUE.equals(predicate)) {
            return havingLLike(LambdaTools.getColumnName(lambdaFunction), param);
        }
        return new HavingSqlBuilder(Boolean.FALSE, null, CommonConstant.EMPTY_OBJECT_ARRAY);
    }

    public static <P> HavingSqlBuilder havingLLike(Boolean predicate, LambdaFunction<P, ?> lambdaFunction, Supplier<Object> param) {
        if (Boolean.TRUE.equals(predicate)) {
            return havingLLike(LambdaTools.getColumnName(lambdaFunction), param.get());
        }
        return new HavingSqlBuilder(Boolean.FALSE, null, CommonConstant.EMPTY_OBJECT_ARRAY);
    }

    public static HavingSqlBuilder havingNotLLike(String column, Object param) {
        return having(Boolean.TRUE, column, QueryRule.NOT_LEFT_LIKE, param);
    }

    public static HavingSqlBuilder havingNotLLike(Boolean predicate, String column, Object param) {
        if (predicate) {
            Tuple2<String, Object[]> pt = SqlTemplateTools.parsePrecompileCondition(SqlNameTools.handleName(column), QueryRule.NOT_LEFT_LIKE, param);
            return new HavingSqlBuilder(Boolean.TRUE, null, CommonConstant.EMPTY_OBJECT_ARRAY, pt.t1, pt.t2);
        }
        return new HavingSqlBuilder(Boolean.FALSE, null, CommonConstant.EMPTY_OBJECT_ARRAY);
    }

    public static HavingSqlBuilder havingNotLLike(Boolean predicate, String column, Supplier<Object> param) {
        if (predicate) {
            Tuple2<String, Object[]> pt = SqlTemplateTools.parsePrecompileCondition(SqlNameTools.handleName(column), QueryRule.NOT_LEFT_LIKE, param.get());
            return new HavingSqlBuilder(Boolean.TRUE, null, CommonConstant.EMPTY_OBJECT_ARRAY, pt.t1, pt.t2);
        }
        return new HavingSqlBuilder(Boolean.FALSE, null, CommonConstant.EMPTY_OBJECT_ARRAY);
    }

    public static <P> HavingSqlBuilder havingNotLLike(LambdaFunction<P, ?> lambdaFunction, Object param) {
        return havingNotLLike(LambdaTools.getColumnName(lambdaFunction), param);
    }

    public static <P> HavingSqlBuilder havingNotLLike(Boolean predicate, LambdaFunction<P, ?> lambdaFunction, Object param) {
        if (Boolean.TRUE.equals(predicate)) {
            return havingNotLLike(LambdaTools.getColumnName(lambdaFunction), param);
        }
        return new HavingSqlBuilder(Boolean.FALSE, null, CommonConstant.EMPTY_OBJECT_ARRAY);
    }

    public static <P> HavingSqlBuilder havingNotLLike(Boolean predicate, LambdaFunction<P, ?> lambdaFunction, Supplier<Object> param) {
        if (Boolean.TRUE.equals(predicate)) {
            return havingNotLLike(LambdaTools.getColumnName(lambdaFunction), param.get());
        }
        return new HavingSqlBuilder(Boolean.FALSE, null, CommonConstant.EMPTY_OBJECT_ARRAY);
    }

    public static HavingSqlBuilder havingRLike(String column, Object param) {
        return having(Boolean.TRUE, column, QueryRule.RIGHT_LIKE, param);
    }

    public static HavingSqlBuilder havingRLike(Boolean predicate, String column, Object param) {
        if (predicate) {
            Tuple2<String, Object[]> pt = SqlTemplateTools.parsePrecompileCondition(SqlNameTools.handleName(column), QueryRule.RIGHT_LIKE, param);
            return new HavingSqlBuilder(Boolean.TRUE, null, CommonConstant.EMPTY_OBJECT_ARRAY, pt.t1, pt.t2);
        }
        return new HavingSqlBuilder(Boolean.FALSE, null, CommonConstant.EMPTY_OBJECT_ARRAY);
    }

    public static HavingSqlBuilder havingRLike(Boolean predicate, String column, Supplier<Object> param) {
        if (predicate) {
            Tuple2<String, Object[]> pt = SqlTemplateTools.parsePrecompileCondition(SqlNameTools.handleName(column), QueryRule.RIGHT_LIKE, param.get());
            return new HavingSqlBuilder(Boolean.TRUE, null, CommonConstant.EMPTY_OBJECT_ARRAY, pt.t1, pt.t2);
        }
        return new HavingSqlBuilder(Boolean.FALSE, null, CommonConstant.EMPTY_OBJECT_ARRAY);
    }

    public static <P> HavingSqlBuilder havingRLike(LambdaFunction<P, ?> lambdaFunction, Object param) {
        return havingRLike(LambdaTools.getColumnName(lambdaFunction), param);
    }

    public static <P> HavingSqlBuilder havingRLike(Boolean predicate, LambdaFunction<P, ?> lambdaFunction, Object param) {
        if (Boolean.TRUE.equals(predicate)) {
            return havingRLike(LambdaTools.getColumnName(lambdaFunction), param);
        }
        return new HavingSqlBuilder(Boolean.FALSE, null, CommonConstant.EMPTY_OBJECT_ARRAY);
    }

    public static <P> HavingSqlBuilder havingRLike(Boolean predicate, LambdaFunction<P, ?> lambdaFunction, Supplier<Object> param) {
        if (Boolean.TRUE.equals(predicate)) {
            return havingRLike(LambdaTools.getColumnName(lambdaFunction), param.get());
        }
        return new HavingSqlBuilder(Boolean.FALSE, null, CommonConstant.EMPTY_OBJECT_ARRAY);
    }

    public static HavingSqlBuilder havingNotRLike(String column, Object param) {
        return having(Boolean.TRUE, column, QueryRule.NOT_RIGHT_LIKE, param);
    }

    public static HavingSqlBuilder havingNotRLike(Boolean predicate, String column, Object param) {
        if (predicate) {
            Tuple2<String, Object[]> pt = SqlTemplateTools.parsePrecompileCondition(SqlNameTools.handleName(column), QueryRule.NOT_RIGHT_LIKE, param);
            return new HavingSqlBuilder(Boolean.TRUE, null, CommonConstant.EMPTY_OBJECT_ARRAY, pt.t1, pt.t2);
        }
        return new HavingSqlBuilder(Boolean.FALSE, null, CommonConstant.EMPTY_OBJECT_ARRAY);
    }

    public static HavingSqlBuilder havingNotRLike(Boolean predicate, String column, Supplier<Object> param) {
        if (predicate) {
            Tuple2<String, Object[]> pt = SqlTemplateTools.parsePrecompileCondition(SqlNameTools.handleName(column), QueryRule.NOT_RIGHT_LIKE, param.get());
            return new HavingSqlBuilder(Boolean.TRUE, null, CommonConstant.EMPTY_OBJECT_ARRAY, pt.t1, pt.t2);
        }
        return new HavingSqlBuilder(Boolean.FALSE, null, CommonConstant.EMPTY_OBJECT_ARRAY);
    }

    public static <P> HavingSqlBuilder havingNotRLike(LambdaFunction<P, ?> lambdaFunction, Object param) {
        return havingNotRLike(LambdaTools.getColumnName(lambdaFunction), param);
    }

    public static <P> HavingSqlBuilder havingNotRLike(Boolean predicate, LambdaFunction<P, ?> lambdaFunction, Object param) {
        if (Boolean.TRUE.equals(predicate)) {
            return havingNotRLike(LambdaTools.getColumnName(lambdaFunction), param);
        }
        return new HavingSqlBuilder(Boolean.FALSE, null, CommonConstant.EMPTY_OBJECT_ARRAY);
    }

    public static <P> HavingSqlBuilder havingNotRLike(Boolean predicate, LambdaFunction<P, ?> lambdaFunction, Supplier<Object> param) {
        if (Boolean.TRUE.equals(predicate)) {
            return havingNotRLike(LambdaTools.getColumnName(lambdaFunction), param.get());
        }
        return new HavingSqlBuilder(Boolean.FALSE, null, CommonConstant.EMPTY_OBJECT_ARRAY);
    }

    public static HavingSqlBuilder havingIn(String column, Object... params) {
        return having(Boolean.TRUE, column, QueryRule.IN, params);
    }

    public static HavingSqlBuilder havingIn(Boolean predicate, String column, Object... params) {
        if (predicate) {
            Tuple2<String, Object[]> pt = SqlTemplateTools.parsePrecompileCondition(SqlNameTools.handleName(column), QueryRule.IN, params);
            return new HavingSqlBuilder(Boolean.TRUE, null, CommonConstant.EMPTY_OBJECT_ARRAY, pt.t1, pt.t2);
        }
        return new HavingSqlBuilder(Boolean.FALSE, null, CommonConstant.EMPTY_OBJECT_ARRAY);
    }

    public static HavingSqlBuilder havingIn(Boolean predicate, String column, Supplier<Object[]> params) {
        if (predicate) {
            Tuple2<String, Object[]> pt = SqlTemplateTools.parsePrecompileCondition(SqlNameTools.handleName(column), QueryRule.IN, params.get());
            return new HavingSqlBuilder(Boolean.TRUE, null, CommonConstant.EMPTY_OBJECT_ARRAY, pt.t1, pt.t2);
        }
        return new HavingSqlBuilder(Boolean.FALSE, null, CommonConstant.EMPTY_OBJECT_ARRAY);
    }

    public static <P> HavingSqlBuilder havingIn(LambdaFunction<P, ?> lambdaFunction, Object... params) {
        return havingIn(LambdaTools.getColumnName(lambdaFunction), params);
    }

    public static <P> HavingSqlBuilder havingIn(Boolean predicate, LambdaFunction<P, ?> lambdaFunction, Object... params) {
        if (Boolean.TRUE.equals(predicate)) {
            return havingIn(LambdaTools.getColumnName(lambdaFunction), params);
        }
        return new HavingSqlBuilder(Boolean.FALSE, null, CommonConstant.EMPTY_OBJECT_ARRAY);
    }

    public static <P> HavingSqlBuilder havingIn(Boolean predicate, LambdaFunction<P, ?> lambdaFunction, Supplier<Object[]> params) {
        if (Boolean.TRUE.equals(predicate)) {
            return havingIn(LambdaTools.getColumnName(lambdaFunction), params.get());
        }
        return new HavingSqlBuilder(Boolean.FALSE, null, CommonConstant.EMPTY_OBJECT_ARRAY);
    }

    public static HavingSqlBuilder havingNotIn(String column, Object... params) {
        return having(Boolean.TRUE, column, QueryRule.NOT_IN, params);
    }

    public static HavingSqlBuilder havingNotIn(Boolean predicate, String column, Object... params) {
        if (predicate) {
            Tuple2<String, Object[]> pt = SqlTemplateTools.parsePrecompileCondition(SqlNameTools.handleName(column), QueryRule.NOT_IN, params);
            return new HavingSqlBuilder(Boolean.TRUE, null, CommonConstant.EMPTY_OBJECT_ARRAY, pt.t1, pt.t2);
        }
        return new HavingSqlBuilder(Boolean.FALSE, null, CommonConstant.EMPTY_OBJECT_ARRAY);
    }

    public static HavingSqlBuilder havingNotIn(Boolean predicate, String column, Supplier<Object[]> params) {
        if (predicate) {
            Tuple2<String, Object[]> pt = SqlTemplateTools.parsePrecompileCondition(SqlNameTools.handleName(column), QueryRule.NOT_IN, params.get());
            return new HavingSqlBuilder(Boolean.TRUE, null, CommonConstant.EMPTY_OBJECT_ARRAY, pt.t1, pt.t2);
        }
        return new HavingSqlBuilder(Boolean.FALSE, null, CommonConstant.EMPTY_OBJECT_ARRAY);
    }

    public static <P> HavingSqlBuilder havingNotIn(LambdaFunction<P, ?> lambdaFunction, Object... params) {
        return havingNotIn(LambdaTools.getColumnName(lambdaFunction), params);
    }

    public static <P> HavingSqlBuilder havingNotIn(Boolean predicate, LambdaFunction<P, ?> lambdaFunction, Object... params) {
        if (Boolean.TRUE.equals(predicate)) {
            return havingNotIn(LambdaTools.getColumnName(lambdaFunction), params);
        }
        return new HavingSqlBuilder(Boolean.FALSE, null, CommonConstant.EMPTY_OBJECT_ARRAY);
    }

    public static <P> HavingSqlBuilder havingNotIn(Boolean predicate, LambdaFunction<P, ?> lambdaFunction, Supplier<Object[]> params) {
        if (Boolean.TRUE.equals(predicate)) {
            return havingNotIn(LambdaTools.getColumnName(lambdaFunction), params.get());
        }
        return new HavingSqlBuilder(Boolean.FALSE, null, CommonConstant.EMPTY_OBJECT_ARRAY);
    }

    public static HavingSqlBuilder havingBetween(String column, Object... params) {
        return having(Boolean.TRUE, column, QueryRule.BETWEEN_AND, params);
    }

    public static HavingSqlBuilder havingBetween(Boolean predicate, String column, Object... params) {
        if (predicate) {
            Tuple2<String, Object[]> pt = SqlTemplateTools.parsePrecompileCondition(SqlNameTools.handleName(column), QueryRule.BETWEEN_AND, params);
            return new HavingSqlBuilder(Boolean.TRUE, null, CommonConstant.EMPTY_OBJECT_ARRAY, pt.t1, pt.t2);
        }
        return new HavingSqlBuilder(Boolean.FALSE, null, CommonConstant.EMPTY_OBJECT_ARRAY);
    }

    public static HavingSqlBuilder havingBetween(Boolean predicate, String column, Supplier<Object> param1, Supplier<Object> param2) {
        if (predicate) {
            Tuple2<String, Object[]> pt = SqlTemplateTools.parsePrecompileCondition(SqlNameTools.handleName(column), QueryRule.BETWEEN_AND, param1.get(), param2.get());
            return new HavingSqlBuilder(Boolean.TRUE, null, CommonConstant.EMPTY_OBJECT_ARRAY, pt.t1, pt.t2);
        }
        return new HavingSqlBuilder(Boolean.FALSE, null, CommonConstant.EMPTY_OBJECT_ARRAY);
    }

    public static HavingSqlBuilder havingBetween(Boolean predicate, String column, Supplier<Object[]> params) {
        if (predicate) {
            Tuple2<String, Object[]> pt = SqlTemplateTools.parsePrecompileCondition(SqlNameTools.handleName(column), QueryRule.BETWEEN_AND, params.get());
            return new HavingSqlBuilder(Boolean.TRUE, null, CommonConstant.EMPTY_OBJECT_ARRAY, pt.t1, pt.t2);
        }
        return new HavingSqlBuilder(Boolean.FALSE, null, CommonConstant.EMPTY_OBJECT_ARRAY);
    }

    public static <P> HavingSqlBuilder havingBetween(LambdaFunction<P, ?> lambdaFunction, Object... params) {
        return havingBetween(LambdaTools.getColumnName(lambdaFunction), params);
    }

    public static <P> HavingSqlBuilder havingBetween(Boolean predicate, LambdaFunction<P, ?> lambdaFunction, Object... params) {
        if (Boolean.TRUE.equals(predicate)) {
            return havingBetween(LambdaTools.getColumnName(lambdaFunction), params);
        }
        return new HavingSqlBuilder(Boolean.FALSE, null, CommonConstant.EMPTY_OBJECT_ARRAY);
    }

    public static <P> HavingSqlBuilder havingBetween(Boolean predicate, LambdaFunction<P, ?> lambdaFunction, Supplier<Object> param1, Supplier<Object> param2) {
        if (Boolean.TRUE.equals(predicate)) {
            return havingBetween(LambdaTools.getColumnName(lambdaFunction), param1.get(), param2.get());
        }
        return new HavingSqlBuilder(Boolean.FALSE, null, CommonConstant.EMPTY_OBJECT_ARRAY);
    }

    public static <P> HavingSqlBuilder havingBetween(Boolean predicate, LambdaFunction<P, ?> lambdaFunction, Supplier<Object[]> params) {
        if (Boolean.TRUE.equals(predicate)) {
            return havingBetween(LambdaTools.getColumnName(lambdaFunction), params.get());
        }
        return new HavingSqlBuilder(Boolean.FALSE, null, CommonConstant.EMPTY_OBJECT_ARRAY);
    }

    public static HavingSqlBuilder havingNotBetween(String column, Object... params) {
        return having(Boolean.TRUE, column, QueryRule.NOT_BETWEEN_AND, params);
    }

    public static HavingSqlBuilder havingNotBetween(Boolean predicate, String column, Object... params) {
        if (predicate) {
            Tuple2<String, Object[]> pt = SqlTemplateTools.parsePrecompileCondition(SqlNameTools.handleName(column), QueryRule.NOT_BETWEEN_AND, params);
            return new HavingSqlBuilder(Boolean.TRUE, null, CommonConstant.EMPTY_OBJECT_ARRAY, pt.t1, pt.t2);
        }
        return new HavingSqlBuilder(Boolean.FALSE, null, CommonConstant.EMPTY_OBJECT_ARRAY);
    }

    public static HavingSqlBuilder havingNotBetween(Boolean predicate, String column, Supplier<Object> param1, Supplier<Object> param2) {
        if (predicate) {
            Tuple2<String, Object[]> pt = SqlTemplateTools.parsePrecompileCondition(SqlNameTools.handleName(column), QueryRule.NOT_BETWEEN_AND, param1.get(), param2.get());
            return new HavingSqlBuilder(Boolean.TRUE, null, CommonConstant.EMPTY_OBJECT_ARRAY, pt.t1, pt.t2);
        }
        return new HavingSqlBuilder(Boolean.FALSE, null, CommonConstant.EMPTY_OBJECT_ARRAY);
    }

    public static HavingSqlBuilder havingNotBetween(Boolean predicate, String column, Supplier<Object[]> params) {
        if (predicate) {
            Tuple2<String, Object[]> pt = SqlTemplateTools.parsePrecompileCondition(SqlNameTools.handleName(column), QueryRule.NOT_BETWEEN_AND, params.get());
            return new HavingSqlBuilder(Boolean.TRUE, null, CommonConstant.EMPTY_OBJECT_ARRAY, pt.t1, pt.t2);
        }
        return new HavingSqlBuilder(Boolean.FALSE, null, CommonConstant.EMPTY_OBJECT_ARRAY);
    }

    public static <P> HavingSqlBuilder havingNotBetween(LambdaFunction<P, ?> lambdaFunction, Object... params) {
        return havingNotBetween(LambdaTools.getColumnName(lambdaFunction), params);
    }

    public static <P> HavingSqlBuilder havingNotBetween(Boolean predicate, LambdaFunction<P, ?> lambdaFunction, Object... params) {
        if (Boolean.TRUE.equals(predicate)) {
            return havingNotBetween(LambdaTools.getColumnName(lambdaFunction), params);
        }
        return new HavingSqlBuilder(Boolean.FALSE, null, CommonConstant.EMPTY_OBJECT_ARRAY);
    }

    public static <P> HavingSqlBuilder havingNotBetween(Boolean predicate, LambdaFunction<P, ?> lambdaFunction, Supplier<Object> param1, Supplier<Object> param2) {
        if (Boolean.TRUE.equals(predicate)) {
            return havingNotBetween(LambdaTools.getColumnName(lambdaFunction), param1.get(), param2.get());
        }
        return new HavingSqlBuilder(Boolean.FALSE, null, CommonConstant.EMPTY_OBJECT_ARRAY);
    }

    public static <P> HavingSqlBuilder havingNotBetween(Boolean predicate, LambdaFunction<P, ?> lambdaFunction, Supplier<Object[]> params) {
        if (Boolean.TRUE.equals(predicate)) {
            return havingNotBetween(LambdaTools.getColumnName(lambdaFunction), params.get());
        }
        return new HavingSqlBuilder(Boolean.FALSE, null, CommonConstant.EMPTY_OBJECT_ARRAY);
    }

    public static HavingSqlBuilder havingNull(String column) {
        return having(Boolean.TRUE, column, QueryRule.IS_NULL);
    }

    public static HavingSqlBuilder havingNull(Boolean predicate, String column) {
        if (predicate) {
            Tuple2<String, Object[]> pt = SqlTemplateTools.parsePrecompileCondition(SqlNameTools.handleName(column), QueryRule.IS_NULL);
            return new HavingSqlBuilder(Boolean.TRUE, null, CommonConstant.EMPTY_OBJECT_ARRAY, pt.t1, pt.t2);
        }
        return new HavingSqlBuilder(Boolean.FALSE, null, CommonConstant.EMPTY_OBJECT_ARRAY);
    }

    public static <P> HavingSqlBuilder havingNull(LambdaFunction<P, ?> lambdaFunction) {
        return havingNull(LambdaTools.getColumnName(lambdaFunction));
    }

    public static <P> HavingSqlBuilder havingNull(Boolean predicate, LambdaFunction<P, ?> lambdaFunction) {
        if (Boolean.TRUE.equals(predicate)) {
            return havingNull(LambdaTools.getColumnName(lambdaFunction));
        }
        return new HavingSqlBuilder(Boolean.FALSE, null, CommonConstant.EMPTY_OBJECT_ARRAY);
    }

    public static HavingSqlBuilder havingIsNotNull(String column) {
        return having(Boolean.TRUE, column, QueryRule.NOT_NULL);
    }

    public static HavingSqlBuilder havingIsNotNull(Boolean predicate, String column) {
        if (predicate) {
            Tuple2<String, Object[]> pt = SqlTemplateTools.parsePrecompileCondition(SqlNameTools.handleName(column), QueryRule.NOT_NULL);
            return new HavingSqlBuilder(Boolean.TRUE, null, CommonConstant.EMPTY_OBJECT_ARRAY, pt.t1, pt.t2);
        }
        return new HavingSqlBuilder(Boolean.FALSE, null, CommonConstant.EMPTY_OBJECT_ARRAY);
    }

    public static <P> HavingSqlBuilder havingIsNotNull(LambdaFunction<P, ?> lambdaFunction) {
        return havingIsNotNull(LambdaTools.getColumnName(lambdaFunction));
    }

    public static <P> HavingSqlBuilder havingIsNotNull(Boolean predicate, LambdaFunction<P, ?> lambdaFunction) {
        if (Boolean.TRUE.equals(predicate)) {
            return havingIsNotNull(LambdaTools.getColumnName(lambdaFunction));
        }
        return new HavingSqlBuilder(Boolean.FALSE, null, CommonConstant.EMPTY_OBJECT_ARRAY);
    }

    public static HavingSqlBuilder havingExists(Object... sqlOrBuilder) {
        return having(Boolean.TRUE, CommonConstant.EMPTY_STRING, QueryRule.EXISTS, sqlOrBuilder);
    }

    public static HavingSqlBuilder havingExists(Boolean predicate, Object... sqlOrBuilder) {
        if (predicate) {
            Tuple2<String, Object[]> pt = SqlTemplateTools.parsePrecompileCondition(null, QueryRule.EXISTS, sqlOrBuilder);
            return new HavingSqlBuilder(Boolean.TRUE, null, CommonConstant.EMPTY_OBJECT_ARRAY, pt.t1, pt.t2);
        }
        return new HavingSqlBuilder(Boolean.FALSE, null, CommonConstant.EMPTY_OBJECT_ARRAY);
    }

    public static HavingSqlBuilder havingExists(Boolean predicate, Supplier<Object> sqlOrBuilder) {
        if (predicate) {
            Tuple2<String, Object[]> pt = SqlTemplateTools.parsePrecompileCondition(null, QueryRule.EXISTS, sqlOrBuilder.get());
            return new HavingSqlBuilder(Boolean.TRUE, null, CommonConstant.EMPTY_OBJECT_ARRAY, pt.t1, pt.t2);
        }
        return new HavingSqlBuilder(Boolean.FALSE, null, CommonConstant.EMPTY_OBJECT_ARRAY);
    }

    public static HavingSqlBuilder havingNotExists(Object... sqlOrBuilder) {
        return having(Boolean.TRUE, CommonConstant.EMPTY_STRING, QueryRule.NOT_EXISTS, sqlOrBuilder);
    }

    public static HavingSqlBuilder havingNotExists(Boolean predicate, Object... sqlOrBuilder) {
        if (predicate) {
            Tuple2<String, Object[]> pt = SqlTemplateTools.parsePrecompileCondition(null, QueryRule.NOT_EXISTS, sqlOrBuilder);
            return new HavingSqlBuilder(Boolean.TRUE, null, CommonConstant.EMPTY_OBJECT_ARRAY, pt.t1, pt.t2);
        }
        return new HavingSqlBuilder(Boolean.FALSE, null, CommonConstant.EMPTY_OBJECT_ARRAY);
    }

    public static HavingSqlBuilder havingNotExists(Boolean predicate, Supplier<Object> sqlOrBuilder) {
        if (predicate) {
            Tuple2<String, Object[]> pt = SqlTemplateTools.parsePrecompileCondition(null, QueryRule.NOT_EXISTS, sqlOrBuilder.get());
            return new HavingSqlBuilder(Boolean.TRUE, null, CommonConstant.EMPTY_OBJECT_ARRAY, pt.t1, pt.t2);
        }
        return new HavingSqlBuilder(Boolean.FALSE, null, CommonConstant.EMPTY_OBJECT_ARRAY);
    }

}
