package indi.snowmeow.zkojweb.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Value;

import java.util.Date;
import java.util.UUID;

/** JwtUtils
 * @author snowmeow
 * @date 2021/01/26
 */
public class JwtUtil {

    /** 加盐值 */
    @Value("${zkoj.jwt-salt}")
    private static String SALT;
    /** 加密方式 */
    private static Algorithm algorithm = Algorithm.HMAC256(SALT);
    /** 默认登录时间30分钟 */
    private static final long DEFAULT_TTL = 1000 * 60 * 30;
    /** 发行者 */
    @Value("${zkoj.jwt-issuer}")
    private static String ISSUER;

    /** 生成jwt token
     * @param subject username
     * @param ttlMillis 有效时间
     * @param role role信息
     * @return jwt-token
     * */
    static public String encode(String subject, long ttlMillis, String role) {

        long nowMillis = System.currentTimeMillis();
        JWTCreator.Builder builder= JWT.create();
        //发行者
        builder.withIssuer(ISSUER)
                //发行对象
                .withSubject(subject)
                //随机ID
                .withJWTId(UUID.randomUUID().toString())
                //发行时间
                .withIssuedAt(new Date(nowMillis))
                //过期时间
                .withExpiresAt(new Date(nowMillis + ttlMillis))
                //角色信息
                .withClaim("role", role);

        return builder.sign(algorithm);


    }

    /**
     * 生成默认的jwt-token
     * @param subject - subject字段，即username
     * @return jwt-token
     * */
    static public String encode(String subject) {
        return encode(subject, DEFAULT_TTL, "NORMAL");
    }

    /**
     * 生成默认的jwt-token，附带role信息
     * @param subject - subject字段，即username
     * @param role - role
     * @return jwt-token
     * */
    static public String encode(String subject, String role) {
        return encode(subject, DEFAULT_TTL, role);
    }

    /** 验证jwt token
     * @param token jwt-token
     * @return 验证是否通过
     * */
    static public boolean verify(String token) {
        try{
            JWTVerifier verifier = JWT.require(algorithm)
                    .withIssuer(ISSUER)
                    .build();
            verifier.verify(token);
        } catch (JWTVerificationException e) {
            // 过期或是信息有误
            return false;
        }
        return true;
    }

    /**
     * 获取subject字段,即username
     * @param token - token
     * @return subject值 - username
     * */
    static public String getSubject(String token) {

        try {
            DecodedJWT jwt = JWT.decode(token);
            return jwt.getSubject();
        } catch (JWTDecodeException e) {
            return null;
        }
    }

    /**
     * 获取role值
     * @param token - token
     * @return role值
     * */
    static public String getRole(String token) {

        try {
            DecodedJWT jwt = JWT.decode(token);
            return jwt.getClaim("role").asString();
        } catch (JWTDecodeException e) {
            return null;
        }
    }
    /**
     * 刷新一个默认时间的JWT TOKEN
     * @param token 旧的jwt token
     * @return 刷新后的新token
     * */
    static public String refreshToken(String token) {

        DecodedJWT jwt = JWT.decode(token);
        String subject = jwt.getSubject();
        String role = jwt.getClaim("role").asString();
        if(role != null) {
            return encode(subject, role);
        } else {
            return encode(subject);
        }
    }

    /**
     * 判断token剩余有效时间是否大于ttl值
     * @param token token
     * @param ttl ttl值
     * @return token有效时间是否大于ttl值
     * */
    static public boolean verifyTime(String token, long ttl) {
        DecodedJWT jwt = JWT.decode(token);
        Date expirationDate = jwt.getExpiresAt();
        Date nowDate = new Date();
        long residue = expirationDate.getTime() - nowDate.getTime();
        return residue >= ttl;

    }

}
