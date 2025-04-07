package tt.smart.agency.sql.builder.sql.dml;

import tt.smart.agency.sql.builder.sql.LambdaFunction;
import tt.smart.agency.sql.builder.sql.SqlBuilderRoute;
import tt.smart.agency.sql.tools.LambdaTools;
import tt.smart.agency.sql.tools.SqlNameTools;

/**
 * <p>
 * 重复键更新 SQL 构造器
 * </p>
 *
 * @author MC_Yang
 * @version V1.0
 **/
public interface DuplicateKeyUpdateSqlBuilderRoute extends SqlBuilderRoute {

    default DuplicateKeyUpdateSqlBuilder onDuplicateKeyUpdateColumn(String column) {
        column = SqlNameTools.handleName(column);
        return new DuplicateKeyUpdateSqlBuilder(precompileSql(), precompileArgs(),  column+ " = values(" + column + ")");
    }

    default <P>DuplicateKeyUpdateSqlBuilder onDuplicateKeyUpdateColumn(LambdaFunction<P, ?> lambdaFunctions) {
        String column = SqlNameTools.handleName(LambdaTools.getColumnName(lambdaFunctions));
        return new DuplicateKeyUpdateSqlBuilder(precompileSql(), precompileArgs(),  column + " = values(" + column + ")");
    }

    default DuplicateKeyUpdateSqlBuilder onDuplicateKeyUpdateSetter(String setter) {
        return new DuplicateKeyUpdateSqlBuilder(precompileSql(), precompileArgs(), setter);
    }

}
