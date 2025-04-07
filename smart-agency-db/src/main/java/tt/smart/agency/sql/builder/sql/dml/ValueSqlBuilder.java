package tt.smart.agency.sql.builder.sql.dml;

/**
 * <p>
 * VALUE SQL 构造器
 * </p>
 *
 * @author MC_Yang
 * @version V1.0
 **/
public class ValueSqlBuilder extends ValuesSqlBuilder {

    public ValueSqlBuilder(String prefix, Object[] compileArgs) {
        super(prefix, compileArgs);
        sign = "VALUE";
    }
}
