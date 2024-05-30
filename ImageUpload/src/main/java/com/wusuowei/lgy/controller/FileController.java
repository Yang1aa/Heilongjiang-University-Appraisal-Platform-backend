package com.wusuowei.lgy.controller;

import com.wusuowei.lgy.utils.R;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import org.springframework.web.bind.annotation.RestController;
import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author lgy
 */
@Slf4j
@RestController
public class FileController {

    @Value("${file.upload.path}")
    private String fileUploadPath;
    String fileName;
    String modex="";

    @PostMapping("/mode")
    public R receiveMode(@RequestParam String mode) {
        // 处理接收到的mode参数
        JsonController jsonController = new JsonController();
        XceptionController xceptionController = new XceptionController();
        modex = mode;
        switch (modex) {
            case "xception":
                xceptionController.RunPython(fileName);
//                    xceptionController.getImage(newFileName);
            case "neumap_classify":
                jsonController.write(fileName);
            default:
        }
        return R.ok().put("success","ok");
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
        fileName=newFileName;
        File destinationFile = new File(fileUploadPath, newFileName);


        // Save the image to the file system
        image.transferTo(destinationFile);

        // Assuming that the server is configured to serve images from the 'fileUploadPath'
//        String imageUrl = "http://localhost:8088/file/" + newFileName;

        String imageUrl = "http://116.63.15.173:8088/file/" + newFileName;
            // 根据 mode 参数执行相应的逻辑



        // Return the URL of the uploaded image
        return R.ok().put("url", imageUrl);
    }

}
