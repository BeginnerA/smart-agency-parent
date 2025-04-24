package tt.smart.agency.message.domain.sms.mas;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * <p>
 * 发送请求
 * </p>
 *
 * @author MC_Yang
 * @version V1.0
 **/
@Data
@SuperBuilder
@NoArgsConstructor
public class MasSendReq {
    /**
     * 集团客户名称
     */
    private String ecName;
    /**
     * 用户名
     */
    private String apId;
    /**
     * 密码
     */
    private String secretKey;
    /**
     * 手机号码逗号分隔。(如“18137282928,18137282922,18137282923”)
     */
    private String mobiles;
    /**
     * 模板 ID。在云 MAS 平台创建模板，路径：『短信』→『模板短信』→『模板管理』，创建后提交审核，审核通过将获得模板 ID。
     */
    private String templateId;
    /**
     * params	String	模板变量。格式：[“param1”,“param2”]，无变量模板填[""]。
     */
    private String params;
    /**
     * 发送短信内容
     */
    private String content;
    /**
     * 网关签名编码，必填，签名编码在中国移动集团开通帐号后分配，可以在云 MAS 网页端管理子系统-SMS 接口管理功能中下载。
     */
    private String sign;
    /**
     * 扩展码，根据向移动公司申请的通道填写，如果申请的精确匹配通道，则填写空字符串("")，否则添加移动公司允许的扩展码。
     */
    private String addSerial;
    /**
     * API 输入参数签名结果，签名算法：将 ecName，apId，secretKey，mobiles，content ，sign，addSerial 按照顺序拼接，然后通过 md5(32位小写)计算后得出的值。
     */
    private String mac;
}
