package tt.smart.agency.sql.tools;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Pattern;

/**
 * <p>
 * 规则工具
 * </p>
 *
 * @author MC_Yang
 * @version V1.0
 **/
@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PatternTools {

    private final static Map<String, Pattern> PATTERN_CACHE_MAP = new ConcurrentHashMap<>();

    /**
     * 正则表达式匹配
     *
     * @param source 匹配内容
     * @param regex  正则表达式
     * @return 结果
     */
    public static boolean matches(CharSequence source, String regex) {
        Pattern p = PATTERN_CACHE_MAP.get(regex);
        if (Objects.isNull(p)) {
            p = Pattern.compile(regex);
            PATTERN_CACHE_MAP.put(regex, p);
        }
        return p.matcher(source).matches();
    }
}
