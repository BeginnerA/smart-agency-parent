package com.reid.smart.agency.message.sms.mas;

import lombok.Data;

/**
 * <p>
 * 发送结果
 * </p>
 *
 * @author MC_Yang
 * @version V1.0
 **/
@Data
public class SendRes {
    /**
     * 响应状态码
     */
    private String rspcod;
    /**
     * 消息批次号，由云MAS平台生成，用于验证短信提交报告和状态报告的一致性（取值msgGroup）注:如果数据验证不通过msgGroup为空
     */
    private String msgGroup;
    /**
     * 数据校验结果
     */
    private boolean success;
}
