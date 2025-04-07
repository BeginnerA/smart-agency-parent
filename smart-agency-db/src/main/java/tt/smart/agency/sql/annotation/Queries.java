package tt.smart.agency.sql.annotation;

import java.lang.annotation.*;

/**
 * <p>
 * 查询注解
 * </p>
 *
 * @author MC_Yang
 * @version V1.0
 **/
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
public @interface Queries {

    /**
     * 值
     */
    Query[] value() default {};

}
