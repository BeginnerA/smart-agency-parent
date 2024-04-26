package tt.smart.agency.message.enums.wx;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * <p>
 *  小程序状态
 * </p>
 *
 * @author YangMC
 * @version V1.0
 **/
@Getter
@AllArgsConstructor
public enum MiniProgramState {
    /**
     * 开发版
     */
    DEVELOPER("developer"),
    /**
     * 体验版
     */
    TRIAL("trial"),
    /**
     * 正式版
     */
    FORMAL("formal");

    /**
     * 状态
     */
    private final String state;

}
