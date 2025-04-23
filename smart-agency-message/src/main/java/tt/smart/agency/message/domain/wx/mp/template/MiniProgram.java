package tt.smart.agency.message.domain.wx.mp.template;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * <p>
 *  小程序
 * </p>
 *
 * @author MC_Yang
 * @version V1.0
 **/
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MiniProgram implements Serializable {

    /**
     * 小程序 appid（该小程序 appid 必须与发模板消息的公众号是绑定关联关系，暂不支持小游戏）
     */
    private String appid;

    /**
     * 小程序的具体页面路径，支持带参数,（示例：index?foo=bar），要求该小程序已发布，暂不支持小游戏
     */
    private String pagePath;

    /**
     * 是否使用 path，否则使用 pagepath。
     */
    @Builder.Default
    private Boolean usePath = false;

    /**
     * 是否使用 pagePath，否则使用 pagepath.
     */
    @Builder.Default
    private Boolean usePagePath = false;

}
