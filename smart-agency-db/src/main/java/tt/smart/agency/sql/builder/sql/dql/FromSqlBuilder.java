package tt.smart.agency.sql.builder.sql.dql;

import tt.smart.agency.sql.builder.sql.SqlBuilderRoute;
import tt.smart.agency.sql.domain.Alias;
import tt.smart.agency.sql.inner.ObjectMapper;
import tt.smart.agency.sql.tools.SqlNameTools;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * FORM SQL 构造器
 * </p>
 *
 * @author MC_Yang
 * @version V1.0
 **/
public class FromSqlBuilder implements SqlBuilderRoute, WhereSqlBuilderRoute, JoinSqlBuilderRoute, LimitSqlBuilderRoute,
        OrderSqlBuilderRoute, UnionSqlBuilderRoute, GroupSqlBuilderRoute {

    /**
     * 前缀
     */
    private final String prefix;
    /**
     * 表
     */
    private final List<String> tables;
    /**
     * 预编译参数
     */
    private final Object[] precompileArgs;

    protected FromSqlBuilder(String prefix, Object[] precompileArgs, String... tables) {
        this.prefix = prefix;
        this.tables = Arrays.stream(tables).map(SqlNameTools::handleName).collect(Collectors.toList());
        this.precompileArgs = precompileArgs;
    }

    protected FromSqlBuilder(String prefix, Object[] precompileArgs, Alias... tables) {
        this.prefix = prefix;
        this.tables = new ArrayList<>();
        this.precompileArgs = precompileArgs;
        addTable(tables);
    }

    protected FromSqlBuilder(String prefix, Object[] precompileArgs, Object... tables) {
        this.prefix = prefix;
        this.tables = new ArrayList<>();
        this.precompileArgs = precompileArgs;
        Arrays.stream(tables).forEach(e -> {
            switch (e) {
                case CharSequence charSequence -> addTable(e.toString());
                case Alias alias -> addTable(alias);
                case Class<?> aClass -> this.tables.add(SqlNameTools.handleName(ObjectMapper.getTableName(aClass)));
                case null, default -> {
                    assert e != null;
                    log.warn("表类型 {} 是sql中无法识别的类型，被忽略", e.getClass().getName());
                }
            }
        });
    }

    public FromSqlBuilder addTable(String... tables) {
        this.tables.addAll(Arrays.stream(tables).map(SqlNameTools::handleName).toList());
        return this;
    }

    public FromSqlBuilder addTableAs(String table, String aliasName) {
        return addTable(Alias.of(table, aliasName));
    }

    public FromSqlBuilder addTable(Alias... tables) {
        this.tables.addAll(Arrays.stream(tables).map(SqlNameTools::handleName).toList());
        return this;
    }

    @Override
    public String precompileSql() {
        if (tables.isEmpty()) {
            return prefix;
        }
        return prefix + " FROM " + String.join(", ", tables);
    }

    @Override
    public Object[] precompileArgs() {
        return precompileArgs;
    }

}
