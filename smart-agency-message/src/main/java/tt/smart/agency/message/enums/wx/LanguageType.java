package tt.smart.agency.message.enums.wx;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * <p>
 *  语言类型
 * </p>
 *
 * @author MC_Yang
 * @version V1.0
 **/
@Getter
@AllArgsConstructor
public enum LanguageType {
    /**
     * 简体中文
     */
    ZH_CN("zh_CN"),
    /**
     * 英文
     */
    EN_US("en_US"),
    /**
     * 繁体中文
     */
    ZH_HK("zh_HK"),
    /**
     * 繁体中文
     */
    ZH_TW("zh_TW");

    /**
     * 类型
     */
    private final String type;

}
