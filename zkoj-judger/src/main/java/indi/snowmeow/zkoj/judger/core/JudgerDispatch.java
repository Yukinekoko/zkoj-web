package indi.snowmeow.zkoj.judger.core;

import indi.snowmeow.zkoj.judger.model.SolutionRequestDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.ExecutorService;

/**
 * @author snowmeow
 * @date 2021/5/8
 */
@Component
public class JudgerDispatch {

    @Autowired
    private SolutionHandle solutionHandle;

    private ExecutorService executorService;

    public void newSolution(SolutionRequestDTO solutionRequestDTO) {
    }

}
