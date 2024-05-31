package com.wusuowei.lgy.controller;

import com.wusuowei.lgy.utils.R;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

@Slf4j
@RestController
public class FileController {

    @Value("${file.upload.path}")
    private String fileUploadPath;

    @PostMapping("/mode")
    public ResponseEntity<String> receiveMode(@RequestParam String mode, @RequestParam String fileName) {
        JsonController jsonController = new JsonController();
        XceptionController xceptionController = new XceptionController();
        switch (mode) {
            case "xception":
                xceptionController.RunPython(fileName);
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
            case "neumap_classify":
                jsonController.write(fileName);
                break;
            default:
                break;
        }
        return null;
    }

    @PostMapping({"/uploadImage"})
    public R uploadImage(@RequestParam("image") MultipartFile image) throws IOException {
        JsonController jsonController = new JsonController();
        XceptionController xceptionController = new XceptionController();
        if (image == null || image.isEmpty()) {
            return R.error("No image file provided");
        }

        // Prepare the file path
        String originalFilename = image.getOriginalFilename();
        String suffixName = originalFilename.substring(originalFilename.lastIndexOf("."));
        String randomFileName = RandomStringUtils.randomAlphanumeric(6);
        String newFileName = randomFileName + suffixName;
        File destinationFile = new File(fileUploadPath, newFileName);

        // Save the image to the file system
        image.transferTo(destinationFile);

        // Assuming that the server is configured to serve images from the 'fileUploadPath'
        String imageUrl = "http://116.63.15.173:8088/file/" + newFileName;

        // Return the URL of the uploaded image
        return R.ok().put("url", imageUrl).put("fileName", newFileName);
    }
}
