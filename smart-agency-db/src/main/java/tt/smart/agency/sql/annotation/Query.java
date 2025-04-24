package tt.smart.agency.sql.annotation;

import tt.smart.agency.sql.constant.QueryRule;

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
public @interface Query {

    /**
     * 列名或字段映射表达式（如：like ${xxx}）
     */
    String value() default "";

    /**
     * 查询规则
     */
    QueryRule type() default QueryRule.EQ;

    /**
     * 映射字段名<br>
     * 注意：映射别名只适用于简单场景查询，复杂查询请使用{@link #mapFun()}
     */
    String attr() default "";

    /**
     * 映射函数<br>
     * 注意：该类必须为{@link java.util.Function}或{@link java.util.Biffunction}实现，并且包含空构造函数。
     */
    Class<?> mapFun() default Void.class;

    /**
     * 是否对常量函数打开布尔值。
     * 注意：{@link #trueToConst()},{@link #trueToConstType()},{@link #falseToConst()},{@link #falseToConstType()}
     * 方法仅在值为true时有效
     */
    boolean openBooleanToConst() default false;

    /**
     * boolean 值“ture”可以使用此方法转换为常量
     */
    String trueToConst() default "";

    /**
     * const 类型为 true
     */
    Class<?> trueToConstType() default Void.class;

    /**
     * boolean 值“false”可以使用此方法转换为常量
     */
    String falseToConst() default "";

    /**
     * const 类型为 true
     */
    Class<?> falseToConstType() default Void.class;

}
