package tt.smart.agency.sql.builder.sql;

/**
 * <p>
 * JDBC 预处理语句支持
 * </p>
 *
 * @author MC_Yang
 * @version V1.0
 **/
public interface PreparedStatementSupport {

    /**
     * 预编译的 SQL
     * @return sql
     */
    String precompileSql();

    /**
     * 预编译参数
     * @return args
     */
    Object[] precompileArgs();

}
