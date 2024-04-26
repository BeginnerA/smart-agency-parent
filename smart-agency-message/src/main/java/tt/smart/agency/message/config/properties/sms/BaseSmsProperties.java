package tt.smart.agency.message.config.properties.sms;

import cn.hutool.core.util.StrUtil;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import tt.smart.agency.message.exception.SmsMessageException;

/**
 * <p>
 * 基础短信配置
 * </p>
 *
 * @author YangMC
 * @version V1.0
 **/
@Data
@SuperBuilder
@NoArgsConstructor
public class BaseSmsProperties implements SupplierConfig {

    /**
     * Access Key
     */
    private String accessKey;

    /**
     * Access Key Secret
     */
    private String accessKeySecret;

    /**
     * 短信签名
     */
    private String signature;

    /**
     * 模板 ID
     */
    private String templateId;

    /**
     * 检查配置完整性。<br>
     * 抛出{@link SmsMessageException}异常
     */
    public void checkIntegrity() {
        if (StrUtil.isBlank(accessKey) || StrUtil.isBlank(accessKeySecret)) {
            throw new SmsMessageException("配置完整性检查未通过，参数[accessKey、accessKeySecret]是不行的");
        }
    }
}
