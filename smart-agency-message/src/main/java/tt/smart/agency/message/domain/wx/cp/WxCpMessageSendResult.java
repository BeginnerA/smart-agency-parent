package tt.smart.agency.message.domain.wx.cp;

import com.alibaba.fastjson2.annotation.JSONField;
import lombok.Data;
import lombok.EqualsAndHashCode;
import tt.smart.agency.message.domain.wx.WxBaseMessageSendResult;

/**
 * <p>
 * 企业微信消息推送结果
 * </p>
 *
 * @author MC_Yang
 * @version V1.0
 **/
@Data
@EqualsAndHashCode(callSuper = true)
public class WxCpMessageSendResult extends WxBaseMessageSendResult {

    /**
     * 不合法的 userId，不区分大小写，统一转为小写
     */
    @JSONField(name = "invaliduser")
    private String invalidUser;

    /**
     * 不合法的 partyId
     */
    @JSONField(name = "invalidparty")
    private String invalidParty;

    /**
     * 不合法的标签 ID
     */
    @JSONField(name = "invalidtag")
    private String invalidTag;

    /**
     * 没有基础接口许可(包含已过期)的用户 ID
     */
    @JSONField(name = "unlicenseduser")
    private String unlicensedUser;

    /**
     * 仅消息类型为“按钮交互型”，“投票选择型”和“多项选择型”的模板卡片消息返回，应用可使用 response_code 调用更新模版卡片消息接口，24小时内有效，且只能使用一次
     */
    @JSONField(name = "response_code")
    private String responseCode;

}
