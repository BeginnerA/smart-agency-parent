package tt.smart.agency.message.domain;

import com.alibaba.fastjson2.JSONObject;
import com.alibaba.fastjson2.annotation.JSONField;
import lombok.Data;

import java.io.Serializable;

/**
 * <p>
 * 令牌 access_token
 * </p>
 *
 * @author MC_Yang
 * @version V1.0
 **/
@Data
public class AccessToken implements Serializable {

    /**
     * 凭证（唯一的）
     */
    private String ident;
    /**
     * 凭证到期时间
     */
    private long expiresTime = -1;
    /**
     * 令牌
     */
    @JSONField(name="access_token")
    private String accessToken;
    /**
     * 凭证有效时间
     */
    @JSONField(name="expires_in")
    private long expiresIn = -1;
    /**
     * 返回码
     */
    @JSONField(name="errcode")
    private Integer errCode = 0;

    public static AccessToken fromJson(String json) {
        AccessToken accessToken = JSONObject.parseObject(json, AccessToken.class);
        accessToken.setExpiresTime(System.currentTimeMillis() + (accessToken.getExpiresIn() * 1000L));
        return accessToken;
    }

}
