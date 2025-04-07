package tt.smart.agency.sql.annotation;

import java.lang.annotation.*;

/**
 * <p>
 * 表列（字段）注解
 * </p>
 *
 * @author MC_Yang
 * @version V1.0
 **/
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.FIELD, ElementType.METHOD, ElementType.ANNOTATION_TYPE })
public @interface Column {
    /**
     * 映射列名
     */
    String value() default "";

    /**
     * 数据值转换函数
     */
    Class<?> readMapFun() default Void.class;
}
