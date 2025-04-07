package tt.smart.agency.sql.builder.sql.dql;

import lombok.extern.slf4j.Slf4j;
import tt.smart.agency.sql.builder.sql.LambdaFunction;
import tt.smart.agency.sql.builder.sql.SqlBuilderRoute;
import tt.smart.agency.sql.domain.Alias;
import tt.smart.agency.sql.inner.ObjectMapper;
import tt.smart.agency.sql.tools.LambdaTools;
import tt.smart.agency.sql.tools.SqlNameTools;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * SELECT SQL 构造器
 * </p>
 *
 * @author MC_Yang
 * @version V1.0
 **/
@Slf4j
public class SelectSqlBuilder implements SqlBuilderRoute, FromSqlBuilderRoute, OrderSqlBuilderRoute,
        LimitSqlBuilderRoute, UnionSqlBuilderRoute {

    /**
     * 前缀
     */
    private final String prefix;
    /**
     * 预编译参数
     */
    private final Object[] precompileArgs;
    /**
     * 列
     */
    private final List<String> columns;

    public SelectSqlBuilder(String prefix, Object[] precompileArgs, String... columns) {
        this.prefix = prefix;
        this.precompileArgs = precompileArgs;
        this.columns = Arrays.stream(columns).map(SqlNameTools::handleName).collect(Collectors.toList());
    }

    public SelectSqlBuilder(String prefix, Object[] precompileArgs, Alias... columns) {
        this.prefix = prefix;
        this.precompileArgs = precompileArgs;
        this.columns = new ArrayList<>(columns.length);
        addColumn(columns);
    }

    @SafeVarargs
    public <P> SelectSqlBuilder(String prefix, Object[] precompileArgs, LambdaFunction<P, ?>... lambdaFunctions) {
        this.prefix = prefix;
        this.precompileArgs = precompileArgs;
        this.columns = new ArrayList<>(lambdaFunctions.length);
        addColumn(lambdaFunctions);
    }

    public SelectSqlBuilder(String prefix, Object[] precompileArgs, Object... columns) {
        this.prefix = prefix;
        this.precompileArgs = precompileArgs;
        this.columns = new ArrayList<>(columns.length);
        Arrays.stream(columns).forEach(this::handleObjectColumn);
    }

    public SelectSqlBuilder addColumn(String... columns) {
        this.columns.addAll(Arrays.stream(columns).map(SqlNameTools::handleName).toList());
        return this;
    }

    public SelectSqlBuilder addColumn(Alias... columns) {
        this.columns.addAll(Arrays.stream(columns).map(SqlNameTools::handleName).toList());
        return this;
    }

    @SafeVarargs
    public final <P> SelectSqlBuilder addColumn(LambdaFunction<P, ?>... lambdaFunctions) {
        this.columns.addAll(Arrays.stream(lambdaFunctions).map(LambdaTools::getColumnName)
                .map(SqlNameTools::handleName).toList());
        return this;
    }

    public final SelectSqlBuilder addColumn(Class<?>... clazz) {
        for (Class<?> aClass : clazz) {
            handleObjectColumn(aClass);
        }
        return this;
    }

    /**
     * 处理对象列
     *
     * @param e 对象
     */
    private void handleObjectColumn(Object e) {
        switch (e) {
            case CharSequence charSequence -> addColumn(e.toString());
            case Alias alias -> addColumn(alias);
            case Class<?> aClass ->
                    this.columns.addAll(ObjectMapper.getStrictColumnName(aClass).stream().map(SqlNameTools::handleName)
                            .toList());
            case LambdaFunction<?, ?> lambdaFunction ->
                    this.columns.add(SqlNameTools.handleName(LambdaTools.getColumnName(lambdaFunction)));
            case null, default -> {
                assert e != null;
                log.warn("列类型 {} 是 SQL 中无法识别的类型，被忽略", e.getClass().getName());
            }
        }
    }

    @Override
    public String precompileSql() {
        if (prefix == null || prefix.isEmpty()) {
            return "SELECT " + String.join(", ", columns);
        }
        return prefix + " SELECT " + String.join(", ", columns);
    }

    @Override
    public Object[] precompileArgs() {
        return precompileArgs;
    }
}
