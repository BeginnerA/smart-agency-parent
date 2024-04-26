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
@NoArgsConstructor
@AllArgsConstructor
public class MpnewsArticle implements Serializable {

    /**
     * 标题，不超过128个字节，超过会自动截断
     */
    private String title;
    /**
     * 图文消息缩略图的 media_id, 可以通过素材管理接口获得。此处 thumb_media_id 即上传接口返回的 media_id
     */
    private String thumbMediaId;
    /**
     * 图文消息的作者，不超过64个字节
     */
    private String author;
    /**
     * 图文消息点击“阅读原文”之后的页面链接
     */
    private String contentSourceUrl;
    /**
     * 图文消息的内容，支持 html 标签，不超过666 K个字节
     */
    private String content;
    /**
     * 图文消息的描述，不超过512个字节，超过会自动截断
     */
    private String digest;

}
