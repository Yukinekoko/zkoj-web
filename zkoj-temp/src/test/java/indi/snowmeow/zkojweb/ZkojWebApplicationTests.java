package indi.snowmeow.zkojweb;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ZkojWebApplicationTests {
    @Value("${path}")
    String path;

    @Test
    void contextLoads() {
        System.out.println("Path+" + path);
    }

}
