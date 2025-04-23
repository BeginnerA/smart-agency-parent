package tt.smart.agency.component.word.tools;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.lang.reflect.Array;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;
import java.util.Collection;
import java.util.Date;
import java.util.Map;
import java.util.Objects;

import static tt.smart.agency.component.word.tools.PoiElTools.EMPTY;

/**
 * <p>
 * 满足模板的 EL 表达式支持
 * </p>
 *
 * @author MC_Yang
 * @version V1.0
 **/
@SuppressWarnings("rawtypes")
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class PoiFunctionTools {

    private static final String TWO_DECIMAL_STR = "###0.00";
    private static final String THREE_DECIMAL_STR = "###0.000";

    private static final DecimalFormat TWO_DECIMAL = new DecimalFormat(TWO_DECIMAL_STR);
    private static final DecimalFormat THREE_DECIMAL = new DecimalFormat(THREE_DECIMAL_STR);

    private static final String DAY_STR = "yyyy-MM-dd";
    private static final String TIME_STR = "yyyy-MM-dd HH:mm:ss";
    private static final String TIME__NO_S_STR = "yyyy-MM-dd HH:mm";

    private static final SimpleDateFormat DAY_FORMAT = new SimpleDateFormat(DAY_STR);
    private static final SimpleDateFormat TIME_FORMAT = new SimpleDateFormat(TIME_STR);
    private static final SimpleDateFormat TIME__NO_S_FORMAT = new SimpleDateFormat(TIME__NO_S_STR);

    /**
     * 获取对象的长度
     *
     * @param obj 值
     * @return 结果
     */
    public static int length(Object obj) {
        switch (obj) {
            case null -> {
                return 0;
            }
            case Map map -> {
                return map.size();
            }
            case Collection collection -> {
                return collection.size();
            }
            default -> {
            }
        }
        if (obj.getClass().isArray()) {
            return Array.getLength(obj);
        }
        return String.valueOf(obj).length();
    }

    /**
     * 格式化数字
     *
     * @param obj    值
     * @param format 格式
     * @return 结果
     */
    public static String formatNumber(Object obj, String format) {
        if (obj == null || Objects.equals(obj.toString(), EMPTY)) {
            return EMPTY;
        }
        double number = Double.parseDouble(obj.toString());
        DecimalFormat decimalFormat;
        if (TWO_DECIMAL.equals(format)) {
            decimalFormat = TWO_DECIMAL;
        } else if (THREE_DECIMAL_STR.equals(format)) {
            decimalFormat = THREE_DECIMAL;
        } else {
            decimalFormat = new DecimalFormat(format);
        }
        return decimalFormat.format(number);
    }

    /**
     * 格式化时间
     *
     * @param obj    值
     * @param format 格式
     * @return 结果
     */
    public static String formatDate(Object obj, String format) {
        if (obj == null || EMPTY.equals(obj.toString())) {
            return EMPTY;
        }
        if (obj instanceof Date || obj instanceof Number) {
            return switch (format) {
                case DAY_STR -> DAY_FORMAT.format(obj);
                case TIME_STR -> TIME_FORMAT.format(obj);
                case TIME__NO_S_STR -> TIME__NO_S_FORMAT.format(obj);
                case null, default -> {
                    assert format != null;
                    yield new SimpleDateFormat(format).format(obj);
                }
            };
        } else if (obj instanceof TemporalAccessor) {
            return DateTimeFormatter.ofPattern(format).format((TemporalAccessor) obj);
        }
        throw new RuntimeException("Word 导出失败，暂不支持的时间类型");
    }

    /**
     * 判断是不是成功
     *
     * @param first    第一个值
     * @param operator 操作符
     * @param second   第二个值
     * @return 结果
     */
    public static boolean isTrue(Object first, String operator, Object second) {
        if (">".endsWith(operator)) {
            return isGt(first, second);
        } else if ("<".endsWith(operator)) {
            return isGt(second, first);
        } else if ("==".endsWith(operator)) {
            if (first != null && second != null) {
                return eq(first, second);
            }
            return first == second;
        } else if ("!=".endsWith(operator)) {
            if (first != null && second != null) {
                return !first.equals(second);
            }
            return first != second;
        } else {
            throw new RuntimeException("Word 导出失败，占不支持改操作符");
        }
    }

    /**
     * 判断两个对象是不是相等
     *
     * @param first  第一个值
     * @param second 第二个值
     * @return 结果
     */
    private static boolean eq(Object first, Object second) {
        // 要求两个对象当中至少一个对象不是字符串才进行数字类型判断
        if (!(first instanceof String) || !(second instanceof String)) {
            try {
                BigDecimal f = new BigDecimal(first.toString());
                BigDecimal s = new BigDecimal(second.toString());
                return f.compareTo(s) == 0;
            } catch (NumberFormatException e) {
                // 可能存在的错误, 忽略继续进行
            }

        }
        return first.equals(second);
    }

    /**
     * 前者是不是大于后者
     *
     * @param first  第一个值
     * @param second 第二个值
     * @return 结果
     */
    private static boolean isGt(Object first, Object second) {
        if (first == null || Objects.equals(first.toString(), EMPTY)) {
            return false;
        }
        if (second == null || Objects.equals(second.toString(), EMPTY)) {
            return true;
        }
        double one = Double.parseDouble(first.toString());
        double two = Double.parseDouble(second.toString());
        return one > two;
    }

}
