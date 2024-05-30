package com.wusuowei.lgy.controller;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.ResponseEntity;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import com.google.gson.Gson;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@RestController
public class JsonController {
    private JsonController DirectoryReader;
    private JsonController JsonConverter;

    @GetMapping("/get-json")
    public ResponseEntity<String> getJsonFile() {
        // 文件路径，根据您的实际文件位置进行修改
        String filePath = "/root/algorithm/neumap_classify/outputs/output.json";

        try {
            // 读取文件内容为字符串
            String content = new String(Files.readAllBytes(Paths.get(filePath)));
            // 返回JSON字符串以及状态码200 OK
            return ResponseEntity.ok().body(content);
        } catch (IOException e) {
            // 处理文件读取异常，例如文件不存在
            e.printStackTrace();
            // 返回错误信息及状态码500 Internal Server Error
            return ResponseEntity.internalServerError().body("{\"error\": \"Error reading JSON file: " + e.getMessage() + "\"}");
        }
    }

    public void write(String imageName) {
        //FileController fileController = new FileController();
        // 准备要写入的JSON数据
        Map<String, String> data = new HashMap<>();
        data.put("model_weights_path", "./resnet18_weights.pth");
        data.put("image_path", "/var/www/html/backend/files/" + imageName);
        data.put("out_image_path", "./outputs");
        data.put("json_path", "./outputs/output.json");

        // 创建ObjectMapper实例用于处理JSON
        ObjectMapper mapper = new ObjectMapper();

        // 目标文件路径
        String path = "/root/algorithm/neumap_classify/config.json";

        try {
            // 将JSON数据写入文件，如果文件已存在则会被覆盖
            mapper.writeValue(new File(path), data);
            System.out.println("JSON file has been created/overwritten successfully!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

//    public void writeXception(String imageName) {
//        //FileController fileController = new FileController();
//        // 准备要写入的JSON数据
//        Map<String, String> data = new HashMap<>();
//        data.put("output_path", "/root/algorithm/Xception");
//        data.put("json_path", "/root/algorithm/Xception/outputs/output.json");
//        data.put("model_weights_path", "/root/algorithm/Xception/Xception.h5");
//
//        // 创建ObjectMapper实例用于处理JSON
//        ObjectMapper mapper = new ObjectMapper();
//
//        // 目标文件路径
//        String path = "/root/algorithm/Xception/config.json";
//
//        try {
//            // 将JSON数据写入文件，如果文件已存在则会被覆盖
//            mapper.writeValue(new File(path), data);
//            System.out.println("JSON file has been created/overwritten successfully!");
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//    获取模型名称

    @GetMapping("/get-Xception-json")
    public ResponseEntity<String> getJsonFile2() {
        // 文件路径，根据您的实际文件位置进行修改
        String filePath = "/root/algorithm/identify/Xception/outputs/output.json";

        try {
            // 读取文件内容为字符串
            String content = new String(Files.readAllBytes(Paths.get(filePath)));
            // 返回JSON字符串以及状态码200 OK
            return ResponseEntity.ok().body(content);
        } catch (IOException e) {
            // 处理文件读取异常，例如文件不存在
            e.printStackTrace();
            // 返回错误信息及状态码500 Internal Server Error
            return ResponseEntity.internalServerError().body("{\"error\": \"Error reading JSON file: " + e.getMessage() + "\"}");
        }
    }

    @Service
    public class FileService {
        private static final String ROOT_DIRECTORY = "/root/algorithm";

        public Map<String, Object> getFilesHierarchy() {
            File rootDir = new File(ROOT_DIRECTORY);
            return getDirectoryStructure(rootDir);
        }

        private Map<String, Object> getDirectoryStructure(File dir) {
            Map<String, Object> structure = new HashMap<>();

            if (dir.exists() && dir.isDirectory()) {
                File[] subDirs = dir.listFiles(File::isDirectory);
                if (subDirs != null) {
                    for (File subDir : subDirs) {
                        structure.put(subDir.getName(), getDirectoryStructure(subDir));
                    }
                }
            }

            return structure;
        }
    }
    @RestController
    @RequestMapping("/api/files")
    public class FileController {
        @Autowired
        private FileService fileService;

        @GetMapping("/hierarchy")
        public Map<String, Object> getFilesHierarchy() {
            return fileService.getFilesHierarchy();
        }
    }
}





