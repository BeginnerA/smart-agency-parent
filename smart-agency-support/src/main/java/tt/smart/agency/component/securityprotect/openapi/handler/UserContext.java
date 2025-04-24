package tt.smart.agency.component.securityprotect.openapi.handler;

import lombok.Data;

/**
 * <p>
 * 用户上下文
 * </p>
 *
 * @author MC_Yang
 * @version V1.0
 **/
@Data
public class UserContext {

    /**
     * 公钥（默认使用用户公钥）
     */
    private String publicKey;

    /**
     * 私钥（默认使用用户私钥）
     */
    private String privateKey;

}
