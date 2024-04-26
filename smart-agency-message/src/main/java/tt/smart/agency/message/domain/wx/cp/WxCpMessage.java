package tt.smart.agency.message.domain.wx.cp;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import lombok.Data;
import lombok.EqualsAndHashCode;
import tt.smart.agency.message.domain.wx.cp.builder.*;

import java.util.List;

/**
 * <p>
 * 企业微信消息：
 * <a href="https://developer.work.weixin.qq.com/document/path/90236">详情参考</a>
 * </p>
 *
 * @author YangMC
 * @version V1.0
 **/
@Data
@EqualsAndHashCode(callSuper = true)
public class WxCpMessage extends WxCpBaseMessage {

    // ======================================== 图文信息 ========================================
    /**
     * 图文消息，一个图文消息支持1到8条图文
     */
    private List<NewArticle> articles;
    /**
     * 图文消息，一个图文消息支持1到8条图文
     */
    private List<MpnewsArticle> mpArticles;

    // ======================================== 文本信息 ========================================
    /**
     * 消息内容，最长不超过2048个字节，超过将截断（支持 ID 转译）
     */
    private String content;

    // ======================================== 媒体文件信息 ========================================
    /**
     * 媒体文件 ID，可以调用上传临时素材接口获取
     */
    private String mediaId;

    // ======================================== 文本卡片信息 ========================================
    /**
     * 标题，不超过128个字节，超过会自动截断（支持 ID 转译）
     */
    private String title;

    /**
     * 描述，不超过512个字节，超过会自动截断（支持 ID 转译）
     */
    private String description;

    /**
     * 点击后跳转的链接。最长2048字节，请确保包含了协议头(http/https)
     */
    private String url;

    /**
     * 按钮文字。 默认为“详情”， 不超过4个文字，超过自动截断
     */
    private String btnTxt;

    // ======================================== 信息公共参数 ========================================
    /**
     * 表示是否开启id转译，0表示否，1表示是，默认0
     */
    private Boolean enableIdTrans = false;

    /**
     * 表示是否开启重复消息检查，0表示否，1表示是，默认0
     */
    private Boolean enableDuplicateCheck = false;

    /**
     * 表示是否重复消息检查的时间间隔，默认1800s，最大不超过4小时
     */
    private Integer duplicateCheckInterval;

    /**
     * 获取文件消息构建器
     *
     * @return 文件消息构建器
     */
    public static FileMessageBuilder fileMsg() {
        return new FileMessageBuilder();
    }

    /**
     * 获取图片消息构建器
     *
     * @return 图片消息构建器
     */
    public static ImageMessageBuilder imageMsg() {
        return new ImageMessageBuilder();
    }

    /**
     * 获取图片图文消息构建器
     *
     * @return 图文消息构建器
     */
    public static NewsMessageBuilder newsMsg() {
        return new NewsMessageBuilder();
    }

    /**
     * 获取图片图文消息构建器
     *
     * @return 图文消息构建器
     */
    public static MpnewsMessageBuilder mpnewsMsg() {
        return new MpnewsMessageBuilder();
    }

    /**
     * 获取文本卡片消息构建器
     *
     * @return 文本卡片消息构建器
     */
    public static TextCardMessageBuilder textCardMsg() {
        return new TextCardMessageBuilder();
    }

    /**
     * 获取文本消息构建器
     *
     * @return 文本消息构建器
     */
    public static TextMessageBuilder textMsg() {
        return new TextMessageBuilder();
    }

    /**
     * 获取视频消息构建器
     *
     * @return 视频消息构建器
     */
    public static VideoMessageBuilder videoMsg() {
        return new VideoMessageBuilder();
    }

    /**
     * 获取语音消息构建器
     *
     * @return 语音消息构建器
     */
    public static VoiceMessageBuilder voiceMsg() {
        return new VoiceMessageBuilder();
    }

