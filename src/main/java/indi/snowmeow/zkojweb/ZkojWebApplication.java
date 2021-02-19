package indi.snowmeow.zkojweb;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @author snowmeow
 */
@SpringBootApplication
@EnableJms
@EnableTransactionManagement
public class ZkojWebApplication {



    public static void main(String[] args) {
        SpringApplication.run(ZkojWebApplication.class, args);
    }

}
