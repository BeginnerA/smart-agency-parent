package tt.smart.agency.message.domain.wx.mp;

import lombok.Data;
import lombok.EqualsAndHashCode;
import tt.smart.agency.message.domain.wx.WxBaseMessage;

/**
 * <p>
 * 微信公众号消息基础类
 * </p>
 *
 * @author YangMC
 * @version V1.0
 **/
@Data
@EqualsAndHashCode(callSuper = true)
public class WxMpBaseMessage extends WxBaseMessage {

    /**
     * 设置微信公众号的 appid
     */
    private String appId;

}