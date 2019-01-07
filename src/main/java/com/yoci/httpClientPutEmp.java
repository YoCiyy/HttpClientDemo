package com.yoci;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

/**
 * Created by yoci on 2019/1/7.
 */
public class httpClientPutEmp {

    /**
     * httpClient Put 请求
     */
    public static void main(String[] args) throws IOException {

        try (CloseableHttpClient httpclient = HttpClients.createDefault()) {

            //第一步 配置 Post 请求 Url
            HttpPut httpPut = new HttpPut("http://httpbin.org/put");

            //设置post请求参数
            httpPut.setEntity(new StringEntity("Hello, World"));

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

            String responseBody = httpclient.execute(httpPut, responseHandler);
            System.out.println("----------------------------------------");
            System.out.println(responseBody);
        }

    }
}
