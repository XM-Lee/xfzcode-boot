package com.xfzcode.utils;

import cn.hutool.crypto.asymmetric.RSA;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;

import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author: XMLee
 * @Date: 2023/4/10 17:50
 * @Description:
 */
public class JwtUtils {

    private static final String SECRET = "X22M12MC20YY20CODE1745";

    //私钥和公钥
    private static final String PRIVATE_KEY_STR = "MIIEvAIBADANBgkqhkiG9w0BAQEFAASCBKYwggSiAgEAAoIBAQCbXiaN/K6Eu3AGDc8bMPraKW7mCu3CUg0csxaGwQL3o/LAKu96QtXVNP4QK7HT9DxfjVRSqOGegJvqgTwd9SgT+JPM6unsHAuL7WbJaO7l3aqzEr++1r/Xorg01fFrsVMaUbXkWMI775s00M49ychhFpmlpB7hd1QrXsCTknClwStH8JYAbYqTse7tI7fydO6DNKYiZd7zXpF+73qKdKOIuTI7Fwpxm7tTuwOfpL47sCxz8aqe2lEIaCLedM3Pu//iYz65sdrENzQ6IonKzbhqh/QnqcqAVxFmKuijCYUwexK1uw4jyzosgW6ZXTzco1Cupn4x1prHgEG6/z5vRoV3AgMBAAECggEAWbw7UG7M7WTFAvoe0HmtqSudHcjaIg35/MWDETmRWS6cGlmyD09i6c29n+FcyCqcZCPgvA1GoqFtbDhQXI0oJMDBvO1BRlm8YYkwgMvWj3VEDkGZ9cNa8lTNR9b5pVW5xc3D3KtXI/70CcFAHXfaABlpjL4KB/c/6fzEBXfjEBhknbCwLLHyBlJQGHvZHuKFFJWKvV+8vMkj4k1Y/Ar4uapIKx/Jk98dg1I8X4FZACAvNrVBGg3bDka4niQ4AjXXPYmuhE9FBcLrIAe4MmzQJWz9nWkfBSY91ZB1n8zUuphd5DT+ImZ1BFex421jIbt7QuSmY3GtCVXIzevkMx/yoQKBgQDo2JVGpHNmfSlsfcmIsEm4ubHBySThco7l7HIjBrUWRJR+FOdVuRRiYPjJoRUM5V9PfTDX5KCyecokdGq2I7cP/hOAocyOD8fb5jq4LtezGSh3Q7F55yjrFOGkrfjnUaBduWetHej198t03AOH3+MEWeU2hyrB61eJxQh/ni5eswKBgQCq0UBvHB5KxYj66K8leVTs955T+b2wZjuUOr/Qs5PpGxxwsntr+nOW78jbRV+rs0LTxgBDWkatptE9OY4mPKhhPzq/z64Mh12QvWRXShDJHELDq9gXlTjXpkU2iZFbZ+VjtyHiOx6gAQIdKFlPmYEKyxWRTbe4Un7k5bo9ulCgLQKBgCknNmLw59CCaJLbPIZjGfSDJW6pnVIXTDhI7PdR1SmlX1VVXnmhJ4AnwXDpfm30ED9dUgemSdyhd9+vj7i3YUB7WHkQn7WwBlPQQiqRZOArnyJNHY39FBdQbn0LnoVLwPsvrKpFMgpfDF+QDzOxl08KkF9tzlnNvYAFCfZ8P5fPAoGAfoIVH3UKtkPP7EvSE4XlsM6/EuB+bRt5+pD8KLwmyliK2qhFIjZNzzZtYn/hmA9eaetVnIxKlidbBeYPciAwcD1c7FwKQjsqv5yUqNUw01E73SDzHJwmiggZcIe4AKNUz2H9FUjFqKi2gRO1+W7tlCnOIlwVucxXMH03TqojNk0CgYArNvMU6LK247Qr/VaKIDri8BVRUHQ5gdcvy/5Esbqv5Pq6QMwSTp0IH+/h/d+UrHZVUxECwGtY0qjczfa7UjxzVVaBkUqqwQvyi/6KS+7sjbCX/cmqj92OoNdCbOJCpHMGctLmEEfJoFvCW+Y56USjJ9zQOrWllUzjnFhW0V5GWA==";
    private static final String PUBLIC_KEY_STR = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAm14mjfyuhLtwBg3PGzD62ilu5grtwlINHLMWhsEC96PywCrvekLV1TT+ECux0/Q8X41UUqjhnoCb6oE8HfUoE/iTzOrp7BwLi+1myWju5d2qsxK/vta/16K4NNXxa7FTGlG15FjCO++bNNDOPcnIYRaZpaQe4XdUK17Ak5JwpcErR/CWAG2Kk7Hu7SO38nTugzSmImXe816Rfu96inSjiLkyOxcKcZu7U7sDn6S+O7Asc/GqntpRCGgi3nTNz7v/4mM+ubHaxDc0OiKJys24aof0J6nKgFcRZiroowmFMHsStbsOI8s6LIFumV083KNQrqZ+Mdaax4BBuv8+b0aFdwIDAQAB";

