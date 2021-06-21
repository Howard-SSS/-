package com.example.demo;

import com.example.demo.baike.Baidu;
import com.example.demo.baike.Wiki;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class DemoApplicationTests {

    @Test
    void contextLoads() {
        String pageBody = new Wiki().getPageBody("开闭原则");
        System.out.println(pageBody);
    }

}
