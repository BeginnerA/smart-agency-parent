package tt.smart.agency.component.spring.autoconfigure;

import org.springframework.boot.autoconfigure.condition.ConditionMessage;
import org.springframework.boot.autoconfigure.condition.ConditionOutcome;
import org.springframework.boot.autoconfigure.condition.SpringBootCondition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.io.FileSystemResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.type.AnnotatedTypeMetadata;
import org.springframework.util.Assert;
import org.springframework.util.MultiValueMap;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 检查特定的资源。
 * </p>
 *
 * @author YangMC
 * @version V1.0
 **/
public class OnConditionalOnFile extends SpringBootCondition {
    private final ResourceLoader fileSystemResourceLoader = new FileSystemResourceLoader();

    @Override
    public ConditionOutcome getMatchOutcome(ConditionContext context, AnnotatedTypeMetadata metadata) {
        MultiValueMap<String, Object> attributes = metadata.getAllAnnotationAttributes(ConditionalOnFile.class.getName(), true);
        ArrayList<String> locations = new ArrayList<>();
        assert attributes != null;
        collectValues(locations, attributes.get("resources"));
        Assert.isTrue(!locations.isEmpty(),
                "@ConditionalOnFile annotations must specify at "
                        + "least one resource location");
        for (String location : locations) {
            String resourceLocation = context.getEnvironment().resolvePlaceholders(location);
            Resource fileResource = this.fileSystemResourceLoader.getResource(resourceLocation);
            if (fileResource.exists()) {
                return ConditionOutcome.match(ConditionMessage.forCondition(ConditionalOnFile.class)
                        .found("location", "locations").items(location));
            }
        }
        return ConditionOutcome.noMatch(ConditionMessage.forCondition(ConditionalOnFile.class)
                .didNotFind("resource", "resources").items(ConditionMessage.Style.QUOTE, locations));
    }

    private void collectValues(List<String> names, List<Object> values) {
        for (Object value : values) {
            for (Object item : (Object[]) value) {
                names.add((String) item);
            }
        }
    }

}