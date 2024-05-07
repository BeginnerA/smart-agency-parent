package tt.smart.agency.component.spring.autoconfigure;

import org.springframework.context.annotation.Conditional;

import java.lang.annotation.*;

/**
 * <p>
 * 对 Spring @ConditionalOnResource 注解进行增强。<br>
 * 支持多文件目录扫描，如果文件不存在，跳过继续扫描。
 * </p>
 *
 * @author YangMC
 * @version V1.0
 **/
@Documented
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Conditional(OnConditionalOnFile.class)
public @interface ConditionalOnFile {
    /**
     * 必须存在的资源。
     *
     * @return 必须存在的资源路径。
     */
    String[] resources() default {};

}