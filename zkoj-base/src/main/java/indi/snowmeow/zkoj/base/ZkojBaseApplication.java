package indi.snowmeow.zkoj.base;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author snowmeow
 */
@SpringBootApplication
@MapperScan("indi.snowmeow.zkoj.base.dao")
//@EnableTransactionManagement
public class ZkojBaseApplication {

    public static void main(String[] args) {
        SpringApplication.run(ZkojBaseApplication.class, args);
    }

}
