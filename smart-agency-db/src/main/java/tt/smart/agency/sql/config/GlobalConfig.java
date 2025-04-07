package tt.smart.agency.sql.config;

import tt.smart.agency.sql.constant.ConditionPriority;
import tt.smart.agency.sql.constant.DbType;

/**
 * <p>
 * 全球配置
 * </p>
 *
 * @author MC_Yang
 * @version V1.0
 **/
public abstract class GlobalConfig {

    /**
     * 全局 SQL 生成器类型。
     */
    public static DbType DATA_SOURCE_TYPE = DbType.MY_SQL;

    /**
     * 条件优先级配置
     */
    public static ConditionPriority CONDITION_PRIORITY = ConditionPriority.DEFAULT;

    /**
     * 配置 lambda 列名是否需要包含表名
     */
    public static boolean OPEN_LAMBDA_TABLE_NAME_MODE = false;

    /**
     * 配置 SQL 严格模式
     */
    public static boolean OPEN_STRICT_MODE = false;

}
