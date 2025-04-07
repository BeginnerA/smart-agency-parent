package tt.smart.agency.sql.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * <p>
 * INSERT
 * </p>
 *
 * @author MC_Yang
 * @version V1.0
 **/
@Getter
@AllArgsConstructor
public enum InsertMode {

    /**
     * INSERT INTO
     */
    INSERT_INTO("INSERT INTO"),
    /**
     * REPLACE
     */
    REPLACE("REPLACE"),
    /**
     * INSERT OVERWRITE
     */
    INSERT_OVERWRITE("INSERT OVERWRITE"),
    /**
     * INSERT IGNORE
     */
    INSERT_IGNORE("INSERT IGNORE"),
    /**
     * REPLACE INTO
     */
    REPLACE_INTO("REPLACE INTO");

    /**
     * 前缀
     */
    private final String prefix;

}
