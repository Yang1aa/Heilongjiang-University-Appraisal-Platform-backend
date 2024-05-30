package com.wusuowei.lgy.controller;

import com.wusuowei.lgy.utils.R;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
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

    String imageName;

//    @PostMapping("/file")
//    public R uploadFile(@RequestParam("files")  MultipartFile[] multipartFiles) throws IOException {
//        String parentFile = fileUploadPath;//服务器保存图片的路径
//        //如果父文件夹不存在，就创建
//        File parent = new File(parentFile);
//        if (!parent.getParentFile().exists()) {
//            parent.mkdirs();
//        }
//        if(multipartFiles == null){
//            return R.error("文件为空");
//        }
//        for (MultipartFile file : multipartFiles) {
//            String filename = file.getOriginalFilename();  //获取上传图片的文件名，包含后缀
//            String suffixName = filename.substring(filename.lastIndexOf("."));//图片后缀
//            String randomFileName = RandomStringUtils.random(6, true, true);//生成6个字符的随机串
//            String nowName = randomFileName + suffixName;//最后保存在服务器时的文件名
//
//            File file1 = new File(parent, nowName);
//            //将图片保存入服务器
//            file.transferTo(file1);
//        }
//        return R.ok("图片上传成功");
//    }
//
//    @GetMapping("/filelist")
//    public List<String> getURL() {
//        String parentFile = fileUploadPath;//服务器保存图片的路径
//        ArrayList<String> urls = new ArrayList<>();
//        File file = new File(parentFile);
//        File[] list = file.listFiles();
//        if (list == null) {
//            return null;
//        }
//        for (File s : list) {
//            urls.add("http://localhost:8088/file/" + s.getName());
//        }
//        return urls;
//    }

    @PostMapping("/uploadImage")
    public R uploadImage(@RequestParam("image") MultipartFile image) throws IOException {
        JsonController jsonController = new JsonController();
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
//        String imageUrl = "http://localhost:8088/file/" + newFileName;

        String imageUrl = "http://116.63.8.192:8088/file/" + newFileName;
        jsonController.write(newFileName);

        // Return the URL of the uploaded image
        return R.ok().put("url", imageUrl);
    }

}
