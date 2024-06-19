package tt.smart.agency.im.context;

import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanNameGenerator;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.type.AnnotationMetadata;

/**
 * <p>
 *
 * </p>
 *
 * @author YangMC
 * @version V1.0
 **/
public class ImImportBeanDefinitionRegistrar implements ImportBeanDefinitionRegistrar {

    @Override
    public void registerBeanDefinitions(@NotNull AnnotationMetadata importingClassMetadata,
                                        @NotNull BeanDefinitionRegistry registry,
                                        @NotNull BeanNameGenerator importBeanNameGenerator) {
        this.registerBeanDefinitions(importingClassMetadata, registry);
    }

    @Override
    public void registerBeanDefinitions(@NotNull AnnotationMetadata importingClassMetadata,
                                        BeanDefinitionRegistry registry) {
//        BeanDefinition beanDefinition = BeanDefinitionBuilder.rootBeanDefinition(SpringWebSocketImConfig.class)
//                .getBeanDefinition();
//        registry.registerBeanDefinition("springWebSocketImConfig", beanDefinition);
        ImportBeanDefinitionRegistrar.super.registerBeanDefinitions(importingClassMetadata, registry);
    }
}
