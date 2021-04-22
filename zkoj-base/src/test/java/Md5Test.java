import indi.snowmeow.zkoj.base.ZkojBaseApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.DigestUtils;

/**
 * @author snowmeow
 * @date 2021/4/23
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ZkojBaseApplication.class)
public class Md5Test {

    @Test
    public void password() {
        String password = "zhkutest";
        String encode = DigestUtils.md5DigestAsHex(("zkoj" + password).getBytes());
        System.out.println(encode);
    }
}
