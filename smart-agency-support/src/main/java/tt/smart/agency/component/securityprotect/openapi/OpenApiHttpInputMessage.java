package tt.smart.agency.component.securityprotect.openapi;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpInputMessage;
import org.springframework.lang.NonNull;

import java.io.InputStream;

/**
 * <p>
 * 开放 API HTTP 输入消息
 * </p>
 *
 * @author MC_Yang
 * @version V1.0
 **/
public class OpenApiHttpInputMessage implements HttpInputMessage {
    private final HttpHeaders httpHeaders;
    private final InputStream body;

    public OpenApiHttpInputMessage(HttpInputMessage httpInputMessage, InputStream inputStream) {
        this.httpHeaders = httpInputMessage.getHeaders();
        this.body = inputStream;
    }

    @NonNull
    @Override
    public InputStream getBody() {
        return this.body;
    }

    @NonNull
    @Override
    public HttpHeaders getHeaders() {
        return this.httpHeaders;
    }
}
