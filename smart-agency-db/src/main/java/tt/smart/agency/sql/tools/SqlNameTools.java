package tt.smart.agency.sql.tools;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import tt.smart.agency.sql.config.GlobalConfig;
import tt.smart.agency.sql.domain.Alias;

import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * <p>
 * SQL 名称工具
 * </p>
 *
 * @author MC_Yang
 * @version V1.0
 **/
@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class SqlNameTools {

    /**
     * 处理名称
     *
     * @param alias 别名
     * @return 名称
     */
    public static String handleName(Alias alias) {
        if (alias != null) {
            if (GlobalConfig.OPEN_STRICT_MODE) {
                return handleName(alias.toString());
            } else {
                return alias.toString();
            }
        }
        return "";
    }

    /**
     * 处理名称
     *
     * @param name 名称
     * @return 名称
     */
    public static String handleName(String name) {
        if (GlobalConfig.OPEN_STRICT_MODE && name != null) {
            return Arrays.stream(name.split("\\.")).map(String::trim).map(SqlNameTools::handleStrictName).collect(Collectors.joining("."));
        }
        return name;
    }

    /**
     * 处理严格名称
     *
     * @param name 名称
     * @return 名称
     */
    private static String handleStrictName(String name) {
        if (!"*".equals(name) && !name.contains("(")) {
            if (name.contains(",")) {
                return Arrays.stream(name.split(",")).map(String::trim).map(SqlNameTools::handleName).collect(Collectors.joining(", "));
            } else if (PatternTools.matches(name, ".*\\s+as\\s+.*")) {
                String[] columnAndAlias = name.split("\\s+as\\s+");
                return handleName(columnAndAlias[0].trim()) + " as " + handleName(columnAndAlias[1].trim());
            } else if (name.startsWith("`") || name.contains("->")) {
                return name;
            }
            return "`" + name + "`";
        }
        return name;
    }
}
