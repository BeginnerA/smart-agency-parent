package tt.smart.agency.sql.builder.sql.dql;

import tt.smart.agency.sql.builder.sql.SqlBuilderRoute;

import java.util.function.Supplier;

/**
 * <p>
 * WHERE SQL 构造器
 * </p>
 *
 * @author MC_Yang
 * @version V1.0
 **/
public class WhereSqlBuilder extends ConditionSqlBuilder<WhereSqlBuilder> implements SqlBuilderRoute,
        GroupSqlBuilderRoute, OrderSqlBuilderRoute, LimitSqlBuilderRoute, UnionSqlBuilderRoute {

    protected WhereSqlBuilder(Boolean predicate, String prefix, Object[] precompileArgs, WhereSqlBuilder b) {
        super(predicate, prefix, precompileArgs, b);
    }

    protected WhereSqlBuilder(Boolean predicate, String prefix, Object[] precompileArgs) {
        super(predicate, prefix, precompileArgs);
    }

    protected WhereSqlBuilder(Boolean predicate, String prefix, Object[] precompileArgs, String condition, Object... values) {
        super(predicate, prefix, precompileArgs, condition, values);
    }

    protected WhereSqlBuilder(Boolean predicate, String prefix, Object[] precompileArgs, String condition, Supplier<Object[]> supplier) {
        super(predicate, prefix, precompileArgs, condition, supplier);
    }

    protected WhereSqlBuilder(Boolean predicate, String prefix, Object[] precompileArgs, Object... queryCriteria) {
        super(predicate, prefix, precompileArgs, queryCriteria);
    }

}
