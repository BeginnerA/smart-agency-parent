package tt.smart.agency.sql.builder.sql.dql;

import tt.smart.agency.sql.builder.sql.SqlBuilderRoute;

/**
 * <p>
 * UNION ALL SQL 构造器
 * </p>
 *
 * @author MC_Yang
 * @version V1.0
 **/
public class UnionAllSqlBuilder extends UnionSqlBuilder {

    protected UnionAllSqlBuilder(String prefix, Object[] precompileArgs, SqlBuilderRoute... unions) {
        super(prefix, precompileArgs, unions);
        sep = "UNION ALL";
    }

}
