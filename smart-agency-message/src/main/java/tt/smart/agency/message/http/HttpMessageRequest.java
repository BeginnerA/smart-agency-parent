package tt.smart.agency.message.http;

import cn.hutool.http.HttpGlobalConfig;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.Method;

/**
 * <p>
 * HTTP 请求
 * </p>
 *
 * @author YangMC
 * @version V1.0
 **/
public class HttpMessageRequest {

    protected static final String OK = "OK";
    /**
     * HTTPS 请求前缀
     */
    protected static final String HTTPS_PREFIX = "https://";
    /**
     * JAVA 时间格式
     */
    protected static final String JAVA_DATE = "yyyy-MM-dd'T'HH:mm:ss'Z'";
    /**
     * Content-Type
     */
    protected static final String FROM_URLENCODED = "application/x-www-form-urlencoded";
    protected static final String FROM_JSON = "application/json; charset=utf-8";

    /**
     * 执行 HTTP 请求
     *
     * @param httpRequestBase HTTP 请求
     * @return 结果
     */
    public String execute(HttpRequestBase httpRequestBase) {
        Method method = httpRequestBase.getMethod();
        HttpRequest request = HttpRequest.of(httpRequestBase.getWithAccessTokenUrl()).method(method);
        request.header(httpRequestBase.getHeaders());
        request.body(httpRequestBase.getBody());
        request.form(httpRequestBase.getParamMap());
        request.cookie(httpRequestBase.getCookie());
        request.setRest(httpRequestBase.isRest());
        request.timeout(HttpGlobalConfig.getTimeout());
        return request.execute().body();
    }

}