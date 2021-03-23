package indi.snowmeow.zkojweb;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import indi.snowmeow.zkojweb.util.JwtUtil;
import org.springframework.util.DigestUtils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author snowmeow
 * @date 2020/10/13
 */
public class Test {

    public static void main(String[] args) {

        Map<String, Object> map = new HashMap<>();
        map.put("test", "test");
    }
}
