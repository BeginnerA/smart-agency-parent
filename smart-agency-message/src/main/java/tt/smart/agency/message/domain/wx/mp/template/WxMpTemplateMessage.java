package tt.smart.agency.message.domain.wx.mp.template;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson2.JSONObject;
import lombok.*;
import tt.smart.agency.message.domain.wx.mp.WxMpBaseMessage;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 模板消息：
 * <a href="http://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1433751277&token=&lang=zh_CN">详情参考</a>
 * </p>
 *
 * @author MC_Yang
 * @version V1.0
 **/
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class WxMpTemplateMessage extends WxMpBaseMessage {

    /**
     * 接收者 openid
     */
    private String toUser;

    /**
     * 模板 ID
     */
    private String templateId;

    /**
     * 模板跳转链接。
     * <pre>
     * url 和 miniprogram 都是非必填字段，若都不传则模板无跳转；若都传，会优先跳转至小程序。
     * 开发中可根据实际需要选择其中一种跳转方式即可。当用户的微信客户端版本不支持跳小程序时，将会跳转至 url。
     * </pre>
     */
    private String url;

    /**
     * 跳小程序所需数据，不需跳小程序可不用传该数据
     */
    private MiniProgram miniProgram;

    /**
     * 模板数据
     */
    private List<WxTemplateData> templateData;

    /**
     * 防重入 ID。对于同一个 toUser + client_msg_id, 只推送一条消息,10分钟有效,超过10分钟不保证效果。若无防重入需求，可不填
     */
    private String clientMsgId;

    /**
     * 添加模板数据
     *
     * @param datum 模板数据
     * @return this
     */
    public WxMpTemplateMessage addData(WxTemplateData datum) {
        if (this.templateData == null) {
            this.templateData = new ArrayList<>();
        }
        this.templateData.add(datum);
        return this;
    }

    /**
     * 转 JSON 字符串
     *
     * @return JSON 字符串
     */
    public String toJson() {
        JSONObject messageJson = new JSONObject();
        messageJson.put("touser", toUser);
        messageJson.put("template_id", templateId);
        if (StrUtil.isNotBlank(clientMsgId)) {
            messageJson.put("client_msg_id", clientMsgId);
        }
        if (url != null) {
            messageJson.put("url", url);
        }

        if (miniProgram != null) {
            JSONObject miniProgramJson = new JSONObject();
            miniProgramJson.put("appid", miniProgram.getAppid());
            if (miniProgram.getUsePath()) {
                miniProgramJson.put("path", miniProgram.getPagePath());
            } else {
                miniProgramJson.put("pagepath", miniProgram.getPagePath());
            }
            messageJson.put("miniprogram", miniProgramJson);
        }

        if (templateData != null) {
            messageJson.put("data", WxTemplateData.getJsonObject(templateData));
        }

        return messageJson.toJSONString();
    }

}
