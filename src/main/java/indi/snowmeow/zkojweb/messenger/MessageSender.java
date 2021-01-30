package indi.snowmeow.zkojweb.messenger;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * 消息发送服务
 * @author snowmeow
 * @date 2021/01/26
 */
@Component
public class MessageSender {

    private static final Logger LOGGER = LogManager.getLogger(MessageSender.class);

    @Autowired
    private JmsMessagingTemplate jmsMessagingTemplate;
    @Value("${zkoj.activemq.queue-request}")
    private String queueName;

    /**
     * TODO
     * */
    public void sendMessage(String destination, Map<String, Object> messageMap) {

        jmsMessagingTemplate.convertAndSend(destination, messageMap);
        LOGGER.info("Send message.");
    }

    /**
     * TODO
     * */
    public void sendMessage(String destination, String messageString) {

        jmsMessagingTemplate.convertAndSend(destination, messageString);
        LOGGER.info("Send message.");
    }

}
