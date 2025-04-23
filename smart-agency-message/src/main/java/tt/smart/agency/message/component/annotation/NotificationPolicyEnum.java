package tt.smart.agency.message.component.annotation;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * <p>
 * 通知政策枚举
 * </p>
 *
 * @author MC_Yang
 * @version V1.0
 **/
@Getter
@AllArgsConstructor
public enum NotificationPolicyEnum {
    /**
     * 执行之前通知
     */
    BEFORE,
    /**
     * 执行之后通知
     */
    AFTER
}