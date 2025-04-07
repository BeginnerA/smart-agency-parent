package tt.smart.agency.sql.annotation;

import java.lang.annotation.*;

/**
 * <p>
 * 主键注解
 * </p>
 *
 * @author MC_Yang
 * @version V1.0
 **/
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.METHOD})
public @interface Primary {
}
