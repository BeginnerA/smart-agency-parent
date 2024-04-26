package tt.smart.agency.message.enums.dd;

import lombok.AllArgsConstructor;
import lombok.Getter;
import tt.smart.agency.message.api.dd.DdApplyMessage;
import tt.smart.agency.message.config.properties.dd.DdConfig;
import tt.smart.agency.message.enums.PlatformType;
import tt.smart.agency.message.factory.dd.BaseDdPlatformFactory;
import tt.smart.agency.message.factory.dd.DdApplyMessageFactory;

/**
 * <p>
 * 钉钉平台类型
 * </p>
 *
 * @author YangMC
 * @version V1.0
 **/
@Getter
@AllArgsConstructor
public enum DdPlatformType implements PlatformType {
    /**
     * 钉钉应用消息
     */
    DD_APPLY("钉钉应用消息", DdApplyMessageFactory.instance());

    /**
     * 钉钉平台名称
     */
    private final String name;

    /**
     * 钉钉消息平台工厂
     */
    private final BaseDdPlatformFactory<? extends DdApplyMessage, ? extends DdConfig> platformFactory;
}