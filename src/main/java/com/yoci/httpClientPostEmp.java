package com.yoci;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by yoci on 2019/1/7.
 */
public class httpClientPostEmp {


    /**
     * httpClient Post请求
     */
    public static void main(String[] args) throws IOException {

        try (CloseableHttpClient httpclient = HttpClients.createDefault()) {

            //第一步 配置 Post 请求 Url
            HttpPost httpPost = new HttpPost("http://httpbin.org/post");

            // 装配post请求参数
            List<BasicNameValuePair> list = new ArrayList<BasicNameValuePair>();
            list.add(new BasicNameValuePair("age", "20"));  //请求参数
            list.add(new BasicNameValuePair("name", "zhangsan")); //请求参数
            httpPost.setEntity(new StringEntity("Hello, World"));

            /*
                设置post请求参数
                两个方式:具体参考UrlEncodedFormEntity和StringEntity区别
            */
            httpPost.setEntity(new UrlEncodedFormEntity(list, "UTF-8"));
            //httpPost.setEntity(new StringEntity(list.toString(), "UTF-8"));


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

            //第三步 执行请求
            String responseBody = httpclient.execute(httpPost, responseHandler);
            System.out.println("----------------------------------------");
            System.out.println(responseBody);
        }
    }
}
