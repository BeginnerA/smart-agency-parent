package tt.smart.agency.sql.builder.sql.dql;

import tt.smart.agency.sql.builder.sql.PreparedStatementSupport;
import tt.smart.agency.sql.builder.sql.SqlBuilderRoute;
import tt.smart.agency.sql.constant.CommonConstant;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * UNION SQL 构造器
 * </p>
 *
 * @author MC_Yang
 * @version V1.0
 **/
public class UnionSqlBuilder implements SqlBuilderRoute, OrderSqlBuilderRoute, LimitSqlBuilderRoute {

    /**
     * 前缀
     */
    private final String prefix;
    /**
     * 拼接方式
     */
    protected String sep;
    /**
     * 拼接 SQL
     */
    private final List<SqlBuilderRoute> unions;
    /**
     * 预编译参数
     */
    private final List<Object> precompileArgs = new ArrayList<>();

    protected UnionSqlBuilder(String prefix, Object[] precompileArgs, SqlBuilderRoute... unions) {
        this.prefix = prefix;
        this.sep = "UNION";
        this.unions = new ArrayList<>(Arrays.asList(unions));
        this.precompileArgs.addAll(Arrays.asList(precompileArgs));
        for (SqlBuilderRoute union : unions) {
            this.precompileArgs.addAll(Arrays.asList(union.precompileArgs()));
        }
    }

    public UnionSqlBuilder union(SqlBuilderRoute... unions) {
        this.unions.addAll(Arrays.asList(unions));
        for (SqlBuilderRoute union : unions) {
            this.precompileArgs.addAll(Arrays.asList(union.precompileArgs()));
        }
        return this;
    }

    @Override
    public String precompileSql() {
        boolean prefixEmpty = prefix == null || prefix.isEmpty(), unionsEmpty = unions.isEmpty();
        if (prefixEmpty && unionsEmpty) {
            return "";
        }
        if (prefixEmpty) {
            return unions.stream().map(PreparedStatementSupport::precompileSql).collect(Collectors.joining(" " + sep + " "));
        }
        if (unionsEmpty) {
            return prefix;
        }
        return prefix + " " + sep + " " + unions.stream().map(PreparedStatementSupport::precompileSql).collect(Collectors.joining(" " + sep + " "));
    }

    @Override
    public Object[] precompileArgs() {
        return precompileArgs.toArray(CommonConstant.EMPTY_OBJECT_ARRAY);
    }

}
