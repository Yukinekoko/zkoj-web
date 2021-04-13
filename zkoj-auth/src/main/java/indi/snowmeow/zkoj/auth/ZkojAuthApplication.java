package indi.snowmeow.zkoj.auth;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author snowmeow
 * @date 2021/4/13
 */
@SpringBootApplication
@MapperScan("indi.snowmeow.zkoj.auth.dao")
public class ZkojAuthApplication {

    public static void main(String[] args) {
        SpringApplication.run(ZkojAuthApplication.class, args);
    }
}
