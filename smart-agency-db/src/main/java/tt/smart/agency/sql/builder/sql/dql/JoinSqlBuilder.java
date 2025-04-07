package tt.smart.agency.sql.builder.sql.dql;

import tt.smart.agency.sql.builder.sql.SqlBuilderRoute;
import tt.smart.agency.sql.domain.Alias;
import tt.smart.agency.sql.inner.ObjectMapper;
import tt.smart.agency.sql.tools.SqlNameTools;

/**
 * <p>
 * JOIN 构造器
 * </p>
 *
 * @author MC_Yang
 * @version V1.0
 **/
public class JoinSqlBuilder implements SqlBuilderRoute, JoinOnSqlBuilderRoute {

    /**
     * 前缀
     */
    private final String prefix;
    /**
     * 链接类型
     */
    private final String joinType;
    /**
     * 表
     */
    private final String table;
    /**
     * 预编译参数
     */
    private final Object[] precompileArgs;

    protected JoinSqlBuilder(String prefix, Object[] precompileArgs, String joinType, Object table) {
        this.prefix = prefix;
        this.precompileArgs = precompileArgs;
        this.joinType = joinType;
        switch (table) {
            case CharSequence charSequence -> this.table = SqlNameTools.handleName(table.toString());
            case Alias alias -> this.table = SqlNameTools.handleName(alias);
            case Class<?> aClass -> this.table = SqlNameTools.handleName(ObjectMapper.getTableName(aClass));
            case null, default -> {
                assert table != null;
                throw new RuntimeException("连接表类型 " + table.getClass().getName() + " 在 SQL 中是无法识别的类型。");
            }
        }
    }

    @Override
    public String precompileSql() {
        return prefix + " " + joinType + " " + table;
    }

    @Override
    public Object[] precompileArgs() {
        return precompileArgs;
    }

}
