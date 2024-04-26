package tt.smart.agency.message.component.domain;

import cn.hutool.core.util.StrUtil;
import lombok.Data;
import lombok.experimental.SuperBuilder;
import tt.smart.agency.message.component.strategy.MessagePushPlatformEnum;

/**
 * <p>
 * 消息模板配置
 * </p>
 *
 * @author YangMC
 * @version V1.0
 **/
@Data
@SuperBuilder
public class MessageTemplateConfig {

    /**
     * 消息类型
     */
    private String type;

    /**
     * 标题
     */
    private String title;

    /**
     * 消息模板内容
     */
    private String content;

    /**
     * 跳转地址（如：微信公众号消息跳转小程序）
     */
    private String path;

    /**
     * 第三方平台模板 ID （如：微信公众号、短信等等）
     */
    private String thirdTemplateId;

    /**
     * 消息推送平台类型{@link MessagePushPlatformEnum}（多个用逗号分隔）
     */
    private String platform;

    /**
     * 消息身份鉴别（唯一的）。<br>
     * 在同一个策略中使用多个消息时用于区分消息，也可以作为消息的唯一身份鉴别
     */
    private String ident;

    /**
     * 接收方（多个用逗号分隔）
     */
    private String recipients;

    /**
     * 消息配置标准基本完整性检查。<br>
     * 检查：消息内容、推送策略、如果推送策略是短信才会检查模板 ID
     *
     * @return 是否是标准的配置
     */
    public boolean checkBasicIntegrity() {
        boolean checkResult = StrUtil.isNotEmpty(content);
        if (checkResult && (MessagePushPlatformEnum.ALIBABA.getCode().equalsIgnoreCase(platform) ||
                MessagePushPlatformEnum.HUAWEI.getCode().equalsIgnoreCase(platform) ||
                MessagePushPlatformEnum.TENCENT.getCode().equalsIgnoreCase(platform) ||
                MessagePushPlatformEnum.CTYUN.getCode().equalsIgnoreCase(platform) ||
                MessagePushPlatformEnum.NETEASE.getCode().equalsIgnoreCase(platform))) {
            checkResult = StrUtil.isNotEmpty(thirdTemplateId);
        }
        return checkResult;
    }
}