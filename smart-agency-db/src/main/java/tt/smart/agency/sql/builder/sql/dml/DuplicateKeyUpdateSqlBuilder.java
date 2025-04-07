package tt.smart.agency.sql.builder.sql.dml;

import lombok.extern.slf4j.Slf4j;
import tt.smart.agency.sql.builder.sql.SqlBuilderRoute;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 重复键更新 SQL 构造器
 * </p>
 *
 * @author MC_Yang
 * @version V1.0
 **/
@Slf4j
public class DuplicateKeyUpdateSqlBuilder implements SqlBuilderRoute {

    /**
     * VALUES/VALUE
     */
    private final String sign;
    /**
     * 前缀
     */
    private final String prefix;
    /**
     * setters
     */
    private final List<String> setters;
    /**
     * 预编译参数
     */
    private final Object[] precompileArgs;

    public DuplicateKeyUpdateSqlBuilder(String prefix, Object[] precompileArgs) {
        this.sign = "values";
        this.prefix = prefix;
        this.setters = new ArrayList<>();
        this.precompileArgs = precompileArgs;
    }

    public DuplicateKeyUpdateSqlBuilder(String prefix, Object[] precompileArgs, String setter) {
        this(prefix, precompileArgs);
        setters.add(setter);
    }

    @Override
    public String precompileSql() {
        return prefix + " ON DUPLICATE KEY UPDATE " + String.join(", ", setters);
    }

    @Override
    public Object[] precompileArgs() {
        return precompileArgs;
    }

}
