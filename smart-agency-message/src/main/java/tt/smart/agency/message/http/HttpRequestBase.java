package tt.smart.agency.message.http;

import cn.hutool.http.Method;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * HTTP
 * </p>
 *
 * @author YangMC
 * @version V1.0
 **/
@Data
@SuperBuilder
public abstract class HttpRequestBase implements Serializable {
    /**
     * 请求
     */
    @Builder.Default
    protected Method method = Method.POST;
    /**
     * 存储头信息
     */
    protected Map<String, List<String>> headers;
    /**
     * 存储表单数据
     */
    protected Map<String, Object> paramMap;
    /**
     * Cookie
     */
    protected String cookie;
    /**
     * 是否是 REST 请求模式
     */
    protected boolean isRest;

    /**
     * 得到携带访问令牌的 URL
     *
     * @return 携带访问令牌的 URL
     */
    public abstract String getWithAccessTokenUrl();

    /**
     * 得到主体 body
     *
     * @return body 字符串
     */
    public abstract String getBody();
}