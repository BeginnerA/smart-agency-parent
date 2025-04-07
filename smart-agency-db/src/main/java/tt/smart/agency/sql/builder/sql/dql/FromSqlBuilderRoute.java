package tt.smart.agency.sql.builder.sql.dql;

import tt.smart.agency.sql.builder.sql.SqlBuilderRoute;
import tt.smart.agency.sql.domain.Alias;

/**
 * <p>
 * FORM SQL 构造器
 * </p>
 *
 * @author MC_Yang
 * @version V1.0
 **/
public interface FromSqlBuilderRoute extends SqlBuilderRoute {

    default FromSqlBuilder from(String ...tableNames) {
        return new FromSqlBuilder(precompileSql(), precompileArgs(), tableNames);
    }

    default FromSqlBuilder from(Alias...tableNames) {
        return new FromSqlBuilder(precompileSql(), precompileArgs(), tableNames);
    }

    default FromSqlBuilder from(Object ...tableNames) {
        return new FromSqlBuilder(precompileSql(), precompileArgs(), tableNames);
    }

    default FromSqlBuilder fromAs(String tableName, String aliasName) {
        return new FromSqlBuilder(precompileSql(), precompileArgs(), Alias.of(tableName, aliasName));
    }

}
