package tt.smart.agency.message.domain.wx.cp.builder;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * <p>
 * 企业微信图文消息构建器：
 * <a href="https://developer.work.weixin.qq.com/document/path/90236">详情参考</a>
 * </p>
 *
 * @author YangMC
 * @version V1.0
 **/
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class NewArticle implements Serializable {

    /**
     * 标题，不超过128个字节，超过会自动截断
     */
    private String title;

    /**
     * 描述，不超过512个字节，超过会自动截断
     */
    private String description;

    /**
     * 点击后跳转的链接。
     */
    private String url;

    /**
     * 图文消息的图片链接，支持 JPG、PNG 格式，较好的效果为大图1068*455，小图150*150。
     */
    private String picUrl;

    /**
     * 按钮文字，仅在图文数为1条时才生效。 默认为“阅读全文”， 不超过4个文字，超过自动截断。该设置只在企业微信上生效，微工作台（原企业号）上不生效。
     */
    private String btnText;

    /**
     * 小程序 appid，必须是与当前应用关联的小程序，appid 和 pagepath 必须同时填写，填写后会忽略 url 字段
     */
    private String appId;

    /**
     * 点击消息卡片后的小程序页面，仅限本小程序内的页面。appid 和 pagepath 必须同时填写，填写后会忽略 url 字段
     */
    private String pagePath;

}
