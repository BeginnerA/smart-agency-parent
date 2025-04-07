package tt.smart.agency.sql.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * <p>
 * 数据库类型
 * </p>
 *
 * @author MC_Yang
 * @version V1.0
 **/
@Getter
@AllArgsConstructor
public enum DbType {

    /**
     * MySQL
     */
    MY_SQL("MySQL"),

    /**
     * Oracle
     */
    ORACLE("Oracle"),

    /**
     * PostgreSQL
     */
    POSTGRE_SQL("PostgreSQL"),

    /**
     * SQL Server
     */
    SQL_SERVER("Microsoft SQL Server");

    /**
     * 数据库类型
     */
    private final String type;

}
