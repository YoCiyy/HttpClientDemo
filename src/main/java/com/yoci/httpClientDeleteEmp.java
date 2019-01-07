package com.yoci;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

/**
 * Created by yoci on 2019/1/7.
 */
public class httpClientDeleteEmp {

    /**
     * httpClient Delete 请求
     */
    public static void main(String[] args) throws IOException {

        try (CloseableHttpClient httpclient = HttpClients.createDefault()) {

            //第一步 配置 Delete 请求 Url
            HttpDelete httpDelete = new HttpDelete("http://httpbin.org/delete");

            System.out.println("Executing request " + httpDelete.getRequestLine());

            //第二步 创建一个自定义的 response handler
            ResponseHandler<String> responseHandler = new ResponseHandler<String>() {
                @Override
                public String handleResponse(HttpResponse response) throws IOException {
                    int status = response.getStatusLine().getStatusCode();
                    if (status >= 200 && status < 300) {
                        HttpEntity entity = response.getEntity();
                        return entity != null ? EntityUtils.toString(entity) : null;
                    } else {
                        throw new ClientProtocolException("Unexpected response status: " + status);
                    }
                }
            };

            String responseBody = httpclient.execute(httpDelete, responseHandler);
            System.out.println("----------------------------------------");
            System.out.println(responseBody);
        }
    }
}
