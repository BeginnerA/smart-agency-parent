package tt.smart.agency.cache.config;

import org.springframework.context.annotation.Import;

/**
 * <p>
 * 自动配置
 * </p>
 *
 * @author YangMC
 * @version V1.0
 **/
@Import({AttributeAutoConfiguration.class, ServiceAutoConfiguration.class})
public class AutoConfiguration {

}
