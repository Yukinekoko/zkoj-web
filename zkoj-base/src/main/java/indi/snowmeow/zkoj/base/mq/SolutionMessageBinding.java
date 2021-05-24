package indi.snowmeow.zkoj.base.mq;

import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;

/**
 * @author snowmeow
 * @date 2021/5/21
 */
public interface SolutionMessageBinding {

    @Output("SOLUTION_REQUEST_INPUT")
    MessageChannel solutionOutput();
}
