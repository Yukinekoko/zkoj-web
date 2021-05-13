package indi.snowmeow.zkoj.judger;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author snowmeow
 * @date 2021/5/8
 */
@SpringBootApplication
@MapperScan("indi.snowmeow.judger.dao")
public class ZkojJudgerApplication {

    public static void main(String[] args) {
        SpringApplication.run(ZkojJudgerApplication.class, args);
    }
}
