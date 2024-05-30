package com.wusuowei.lgy.controller;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

public class XceptionController {
    public static void main(String[] args) throws IOException {
        // 定义Flask服务器的URL，更新为您的服务器IP地址
        String url = "http://your_server_ip:5000/process_image";
        // 定义要上传的图片文件，更新为您的图片文件路径
        File imageFile = new File("/path/to/your/image.png");

        // 创建一个默认的HttpClient实例
        CloseableHttpClient httpClient = HttpClients.createDefault();
        // 创建一个HttpPost请求，指向Flask服务器的URL
        HttpPost uploadFile = new HttpPost(url);

        // 使用MultipartEntityBuilder创建一个多部分表单请求
        MultipartEntityBuilder builder = MultipartEntityBuilder.create();
        builder.addBinaryBody(
                "image", // 表单字段名
                Files.readAllBytes(imageFile.toPath()), // 读取图片文件的字节内容
                ContentType.APPLICATION_OCTET_STREAM, // 设置内容类型为二进制流
                imageFile.getName() // 设置文件名
        );

        // 构建多部分表单请求实体
        HttpEntity multipart = builder.build();
        // 将多部分表单请求实体设置到HttpPost请求中
        uploadFile.setEntity(multipart);

        // 执行HttpPost请求，并获取响应
        CloseableHttpResponse response = httpClient.execute(uploadFile);
        // 获取响应实体
        HttpEntity responseEntity = response.getEntity();
        // 将响应实体转换为字符串
        String responseString = EntityUtils.toString(responseEntity, "UTF-8");

        // 打印响应字符串
        System.out.println(responseString);
        // 关闭HttpClient实例
        httpClient.close();
    }
}
