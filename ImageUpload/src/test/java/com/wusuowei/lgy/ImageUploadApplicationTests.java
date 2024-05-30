package com.wusuowei.lgy;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.FileNotFoundException;

@SpringBootTest
class ImageUploadApplicationTests {

    @Test
    void contextLoads() throws FileNotFoundException {
        //System.err.println(ResourceUtils.getURL("classpath:static/image").getPath());

       // System.err.println(this.getClass().getClassLoader().getResource(""));
        System.err.println(System.getProperty("user.dir"));
        //System.err.println(this.getClass().getResource("/"));
    }

}