    public String toJson() {
        JSONObject messageJson = new JSONObject();
        messageJson.put("msgtype", this.getMsgType());
        if (StrUtil.isNotBlank(this.getAgentId())) {
            messageJson.put("agentid", this.getAgentId());
        }

        if (StrUtil.isNotBlank(this.getToUser())) {
            messageJson.put("touser", this.getToUser());
        }

        if (StrUtil.isNotBlank(this.getToParty())) {
            messageJson.put("toparty", this.getToParty());
        }

        if (StrUtil.isNotBlank(this.getToTag())) {
            messageJson.put("totag", this.getToTag());
        }

        if (this.getEnableIdTrans()) {
            messageJson.put("enable_id_trans", 1);
        }

        if (this.getSafe()) {
            messageJson.put("safe", 1);
        }

        if (this.getEnableDuplicateCheck()) {
            messageJson.put("enable_duplicate_check", 1);
        }

        if (this.getDuplicateCheckInterval() != null) {
            messageJson.put("duplicate_check_interval", this.getDuplicateCheckInterval());
        }

        this.handleMsgType(messageJson);

        return messageJson.toString();
    }

    /**
     * 按照类型组装消息
     *
     * @param messageJson 消息
     */
    private void handleMsgType(JSONObject messageJson) {
        switch (this.getMsgType()) {
            case TEXT: {
                JSONObject text = new JSONObject();
                text.put("content", this.getContent());
                messageJson.put("text", text);
                break;
            }
            case TEXTCARD: {
                JSONObject textCard = new JSONObject();
                textCard.put("title", this.getTitle());
                textCard.put("description", this.getDescription());
                textCard.put("url", this.getUrl());
                textCard.put("btntxt", this.getBtnTxt());
                messageJson.put("textcard", textCard);
                break;
            }
            case MARKDOWN: {
                JSONObject markdown = new JSONObject();
                markdown.put("content", this.getContent());
                messageJson.put("markdown", markdown);
                break;
            }
            case IMAGE: {
                JSONObject image = new JSONObject();
                image.put("media_id", this.getMediaId());
                messageJson.put("image", image);
                break;
            }
            case FILE: {
                JSONObject file = new JSONObject();
                file.put("media_id", this.getMediaId());
                messageJson.put("file", file);
                break;
            }
            case VOICE: {
                JSONObject voice = new JSONObject();
                voice.put("media_id", this.getMediaId());
                messageJson.put("voice", voice);
                break;
            }
            case VIDEO: {
                JSONObject video = new JSONObject();
                video.put("media_id", this.getMediaId());
                video.put("title", this.getTitle());
                video.put("description", this.getDescription());
                messageJson.put("video", video);
                break;
            }
            case NEWS: {
                JSONObject news = new JSONObject();
                JSONArray articles = new JSONArray();
                for (NewArticle article : this.getArticles()) {
                    JSONObject articleJson = new JSONObject();
                    articleJson.put("title", article.getTitle());
                    articleJson.put("description", article.getDescription());
                    articleJson.put("url", article.getUrl());
                    articleJson.put("picurl", article.getPicUrl());
                    articleJson.put("btntxt", article.getBtnText());
                    articles.add(articleJson);
                }
                news.put("articles", articles);
                messageJson.put("news", news);
                break;
            }
            case MPNEWS: {
                JSONObject mpnews = new JSONObject();
                if (this.getMediaId() != null) {
                    mpnews.put("media_id", this.getMediaId());
                } else {
                    JSONArray articles = new JSONArray();
                    for (MpnewsArticle article : this.getMpArticles()) {
                        JSONObject articleJson = new JSONObject();
                        articleJson.put("title", article.getTitle());
                        articleJson.put("thumb_media_id", article.getThumbMediaId());
                        articleJson.put("author", article.getAuthor());
                        articleJson.put("content_source_url", article.getContentSourceUrl());
                        articleJson.put("content", article.getContent());
                        articleJson.put("digest", article.getDigest());
                        articles.add(articleJson);
                    }
                    mpnews.put("articles", articles);
                }
                messageJson.put("mpnews", mpnews);
                break;
            }
            case TASKCARD: {
                System.out.print("待做：任务卡片消息");
                break;
            }
            case MINIPROGRAM_NOTICE: {
                System.out.print("待做：小程序通知消息");
                break;
            }
            case TEMPLATE_CARD: {
                System.out.print("待做：模板卡片消息");
                break;
            }
            default: {
                System.out.print("待做：默认消息");
            }
        }
    }
}
