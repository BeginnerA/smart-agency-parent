package tt.smart.agency.sql.builder.sql.dml;

import tt.smart.agency.sql.builder.sql.SqlBuilderRoute;
import tt.smart.agency.sql.constant.CommonConstant;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * <p>
 * VALUES SQL 构造器
 * </p>
 *
 * @author MC_Yang
 * @version V1.0
 **/
public class ValuesSqlBuilder implements SqlBuilderRoute, DuplicateKeyUpdateSqlBuilderRoute {

    /**
     * 前缀
     */
    protected final String prefix;

    /**
     * 值
     */
    protected final StringBuilder valuesBuilder;

    /**
     * VALUES
     */
    protected String sign = "VALUES";

    /**
     * 预编译参数
     */
    protected final List<Object> precompileArgs = new ArrayList<>();

    protected ValuesSqlBuilder(String prefix, Object[] compileArgs) {
        this.prefix = prefix;
        this.valuesBuilder = new StringBuilder();
        precompileArgs.addAll(Arrays.asList(compileArgs));
    }

    public ValuesSqlBuilder addValue(Object... value) {
        if (value.length == 0) {
            return this;
        }
        if (!valuesBuilder.isEmpty()) {
            valuesBuilder.append(", ");
        }
        valuesBuilder.append("(");
        for (int i = 0; i < value.length; i++) {
            if (i > 0) {
                valuesBuilder.append(", ");
            }
            valuesBuilder.append("?");
            precompileArgs.add(value[i]);
        }
        valuesBuilder.append(")");
        return this;
    }

    public ValuesSqlBuilder addValues(List<Object[]> values) {
        if (values == null || values.isEmpty()) {
            return this;
        }
        values.forEach(this::addValue);
        return this;
    }

    @Override
    public String precompileSql() {
        boolean prefixEmpty = prefix == null || prefix.isEmpty(), valuesEmpty = valuesBuilder.isEmpty();
        if (prefixEmpty && valuesEmpty) {
            return "";
        }
        if (valuesEmpty) {
            return prefix;
        }
        if (prefixEmpty) {
            return valuesBuilder.toString();
        }
        return prefix + " " + sign + valuesBuilder;
    }

    @Override
    public Object[] precompileArgs() {
        return precompileArgs.toArray(CommonConstant.EMPTY_OBJECT_ARRAY);
    }

}
