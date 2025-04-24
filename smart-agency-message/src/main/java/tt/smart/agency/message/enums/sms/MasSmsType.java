package tt.smart.agency.message.enums.sms;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * <p>
 * 移动短信类型
 * </p>
 *
 * @author MC_Yang
 * @version V1.0
 **/
@Getter
@AllArgsConstructor
public enum MasSmsType {
    /**
     * 普通内容短信
     */
    CONTENT,
    /**
     * 模板内容短信
     */
    TEMPLATE
}
