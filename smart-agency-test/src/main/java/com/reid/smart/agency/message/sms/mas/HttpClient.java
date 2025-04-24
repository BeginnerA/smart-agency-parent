package com.reid.smart.agency.message.sms.mas;

import org.jetbrains.annotations.NotNull;

import javax.net.ssl.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.security.cert.X509Certificate;

/**
 * <p>
 * HTTP 客户端
 * </p>
 *
 * @author MC_Yang
 * @version V1.0
 **/
public class HttpClient {
    //这里用到了内部类
    private static class TrustAnyTrustManager implements X509TrustManager {

        @Override
        public void checkClientTrusted(X509Certificate[] chain, String authType) {
        }

        @Override
        public void checkServerTrusted(X509Certificate[] chain, String authType) {
        }

        @Override
        public X509Certificate[] getAcceptedIssuers() {
            return new X509Certificate[]{};
        }
    }

    private static class TrustAnyHostnameVerifier implements HostnameVerifier {
        @Override
        public boolean verify(String hostname, SSLSession session) {
            return true;
        }
    }

    public static String sendPost(String url, String param, String token) throws Exception {
        // PrintWriter out = null;
        // 需要用 outputStreamWriter
        // 新增 SSL 安全信任
        SSLContext sc = SSLContext.getInstance("SSL");
        sc.init(null, new TrustManager[]{new TrustAnyTrustManager()}, new java.security.SecureRandom());
        //end
        OutputStreamWriter out = null;
        BufferedReader in = null;
        StringBuilder result = new StringBuilder();
        try {
            HttpsURLConnection conn = getHttpsUrlConnection(url, token, sc);
            // 获取 URLConnection 对象对应的输出流
            out = new OutputStreamWriter(conn.getOutputStream(), StandardCharsets.UTF_8);
            // out = new PrintWriter(conn.getOutputStream());
            // 发送请求参数
            out.append(param);
            // out.print(param);
            // flush 输出流的缓冲
            out.flush();
            // 定义 BufferedReader 输入流来读取 URL 的响应
            in = new BufferedReader(new InputStreamReader(conn.getInputStream(), StandardCharsets.UTF_8));

            String line;
            while ((line = in.readLine()) != null) {
                result.append(line);
            }
        } catch (Exception e) {
            System.out.println("发送POST请求出现异常！" + e);
            e.printStackTrace();
        }
        // 使用 finally 块来关闭输出流、输入流
        finally {
            try {
                if (out != null) {
                    out.close();
                }
                if (in != null) {
                    in.close();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        return result.toString();
    }

    private static @NotNull HttpsURLConnection getHttpsUrlConnection(String url, String token, SSLContext sc) throws IOException, URISyntaxException {
        URI uri = new URI(url);
        URL realUrl = uri.toURL();
        //打开和URL之间的连接
        HttpsURLConnection conn = (HttpsURLConnection) realUrl.openConnection();
        //新增conn连接属性
        conn.setSSLSocketFactory(sc.getSocketFactory());
        conn.setHostnameVerifier(new TrustAnyHostnameVerifier());
        //end
        //设置通用的请求属性
        conn.setRequestProperty("Accept", "application/json");
        conn.setRequestProperty("connection", "Keep-Alive");
        conn.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1; SV1)");
        conn.setRequestProperty("Content-Type", "application/json");
        conn.setRequestProperty("Authorization", token);
        //发送POST请求必须设置如下两行
        conn.setDoOutput(true);
        conn.setDoInput(true);
        return conn;
    }

}
