package indi.snowmeow.zkoj.judger;

import indi.snowmeow.zkoj.judger.mq.SolutionRequestBinding;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.stream.annotation.EnableBinding;

/**
 * @author snowmeow
 * @date 2021/5/8
 */
@SpringBootApplication
@MapperScan("indi.snowmeow.zkoj.judger.dao")
@EnableBinding(SolutionRequestBinding.class)
public class ZkojJudgerApplication {

    public static void main(String[] args) {
        SpringApplication.run(ZkojJudgerApplication.class, args);
    }
}
