package tt.smart.agency.sql.builder.sql.dql;

import tt.smart.agency.sql.builder.sql.SqlBuilderRoute;

import java.util.function.Supplier;

/**
 * <p>
 * HAVING SQL 构造器
 * </p>
 *
 * @author MC_Yang
 * @version V1.0
 **/
public class HavingSqlBuilder extends ConditionSqlBuilder<HavingSqlBuilder> implements SqlBuilderRoute,
        OrderSqlBuilderRoute, LimitSqlBuilderRoute, UnionSqlBuilderRoute {

    protected HavingSqlBuilder(Boolean predicate, String prefix, Object[] precompileArgs, HavingSqlBuilder b) {
        super(predicate, prefix, precompileArgs, b);
        sign = "HAVING";
    }

    protected HavingSqlBuilder(Boolean predicate, String prefix, Object[] precompileArgs) {
        super(predicate, prefix, precompileArgs);
        sign = "HAVING";
    }

    protected HavingSqlBuilder(Boolean predicate, String prefix, Object[] precompileArgs, String condition, Object... values) {
        super(predicate, prefix, precompileArgs, condition, values);
        sign = "HAVING";
    }

    protected HavingSqlBuilder(Boolean predicate, String prefix, Object[] precompileArgs, String condition, Supplier<Object[]> supplier) {
        super(predicate, prefix, precompileArgs, condition, supplier);
        sign = "HAVING";
    }

    protected HavingSqlBuilder(Boolean predicate, String prefix, Object[] precompileArgs, Object... queryCriteria) {
        super(predicate, prefix, precompileArgs, queryCriteria);
        sign = "HAVING";
    }

}
