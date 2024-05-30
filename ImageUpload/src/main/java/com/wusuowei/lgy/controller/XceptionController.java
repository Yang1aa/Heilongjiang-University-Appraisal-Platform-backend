package com.wusuowei.lgy.controller;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Path;
import java.nio.file.Paths;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import java.net.MalformedURLException;

public class XceptionController {
    public void RunPython(String imageName){
        try {
            // 创建Runtime对象
            Runtime runtime = Runtime.getRuntime();

            // 构建命令行命令
            String pythonCommand = "python3 /root/algorithm/identify/Xception/Xception_test.py /var/www/html/backend/files/"+imageName;

            // 执行命令
            Process process = runtime.exec(pythonCommand);

            // 读取命令输出
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }

            // 等待命令执行完成
            int exitCode = process.waitFor();
            System.out.println(pythonCommand);
            System.out.println("Exit Code: " + exitCode);

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
