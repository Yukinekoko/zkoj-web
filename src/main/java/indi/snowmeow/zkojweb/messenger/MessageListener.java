package indi.snowmeow.zkojweb.messenger;

import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

/**
 * @author snowmeow
 * @date 2021/01/26
 */
@Component
public class MessageListener {

    @JmsListener(destination="${zkoj.activemq.queue-response}", containerFactory="queueListener")
    public void test(String message) {

        System.out.println("Message ------ >>> " + message);

    }

}
