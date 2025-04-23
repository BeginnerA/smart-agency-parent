package tt.smart.agency.message.domain.wx.mp.template;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson2.JSONObject;
import lombok.*;
import tt.smart.agency.message.domain.wx.mp.WxMpBaseMessage;
import tt.smart.agency.message.enums.wx.LanguageType;
import tt.smart.agency.message.enums.wx.MiniProgramState;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 微信小程序订阅消息：
 * <a href="https://developers.weixin.qq.com/miniprogram/dev/OpenApiDoc/mp-message-management/subscribe-message/sendMessage.html">详情参考</a>
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
public class WxMpSubscribeMessage extends WxMpBaseMessage {

    /**
     * 接收者 openid
     */
    private String toUser;

    /**
     * 模板 ID
     */
    private String templateId;

    /**
     * 小程序页面路径
     */
    private String page;

    /**
     * 模板数据
     */
    private List<WxTemplateData> templateData;

    /**
     * 跳转小程序类型：developer为开发版；trial为体验版；formal为正式版；默认为正式版
     */
    @Builder.Default
    private String miniProgramState = MiniProgramState.FORMAL.getState();

    /**
     * 进入小程序查看的语言类型，支持zh_CN(简体中文)、en_US(英文)、zh_HK(繁体中文)、zh_TW(繁体中文)，默认为zh_CN
     */
    @Builder.Default
    private String lang = LanguageType.ZH_CN.getType();

    /**
     * 添加模板数据
     *
     * @param datum 模板数据
     * @return this
     */
    public WxMpSubscribeMessage addData(WxTemplateData datum) {
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
        if (StrUtil.isNotBlank(page)) {
            messageJson.put("page", page);
        }
        if (StrUtil.isNotBlank(miniProgramState)) {
            messageJson.put("miniprogram_state", miniProgramState);
        }
        if (StrUtil.isNotBlank(lang)) {
            messageJson.put("lang", lang);
        }
        if (templateData != null) {
            messageJson.put("data", WxTemplateData.getJsonObject(templateData));
        }

        return messageJson.toJSONString();
    }

}
