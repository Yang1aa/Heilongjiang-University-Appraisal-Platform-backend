package com.wusuowei.lgy.controller;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class neumap_classifyController {
    @GetMapping("/image")
    public ResponseEntity<Resource> getImage() {
        try {
            // 图片的路径，根据实际情况进行修改
            Path imagePath = Paths.get("/root/algorithm/neumap_classify/outputs/ship_neu.jpg");
            Resource resource = new UrlResource(imagePath.toUri());

            // 确保图片文件实际存在
            if (resource.exists() || resource.isReadable()) {
                return ResponseEntity.ok()
                        .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                        .contentType(MediaType.IMAGE_JPEG) // 根据实际图片类型修改
                        .body(resource);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (MalformedURLException e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
