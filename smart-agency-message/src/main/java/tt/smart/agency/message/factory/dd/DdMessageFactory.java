package tt.smart.agency.message.factory.dd;

import tt.smart.agency.message.api.dd.DdMessageBlend;
import tt.smart.agency.message.config.properties.dd.DdConfig;
import tt.smart.agency.message.domain.dd.DdMessage;
import tt.smart.agency.message.domain.dd.DdMessageSendResult;
import tt.smart.agency.message.enums.PlatformType;
import tt.smart.agency.message.enums.dd.DdPlatformType;

/**
 * <p>
 * 钉钉消息工厂
 * </p>
 *
 * @author YangMC
 * @version V1.0
 **/
public class DdMessageFactory {

    /**
     * 创建钉钉消息服务
     *
     * @param platformType 钉钉平台类型
     * @return 钉钉消息服务
     */
    @SuppressWarnings(value = {"unchecked", "rawtypes"})
    public static DdMessageBlend<DdMessageSendResult, DdMessage> createDdMessageBlend(PlatformType platformType) {
        BaseDdPlatformFactory platformFactory = platformType.getPlatformFactory();
        return platformFactory.createDdMessage((DdConfig) platformFactory.buildConfig());
    }

    /**
     * 创建钉钉消息服务
     *
     * @param platformType 钉钉平台类型
     * @return 钉钉消息服务
     */
    @SuppressWarnings(value = {"unchecked", "rawtypes"})
    public static DdMessageBlend<DdMessageSendResult, DdMessage> createDdMessageBlend(DdPlatformType platformType) {
        BaseDdPlatformFactory platformFactory = platformType.getPlatformFactory();
        return platformFactory.createDdMessage((DdConfig) platformFactory.buildConfig());
    }

}
