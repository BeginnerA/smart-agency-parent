package com.reid.smart.agency.message.component;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import tt.smart.agency.message.component.config.MessagePushConfig;
import tt.smart.agency.message.component.domain.MessageTemplateConfig;
import tt.smart.agency.message.domain.dd.DdMessage;
import tt.smart.agency.message.domain.wx.mp.template.WxMpTemplateMessage;
import tt.smart.agency.message.domain.wx.mp.template.WxTemplateData;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 消息推送配置
 * </p>
 *
 * @author MC_Yang
 * @version V1.0
 **/
@Configuration
public class MsgTemplateConfig {

    @Bean
    public MessagePushConfig messagePushConfig() {
        return notificationStrategyClassName -> {
            List<MessageTemplateConfig> messageTemplateConfigs = new ArrayList<>();
            if ("TestDdNotificationStrategy".equals(notificationStrategyClassName)) {
                // 文本消息
                DdMessage textMsg = DdMessage.textMsg()
                        .agentId("2695452363")
                        .useridList("manager1374")
                        .content("你的快递已到，请携带工卡前往邮件中心领取。出发前可查看<a href=\"https://oa.dingtalk.com\">邮件中心视频实况</a>，聪明避开排队。")
                        .build();
                MessageTemplateConfig config = MessageTemplateConfig.builder()
                        .content(textMsg.toJson()).platform("DD_APPLY")
                        .build();
                messageTemplateConfigs.add(config);
            } else if ("TestSmsNotificationStrategy".equals(notificationStrategyClassName)) {
                MessageTemplateConfig config = MessageTemplateConfig.builder()
                        .content("内容").recipients("182xxxxxxxx").platform("ALIBABA")
                        .thirdTemplateId("SMS_462270594")
                        .build();
                messageTemplateConfigs.add(config);
            } else if ("TestWxNotificationStrategy".equals(notificationStrategyClassName)) {
                WxMpTemplateMessage templateMessage = WxMpTemplateMessage.builder()
                        .toUser("oYetG6kNgutckVJ234X62uDsI35k")
                        .templateId("Tdy8DL8U-XWJowkINe84yRgMlQvpC4F82EaWhZ1vbCw")
                        .url("https://www.baidu.com/")
                        .templateData(foundTemplateData())
                        .build();
                MessageTemplateConfig config = MessageTemplateConfig.builder()
                        .content(templateMessage.toJson()).platform("mp_template")
                        .build();
                messageTemplateConfigs.add(config);
            }
            return messageTemplateConfigs;
        };
    }

    private List<WxTemplateData> foundTemplateData() {
        List<WxTemplateData> data = new ArrayList<>();
        data.add(new WxTemplateData("keyword1", "未来科技股份有限公司"));
        data.add(new WxTemplateData("keyword2", "原子弹维护保养合同"));
        data.add(new WxTemplateData("keyword3", "原子弹保养"));
        data.add(new WxTemplateData("keyword4", "原子弹表面积灰，请立刻马上进行擦拭"));
        data.add(new WxTemplateData("keyword5", "法外狂徒"));
        return data;
    }

}
