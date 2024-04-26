package tt.smart.agency.message.component.domain;

import cn.hutool.core.util.StrUtil;
import lombok.Builder;
import lombok.Data;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * <p>
 * 消息
 * </p>
 *
 * @author YangMC
 * @version V1.0
 **/
@Data
@Builder
public class Message {

    /**
     * 消息内容
     */
    private String content;

    /**
     * 接收方列表。<br>
     * 只有推送平台是短信时才会使用
     */
    private Set<String> addresseeSet;

    /**
     * 设置接收方
     *
     * @param addressee 接收方
     */
    public void setAddressee(String addressee) {
        if (StrUtil.isNotEmpty(addressee)) {
            Set<String> addressees = Arrays.stream(addressee.split(",")).collect(Collectors.toSet());
            if (addresseeSet == null) {
                addresseeSet = addressees;
            } else {
                addresseeSet.addAll(addressees);
            }
        }
    }
}