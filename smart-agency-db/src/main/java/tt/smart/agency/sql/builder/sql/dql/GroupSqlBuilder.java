package tt.smart.agency.sql.builder.sql.dql;

import tt.smart.agency.sql.builder.sql.LambdaFunction;
import tt.smart.agency.sql.builder.sql.SqlBuilderRoute;
import tt.smart.agency.sql.tools.LambdaTools;
import tt.smart.agency.sql.tools.SqlNameTools;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * GROUP BY SQL 构造器
 * </p>
 *
 * @author MC_Yang
 * @version V1.0
 **/
public class GroupSqlBuilder implements SqlBuilderRoute, HavingSqlBuilderRoute, OrderSqlBuilderRoute,
        LimitSqlBuilderRoute, UnionSqlBuilderRoute {

    /**
     * 前缀
     */
    private final String prefix;
    /**
     * 列
     */
    private final List<String> columns;
    /**
     * 预编译参数
     */
    private final Object[] precompileArgs;

    protected GroupSqlBuilder(String prefix, Object[] precompileArgs, String... columns) {
        this.prefix = prefix;
        this.columns = Arrays.stream(columns).map(SqlNameTools::handleName).collect(Collectors.toList());
        this.precompileArgs = precompileArgs;
    }

    protected GroupSqlBuilder(String prefix, Object[] precompileArgs, SqlBuilderRoute... columns) {
        this(prefix, precompileArgs, Arrays.stream(columns).map(s -> "(" + s.precompileSql() + ")").toArray(String[]::new));
    }

    public GroupSqlBuilder addColumn(String... columns) {
        this.columns.addAll(Arrays.stream(columns).map(SqlNameTools::handleName).toList());
        return this;
    }

    public GroupSqlBuilder addColumn(SqlBuilderRoute... columns) {
        this.columns.addAll(Arrays.stream(columns).map(s -> "(" + s.build() + ")").toList());
        return this;
    }

    @SafeVarargs
    public final <P> GroupSqlBuilder addColumn(LambdaFunction<P, ?>... lambdaFunctions) {
        this.columns.addAll(Arrays.stream(lambdaFunctions).map(LambdaTools::getColumnName).map(SqlNameTools::handleName).toList());
        return this;
    }

    @Override
    public String precompileSql() {
        boolean prefixEmpty = prefix == null || prefix.isEmpty(), columnsEmpty = columns.isEmpty();
        if (prefixEmpty && columnsEmpty) {
            return "";
        }
        if (prefixEmpty) {
            return String.join(", ", columns);
        }
        if (columnsEmpty) {
            return prefix;
        }
        return prefix + " GROUP BY " + String.join(", ", columns);
    }

    @Override
    public Object[] precompileArgs() {
        return precompileArgs;
    }

}
