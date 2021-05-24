package indi.snowmeow.zkoj.judger.mq;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.messaging.SubscribableChannel;

/**
 * @author snowmeow
 * @date 2021/5/21
 */
public interface SolutionRequestBinding {

    @Input("SOLUTION_REQUEST_INPUT")
    SubscribableChannel solutionRequestInput();
}
