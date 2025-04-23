package tt.smart.agency.message.enums.dd;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

/**
 * <p>
 * 钉钉消息类型
 * </p>
 *
 * @author MC_Yang
 * @version V1.0
 **/
@Getter
@AllArgsConstructor
public enum DdMessageType {

    /**
     * 文本消息
     */
    TEXT("text"),
    /**
     * 图片消息
     */
    IMAGE("image"),
    /**
     * 语音消息
     */
    VOICE("voice"),
    /**
     * 推送文件
     */
    FILE("file"),
    /**
     * 链接消息
     */
    LINK("link"),
    /**
     * OA消息
     */
    OA("oa"),
    /**
     * markdown 消息
     */
    MARKDOWN("markdown"),
    /**
     * 卡片消息
     */
    ACTION_CARD("action_card");

    /**
     * 类型
     */
    private final String type;

    /**
     * 得到钉钉消息类型。<br>
     * 主要如果没有匹配类型则返回{@code DdMessageType.TEXT}
     *
     * @param type 类型
     * @return 类型
     */
    public static DdMessageType getDdMessageType(String type) {
        return Arrays.stream(DdMessageType.values())
                .filter(messageType -> messageType.getType().equals(type))
                .findFirst()
                .orElse(DdMessageType.TEXT);
    }
}
