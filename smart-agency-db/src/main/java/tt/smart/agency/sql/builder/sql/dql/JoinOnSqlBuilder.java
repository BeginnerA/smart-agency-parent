package tt.smart.agency.sql.builder.sql.dql;

import tt.smart.agency.sql.builder.sql.SqlBuilderRoute;

import java.util.function.Supplier;

/**
 * <p>
 * JOIN 链接构造器
 * </p>
 *
 * @author MC_Yang
 * @version V1.0
 **/
public class JoinOnSqlBuilder extends ConditionSqlBuilder<JoinOnSqlBuilder> implements SqlBuilderRoute, WhereSqlBuilderRoute,
        LimitSqlBuilderRoute, OrderSqlBuilderRoute, UnionSqlBuilderRoute, GroupSqlBuilderRoute, JoinSqlBuilderRoute {

    protected JoinOnSqlBuilder(Boolean predicate, String prefix, Object[] precompileArgs, JoinOnSqlBuilder b) {
        super(predicate, prefix, precompileArgs, b);
        sign = "ON";
    }

    protected JoinOnSqlBuilder(Boolean predicate, String prefix, Object[] precompileArgs) {
        super(predicate, prefix, precompileArgs);
        sign = "ON";
    }

    protected JoinOnSqlBuilder(Boolean predicate, String prefix, Object[] precompileArgs, String condition, Object... values) {
        super(predicate, prefix, precompileArgs, condition, values);
        sign = "ON";
    }

    protected JoinOnSqlBuilder(Boolean predicate, String prefix, Object[] precompileArgs, String condition, Supplier<Object[]> supplier) {
        super(predicate, prefix, precompileArgs, condition, supplier);
        sign = "ON";
    }

    protected JoinOnSqlBuilder(Boolean predicate, String prefix, Object[] precompileArgs, Object... queryCriteria) {
        super(predicate, prefix, precompileArgs, queryCriteria);
        sign = "ON";
    }

}
