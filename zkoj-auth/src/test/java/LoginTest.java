import indi.snowmeow.zkoj.auth.ZkojAuthApplication;
import indi.snowmeow.zkoj.auth.model.dto.UserLoginDTO;
import indi.snowmeow.zkoj.auth.service.impl.UserServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author snowmeow
 * @date 2021/4/13
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ZkojAuthApplication.class)
public class LoginTest {

    @Autowired
    UserServiceImpl userService;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Test
    public void loginTest() {
        String username = "zhkuadmin";
        UserLoginDTO userLoginDTO = userService.findByUsername(username);
        System.out.println(userLoginDTO.getUsername());
        System.out.println(userLoginDTO.getPassword());
        System.out.println(userLoginDTO.getRoles());
    }

    @Test
    public void passwordEncode() {
        String password = "084e20fab576b23ce869f9a1780ebd65";
        String encode = passwordEncoder.encode(password);
        System.out.println(encode);
        System.out.println(passwordEncoder.matches(password, encode));
    }
}
