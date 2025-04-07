package tt.smart.agency.sql.builder.sql.dql;

import tt.smart.agency.sql.builder.sql.SqlBuilderRoute;
import tt.smart.agency.sql.constant.CommonConstant;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * <p>
 * LIMIT SQL 构造器
 * </p>
 *
 * @author MC_Yang
 * @version V1.0
 **/
public class LimitSqlBuilder implements SqlBuilderRoute, UnionSqlBuilderRoute {

    /**
     * 前缀
     */
    private final String prefix;
    /**
     * LIMIT SQL
     */
    private final String limitSql;
    /**
     * 预编译参数
     */
    private final List<Object> precompileArgs = new ArrayList<>();

    protected LimitSqlBuilder(String prefix, Object[] precompileArgs) {
        this.prefix = prefix;
        this.limitSql = null;
        this.precompileArgs.addAll(Arrays.asList(precompileArgs));
    }

    protected LimitSqlBuilder(String prefix, Object[] precompileArgs, int count) {
        this.prefix = prefix;
        this.precompileArgs.addAll(Arrays.asList(precompileArgs));
        this.precompileArgs.add(count);
        limitSql = "?";
    }

    protected LimitSqlBuilder(String prefix, Object[] precompileArgs, int offset, int count) {
        this.prefix = prefix;
        this.precompileArgs.addAll(Arrays.asList(precompileArgs));
        this.precompileArgs.add(offset);
        this.precompileArgs.add(count);
        limitSql = "?, ?";
    }

    @Override
    public String precompileSql() {
        if (prefix == null || prefix.isEmpty()) {
            return limitSql;
        }
        if (limitSql == null || limitSql.isEmpty()) {
            return prefix;
        }
        return prefix + " LIMIT " + limitSql;
    }

    @Override
    public Object[] precompileArgs() {
        return precompileArgs.toArray(CommonConstant.EMPTY_OBJECT_ARRAY);
    }

}
