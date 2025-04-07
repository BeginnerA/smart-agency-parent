package tt.smart.agency.sql.builder.sql.dml;

import tt.smart.agency.sql.builder.sql.LambdaFunction;
import tt.smart.agency.sql.builder.sql.SqlBuilderRoute;
import tt.smart.agency.sql.builder.sql.Tuple2;
import tt.smart.agency.sql.builder.sql.dql.FromSqlBuilderRoute;
import tt.smart.agency.sql.builder.sql.dql.WhereSqlBuilderRoute;
import tt.smart.agency.sql.constant.CommonConstant;
import tt.smart.agency.sql.constant.QueryRule;
import tt.smart.agency.sql.tools.LambdaTools;
import tt.smart.agency.sql.tools.SqlNameTools;
import tt.smart.agency.sql.tools.SqlTemplateTools;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * <p>
 * SET SQL 构造器
 * </p>
 *
 * @author MC_Yang
 * @version V1.0
 **/
public class SetSqlBuilder implements SqlBuilderRoute, FromSqlBuilderRoute, WhereSqlBuilderRoute {

    /**
     * 前缀
     */
    private final String prefix;
    /**
     * set
     */
    private final List<String> setters;
    /**
     * 预编译参数
     */
    private final List<Object> precompileArgs = new ArrayList<>();

    protected SetSqlBuilder(String prefix, Object[] precompileArgs) {
        this.prefix = prefix;
        this.setters = new ArrayList<>();
        this.precompileArgs.addAll(Arrays.asList(precompileArgs));
    }

    protected SetSqlBuilder(String prefix, Object[] precompileArgs, String column, Object value) {
        this(prefix, precompileArgs);
        addSet(column, value);
    }

    protected SetSqlBuilder(String prefix, Object[] precompileArgs, LambdaFunction<?, ?> lambdaFunction, Object value) {
        this(prefix, precompileArgs);
        SetSqlBuilder setSqlBuilder = addSet(lambdaFunction, value);
    }

    protected SetSqlBuilder(String prefix, Object[] precompileArgs, String setter) {
        this(prefix, precompileArgs);
        setters.add(setter);
    }

    public SetSqlBuilder addSet(String setter, Object...values) {
        setters.add(setter);
        if (values.length > 0) {
            precompileArgs.addAll(Arrays.asList(values));
        }
        return this;
    }

    public SetSqlBuilder addSet(String column, Object value) {
        Tuple2<String, Object[]> pt = SqlTemplateTools.parsePrecompileCondition(SqlNameTools.handleName(column),
                QueryRule.EQ, value);
        return addSet(pt.t1, pt.t2);
    }

    public <P> SetSqlBuilder addSet(LambdaFunction<P, ?> lambdaFunction, Object value) {
        Tuple2<String, Object[]> pt = SqlTemplateTools.parsePrecompileCondition(SqlNameTools.handleName(
                LambdaTools.getColumnName(lambdaFunction)), QueryRule.EQ, value);
        return addSet(pt.t1, pt.t2);
    }


    @Override
    public String precompileSql() {
        boolean prefixEmpty = prefix == null || prefix.isEmpty(), settersEmpty = setters.isEmpty();
        if (prefixEmpty && settersEmpty) {
            return "";
        }
        if (settersEmpty) {
            return prefix;
        }
        if (prefixEmpty) {
            return String.join(", ", setters);
        }
        return prefix + " SET " + String.join(", ", setters);
    }

    @Override
    public Object[] precompileArgs() {
        return precompileArgs.toArray(CommonConstant.EMPTY_OBJECT_ARRAY);
    }

}
