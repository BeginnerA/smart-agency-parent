package tt.smart.agency.im.domain;

import lombok.Data;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;
import java.util.Set;

/**
 * <p>
 * 即时通讯消息
 * </p>
 *
 * @author MC_Yang
 * @version V1.0
 **/
@Data
@SuperBuilder
public class ImMessage implements Serializable {

    /**
     * 主题。多个主题通过英文逗号分隔<br>
     * 注意：如果存在主题，那{@code receiver} 和 {@code filterReceiver}参数是无效的。
     */
    private String topic;
    /**
     * 发送方
     */
    private String sender;
    /**
     * 接收方。<br>
     * 注意：如果{@code receiver} 和 {@code filterReceiver}参数都为空时，消息将会推送给所有人，包括自己。
     */
    private Set<String> receiver;
    /**
     * 消息内容
     */
    private String content;
    /**
     * 需要过滤的接收方。<br>
     * 注意：如果{@code receiver}参数同时出现，只会使用{@code receiver}参数中的内容
     */
    private Set<String> filterReceiver;

}
