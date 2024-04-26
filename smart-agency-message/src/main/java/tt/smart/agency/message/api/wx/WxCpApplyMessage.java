package tt.smart.agency.message.api.wx;

import tt.smart.agency.message.domain.wx.cp.WxCpMessage;
import tt.smart.agency.message.domain.wx.cp.WxCpMessageSendResult;

/**
 * <p>
 * 企业微信消息服务：
 * <a href="https://developer.work.weixin.qq.com/document/path/90236">详情参考</a>
 * </p>
 *
 * @author YangMC
 * @version V1.0
 **/
public interface WxCpApplyMessage extends WxMessageBlend<WxCpMessageSendResult, WxCpMessage> {

    /**
     * 微信通用消息推送
     *
     * @param message 消息
     * @return 结果
     */
    WxCpMessageSendResult sendMessage(String message);

}