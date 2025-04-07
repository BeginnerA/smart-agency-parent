package tt.smart.agency.sql.annotation;

import java.lang.annotation.*;

/**
 * <p>
 * 表注解
 * </p>
 *
 * @author MC_Yang
 * @version V1.0
 **/
@Inherited
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface Table {

    /**
     * 映射表名称
     */
    String value() default "";

}
