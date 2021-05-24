package indi.snowmeow.zkoj.base;

import indi.snowmeow.zkoj.base.mq.SolutionMessageBinding;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @author snowmeow
 */
@SpringBootApplication
@MapperScan("indi.snowmeow.zkoj.base.dao")
@EnableTransactionManagement
@EnableBinding(SolutionMessageBinding.class)
public class ZkojBaseApplication {

    public static void main(String[] args) {
        SpringApplication.run(ZkojBaseApplication.class, args);
    }

}
