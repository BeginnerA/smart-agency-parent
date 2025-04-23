package tt.smart.agency.message.api.wx;

import tt.smart.agency.message.domain.wx.mp.WxMpBaseMessage;
import tt.smart.agency.message.domain.wx.mp.WxMpMessageSendResult;
import tt.smart.agency.message.domain.wx.mp.template.WxMpSubscribeMessage;
import tt.smart.agency.message.domain.wx.mp.template.WxMpTemplateMessage;

/**
 * <p>
 * 微信公众号消息服务：
 * <a href="https://developers.weixin.qq.com/doc/offiaccount/Message_Management/Receiving_standard_messages.html">公众号消息详情参考</a>
 * <a href="https://developers.weixin.qq.com/miniprogram/dev/OpenApiDoc/mp-message-management/uniform-message/sendUniformMessage.html">小程序消息详情参考</a>
 * </p>
 *
 * @author MC_Yang
 * @version V1.0
 **/
public interface WxMpMessage extends WxMessageBlend<WxMpMessageSendResult, WxMpBaseMessage> {

    /**
     * <p>
     * 推送模板消息：
     * <a href="https://developers.weixin.qq.com/doc/offiaccount/Message_Management/Template_Message_Interface.html">详情参考</a>
     * </p>
     *
     * @param templateMessage 模板消息
     * @return 信息结果
     */
    WxMpMessageSendResult sendTemplateMessage(WxMpTemplateMessage templateMessage);

    /**
     * <p>
     * 推送订阅消息：
     * <a href="https://developers.weixin.qq.com/miniprogram/dev/OpenApiDoc/mp-message-management/subscribe-message/sendMessage.html">详情参考</a>
     * </p>
     *
     * @param subscribeMessage 模板消息
     * @return 信息结果
     */
    WxMpMessageSendResult sendSubscribeMessage(WxMpSubscribeMessage subscribeMessage);

    /**
     * 推送模板消息
     *
     * @param message 消息内容
     * @return 结果
     */
    WxMpMessageSendResult sendTemplateMessage(String message);

    /**
     * 推送订阅消息
     *
     * @param message 消息内容
     * @return 结果
     */
    WxMpMessageSendResult sendSubscribeMessage(String message);
}