    /**
     * 根据RSA算法加密生成token【非对称加密】
     * @param payload
     * @return
     */
    public static String genTokenRAS(Map<String, String> payload){
        //指定过期时间 【3天】
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, 3);

        //获取一个构建对象【静态内部类】
        JWTCreator.Builder builder = JWT.create();

        //将信息、数据（body）信息放到需要的claim里面
        payload.forEach(builder::withClaim);

        //通过hutool工具类来创建RSA对象
        RSA rsa = new RSA(PRIVATE_KEY_STR, null);

        //获取私钥
        RSAPrivateKey privateKey = (RSAPrivateKey) rsa.getPrivateKey();
        String token = builder.withExpiresAt(calendar.getTime()).sign(Algorithm.RSA256(null, privateKey));
        return token;
    }

    /**
     * 使用RSA的方式来解密【非对称】
     * @param token
     * @return
     * 根据不同的异常来判断当前token到底是什么情况【比如被伪造...】
     */
    public static DecodedJWT decodedRSA(String token){
        //通过公钥获取我们的RSA的公钥对象
        RSA rsa = new RSA(null, PUBLIC_KEY_STR);
        RSAPublicKey publicKey = (RSAPublicKey) rsa.getPublicKey();

        //jwt的验证对象
        JWTVerifier jwtVerifier = JWT.require(Algorithm.RSA256(publicKey, null)).build();
        DecodedJWT decodedJWT = jwtVerifier.verify(token);
        return decodedJWT;
    }


    /**
     * 获取token【对称加密】
     * @param payload
     * @return
     */
    public static String getToken(Map<String, String> payload,int field, int amount){
        //指定过期时间【3天】
        Calendar calendar = Calendar.getInstance();
        calendar.add(field, amount);

        //获取一个构建对象【静态内部类】
        JWTCreator.Builder builder = JWT.create();

        //将body信息放到我们需要生成的claim里面
        payload.forEach(builder::withClaim);

        //通过指定签名算法和过期时间生成一个token
        return builder.withExpiresAt(calendar.getTime()).sign(Algorithm.HMAC256(SECRET));
    }

    /**
     * 解析token【对称加密】
     * @param token
     * @return
     */
    public static DecodedJWT decodedJWT(String token){
        //构建一个验证jwt token的对象
        JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256(SECRET)).build();
        //验证以后获取信息的对象
        DecodedJWT decodedJWT = jwtVerifier.verify(token);
        //返回信息对象
        return decodedJWT;
    }

    public static void main(String[] args) {
        Map<String, String> map = new HashMap<>();
        map.put("username", "XMLee");
        map.put("password", "XMLee4pwd");
        map.put("sex", "男");
        //RSA加密token
        String token = getToken(map,Calendar.DATE,3);
        System.out.println("对称加密后的token：" + token);

    }
}
