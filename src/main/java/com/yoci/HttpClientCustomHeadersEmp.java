package com.yoci;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHeaders;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.methods.RequestBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicHeader;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

/**
 * Created by yoci on 2019/1/7.
 */
public class HttpClientCustomHeadersEmp {

    /**
     * HttpClient自定义HTTP头
     */
    public static void main(String[] args)throws IOException {
        // 创建自定义 http headers
        List<Header> defaultHeaders = Arrays.asList(
                new BasicHeader("X-Default-Header", "default header httpclient"));

        // 设置自定义 http headers
        CloseableHttpClient httpclient = HttpClients
                .custom()
                .setDefaultHeaders(defaultHeaders)
                .build();

        try {

            // 配置超时时间
            RequestConfig requestConfig = RequestConfig.custom()
                    .setConnectTimeout(5000)   //设置连接超时时间
                    .setConnectionRequestTimeout(5000) // 设置请求超时时间
                    .setSocketTimeout(5000)
                    .setRedirectsEnabled(true)//默认允许自动重定向
                    .build();

            // 创建自定义 http headers on the http request
            HttpUriRequest request = RequestBuilder.get()
                    .setUri("http://httpbin.org/headers")
                    .setHeader(HttpHeaders.CONTENT_TYPE, "application/json")
                    .setHeader(HttpHeaders.FROM, "https://memorynotfound.com")
                    .setHeader("X-Custom-Header", "custom header http request")
                    .setConfig(requestConfig)
                    .build();

            System.out.println("Executing request " + request.getRequestLine());

            // 创建自定义 response handler
            ResponseHandler<String> responseHandler = response -> {
                int status = response.getStatusLine().getStatusCode();
                if (status >= 200 && status < 300) {
                    HttpEntity entity = response.getEntity();
                    return entity != null ? EntityUtils.toString(entity) : null;
                } else {
                    throw new ClientProtocolException("Unexpected response status: " + status);
                }
            };
            String responseBody = httpclient.execute(request, responseHandler);
            System.out.println("----------------------------------------");
            System.out.println(responseBody);
        } finally {
            httpclient.close();
        }
    }
}
