package com.victor2022.httpclient;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

/**
 * @author: victor2022
 * @date: 2022/4/22 下午9:50
 * @description: 测试httpclient
 */
public class TestHttpClient {

    public static void main(String[] args) {
        testGetNoParam("https://www.baidu.com");
//        testGetWithParam();
    }

    /**
     * @return: void
     * @author: victor2022
     * @date: 2022/4/22 下午9:51
     * @description: 使用httpclient访问对应网站
     * 1. 创建客户端
     * 2. 创建请求地址
     * 3. 发起请求
     * 4. 处理响应结果
     */
    public static void testGetNoParam(String url){
        HttpClient client;
        try {
            // 1
            client = HttpClients.createDefault();
            // 2
//            HttpGet get = new HttpGet("http://localhost:8081/test/testGet");
            HttpGet get = new HttpGet(url);
            // 3
            HttpResponse response = client.execute(get);
            // 获取相应体
            HttpEntity entity = response.getEntity();
            // 4，使用工具类解析响应体
            String resString = EntityUtils.toString(entity,"UTF-8");
            System.out.println(resString);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void testGetWithParam(){
        HttpClient client;
        try {
            // 1
            client = HttpClients.createDefault();
            // 2
            HttpGet get = new HttpGet("http://localhost:8081/test/testGet/parameter");
            // 3
            HttpResponse response = client.execute(get);
            // 获取相应体
            HttpEntity entity = response.getEntity();
            // 4，使用工具类解析响应体
            String resString = EntityUtils.toString(entity,"UTF-8");
            System.out.println(resString);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
