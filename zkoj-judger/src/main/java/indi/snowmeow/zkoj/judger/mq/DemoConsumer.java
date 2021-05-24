package indi.snowmeow.zkoj.judger.mq;

import indi.snowmeow.zkoj.judger.core.SolutionHandle;
import indi.snowmeow.zkoj.judger.model.SolutionRequestDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

/**
 * @author snowmeow
 * @date 2021/5/21
 */
@Component
public class DemoConsumer {

    private static final Logger LOGGER = LoggerFactory.getLogger(DemoConsumer.class);

    @Autowired
    SolutionHandle solutionHandle;

    @StreamListener("SOLUTION_REQUEST_INPUT")
    public void onMessage(@Payload SolutionRequestDTO message) {
        LOGGER.info("New solution - solutionId: {}", message.getSolutionId());
        solutionHandle.newSolution(message);
    }
}
