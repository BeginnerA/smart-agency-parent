package tt.smart.agency.sql.tools;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

/**
 * <p>
 * 类型转换工具
 * </p>
 *
 * @author MC_Yang
 * @version V1.0
 **/
@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ConvertTools {

    private static final Map<Class<?>, Function<String, Object>> CONVERTERS = new HashMap<>();

    static {
        CONVERTERS.put(String.class, s -> s);
        CONVERTERS.put(Byte.class, Byte::valueOf);
        CONVERTERS.put(Short.class, Short::valueOf);
        CONVERTERS.put(Integer.class, Integer::valueOf);
        CONVERTERS.put(Long.class, Long::valueOf);
        CONVERTERS.put(BigInteger.class, BigInteger::new);
        CONVERTERS.put(Double.class, Double::valueOf);
        CONVERTERS.put(Float.class, Float::valueOf);
        CONVERTERS.put(BigDecimal.class, BigDecimal::new);
        CONVERTERS.put(Boolean.class, Boolean::valueOf);
    }

    /**
     * 字符串类型转换
     *
     * @param origin 源值
     * @param clazz  类型
     * @param <T>    类型
     * @return 转换后的类型值
     */
    public static <T> T strTo(String origin, Class<T> clazz) {
        if (origin == null) {
            return null;
        }
        Function<String, Object> converter = CONVERTERS.get(clazz);
        if (converter != null) {
            return clazz.cast(converter.apply(origin));
        }
        throw new UnsupportedOperationException("不支持将字符串类型转换为 " + clazz.getSimpleName() + " 类型");
    }

}
