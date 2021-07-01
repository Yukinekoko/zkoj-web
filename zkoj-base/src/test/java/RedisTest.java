import indi.snowmeow.zkoj.base.ZkojBaseApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author snowmeow
 * @date 2021/5/25
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ZkojBaseApplication.class)
public class RedisTest {

    @Autowired
    StringRedisTemplate stringRedisTemplate;

    @Test
    public void test() {
        stringRedisTemplate.opsForValue().append("test1", "nice");
        stringRedisTemplate.opsForValue().append("test2", "nice2");
    }
}
