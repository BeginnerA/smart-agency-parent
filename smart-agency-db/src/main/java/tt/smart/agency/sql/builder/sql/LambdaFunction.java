package tt.smart.agency.sql.builder.sql;

import java.io.Serializable;
import java.util.function.Function;

/**
 * <p>
 * lambda 函数
 * </p>
 *
 * @author MC_Yang
 * @version V1.0
 **/
@FunctionalInterface
public interface LambdaFunction<T, R> extends Function<T, R>, Serializable {
}
