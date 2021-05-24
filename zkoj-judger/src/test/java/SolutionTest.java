import indi.snowmeow.zkoj.judger.ZkojJudgerApplication;
import indi.snowmeow.zkoj.judger.core.SolutionHandle;
import indi.snowmeow.zkoj.judger.model.SolutionRequestDTO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author snowmeow
 * @date 2021/5/20
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ZkojJudgerApplication.class)
public class SolutionTest {

    @Autowired
    SolutionHandle solutionHandle;

    @Test
    public void test() {
        SolutionRequestDTO solutionRequest = new SolutionRequestDTO();
        solutionRequest.setSolutionId(126L);
        solutionRequest.setProblemId(2L);
        solutionRequest.setLanguageId(4L);
        solutionRequest.setProblemVersion((short) 1);
        solutionRequest.setTimeLimit(5000);
        solutionRequest.setMemoryLimit(500);
        solutionHandle.newSolution(solutionRequest);
    }
}
