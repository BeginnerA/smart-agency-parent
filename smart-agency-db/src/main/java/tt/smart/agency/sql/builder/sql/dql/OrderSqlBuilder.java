package tt.smart.agency.sql.builder.sql.dql;

import tt.smart.agency.sql.builder.sql.LambdaFunction;
import tt.smart.agency.sql.builder.sql.SqlBuilderRoute;
import tt.smart.agency.sql.constant.Order;
import tt.smart.agency.sql.tools.LambdaTools;
import tt.smart.agency.sql.tools.SqlNameTools;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * <p>
 * ORDER SQL 构造器
 * </p>
 *
 * @author MC_Yang
 * @version V1.0
 **/
public class OrderSqlBuilder implements SqlBuilderRoute, LimitSqlBuilderRoute, UnionSqlBuilderRoute {

    /**
     * 前缀
     */
    private final String prefix;
    /**
     * 排序
     */
    private final List<String> sorts;
    /**
     * 是否条件过滤
     */
    private boolean passPredicate;
    /**
     * 预编译参数
     */
    private Object[] precompileArgs;

    protected OrderSqlBuilder(String prefix, Object[] precompileArgs) {
        this.prefix = prefix;
        this.sorts = new ArrayList<>();
        this.passPredicate = true;
        this.precompileArgs = precompileArgs;
    }

    protected OrderSqlBuilder(String prefix, Object[] precompileArgs, String sort) {
        this(prefix, precompileArgs);
        addSort(sort);
    }

    protected OrderSqlBuilder(String prefix, Object[] precompileArgs, String column, Order order) {
        this(prefix, precompileArgs);
        addSort(column, order);
    }

    @SafeVarargs
    public final <P> OrderSqlBuilder addAsc(LambdaFunction<P, ?>... lambdaFunctions) {
        return addAsc(Arrays.stream(lambdaFunctions).map(LambdaTools::getColumnName).toArray(String[]::new));
    }

    public <P> OrderSqlBuilder addAsc(Boolean predicate, LambdaFunction<P, ?>... lambdaFunctions) {
        if (prefix == null) {
            passPredicate &= predicate;
        }
        if (Boolean.TRUE.equals(predicate)) {
            return addAsc(lambdaFunctions);
        }
        return this;
    }

    @SafeVarargs
    public final <P> OrderSqlBuilder addDesc(LambdaFunction<P, ?>... lambdaFunctions) {
        return addDesc(Arrays.stream(lambdaFunctions).map(LambdaTools::getColumnName).toArray(String[]::new));
    }

    public <P> OrderSqlBuilder addDesc(Boolean predicate, LambdaFunction<P, ?>... lambdaFunctions) {
        if (prefix == null) {
            passPredicate &= predicate;
        }
        if (Boolean.TRUE.equals(predicate)) {
            return addDesc(lambdaFunctions);
        }
        return this;
    }

    public OrderSqlBuilder addAsc(String... columns) {
        return addAsc(Boolean.TRUE, columns);
    }

    public OrderSqlBuilder addAsc(Boolean predicate, String... columns) {
        if (prefix == null) {
            passPredicate &= predicate;
        }
        if (Boolean.TRUE.equals(predicate) && passPredicate) {
            for (String column : columns) {
                addSort(column, Order.ASC);
            }
        }
        return this;
    }

    public OrderSqlBuilder addDesc(String... columns) {
        return addDesc(Boolean.TRUE, columns);
    }

    public OrderSqlBuilder addDesc(Boolean predicate, String... columns) {
        if (prefix == null) {
            passPredicate &= predicate;
        }
        if (Boolean.TRUE.equals(predicate) && passPredicate) {
            for (String column : columns) {
                addSort(column, Order.DESC);
            }
        }
        return this;
    }

    public <P> OrderSqlBuilder addSort(LambdaFunction<P, ?> lambdaFunction, Order order) {
        return addSort(LambdaTools.getColumnName(lambdaFunction), order);
    }

    public <P> OrderSqlBuilder addSort(Boolean predicate, LambdaFunction<P, ?> lambdaFunction, Order order) {
        if (prefix == null) {
            passPredicate &= predicate;
        }
        if (Boolean.TRUE.equals(predicate)) {
            return addSort(lambdaFunction, order);
        }
        return this;
    }

    public OrderSqlBuilder addSort(String column, Order order) {
        return addSort(Boolean.TRUE, column, order);
    }

    public OrderSqlBuilder addSort(Boolean predicate, String column, Order order) {
        if (prefix == null) {
            passPredicate &= predicate;
        }
        if (Boolean.TRUE.equals(predicate) && passPredicate) {
            sorts.add(SqlNameTools.handleName(column) + " " + order.toString());
        }
        return this;
    }

    public OrderSqlBuilder addSort(String sort) {
        return addSort(Boolean.TRUE, sort);
    }

    public OrderSqlBuilder addSort(Boolean predicate, String sort) {
        if (prefix == null) {
            passPredicate &= predicate;
        }
        if (Boolean.TRUE.equals(predicate) && passPredicate) {
            sorts.add(sort);
        }
        return this;
    }

    public OrderSqlBuilder addSort(OrderSqlBuilder wrapper) {
        return addSort(Boolean.TRUE, wrapper);
    }

    public OrderSqlBuilder addSort(Boolean predicate, OrderSqlBuilder wrapper) {
        if (prefix == null) {
            passPredicate &= predicate;
        }
        if (Boolean.TRUE.equals(predicate) && passPredicate && wrapper != null && !wrapper.sorts.isEmpty()) {
            this.sorts.addAll(wrapper.sorts);
        }
        return this;
    }

    @Override
    public String precompileSql() {
        if (prefix == null || prefix.isEmpty()) {
            return String.join(", ", sorts);
        }
        if (sorts.isEmpty()) {
            return prefix;
        }
        return prefix + " ORDER BY " + String.join(", ", sorts);
    }

    @Override
    public Object[] precompileArgs() {
        return precompileArgs;
    }

}
