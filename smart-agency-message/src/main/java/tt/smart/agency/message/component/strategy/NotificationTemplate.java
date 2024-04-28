package tt.smart.agency.message.component.strategy;

import cn.hutool.core.util.ObjUtil;
import cn.hutool.core.util.StrUtil;
import lombok.Data;
import tt.smart.agency.message.component.domain.Message;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * <p>
 * 通知模板
 * </p>
 *
 * @author YangMC
 * @version V1.0
 **/
@Data
public class NotificationTemplate {
    /**
     * 消息推送平台
     */
    Set<MessagePushPlatformEnum> platformSet = new HashSet<>();
    /**
     * 消息列表
     */
    List<Message> messagesList = new ArrayList<>();

    /**
     * 设置消息推送平台。<br>
     * 注意：该设置不会清除原始配置
     *
     * @param platform 推送平台
     */
    public void setPlatform(String platform) {
        setPlatform(platform, false);
    }

    /**
     * 设置消息推送平台
     *
     * @param platform              推送平台
     * @param isClearOriginalConfig 是否清除原始配置
     */
    public void setPlatform(String platform, boolean isClearOriginalConfig) {
        if (StrUtil.isNotEmpty(platform)) {
            String[] platforms = platform.split(",");
            Set<MessagePushPlatformEnum> strategyEnums = new HashSet<>();
            for (String platformCode : platforms) {
                MessagePushPlatformEnum platformEnum = MessagePushPlatformEnum.getPlatformEnum(platformCode);
                if (platformEnum != null) {
                    strategyEnums.add(platformEnum);
                }
            }
            if (isClearOriginalConfig) {
                this.platformSet = strategyEnums;
            } else {
                this.platformSet.addAll(strategyEnums);
            }
        }
    }

    /**
     * 设置推送消息
     *
     * @param message 消息
     */
    public void setMessages(Message message) {
        this.messagesList.add(message);
    }

    /**
     * 设置推送消息
     *
     * @param messages 消息列表
     */
    public void setMessages(List<Message> messages) {
        this.messagesList = messages;
    }

    /**
     * 检查消息完整性，消息是否具备推送条件
     *
     * @return 消息是否具备推送条件
     */
    public boolean checkIntegrity() {
        if (this.messagesList.isEmpty() && this.platformSet.isEmpty()) {
            return false;
        }
        return this.messagesList.stream().allMatch(e -> ObjUtil.isNotEmpty(e.getContent()));
    }
}