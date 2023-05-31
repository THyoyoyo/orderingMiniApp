package com.blogs.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.blogs.constants.JwtConstants;

import java.util.Date;

public class JWTUtil {

    /**
        * 生成签名
        */
    public static String generateToken(Integer userId,String ip){
        Date now = new Date();
        Algorithm algorithm = Algorithm.HMAC256(JwtConstants.SECRET_KEY); //算法
        String token = JWT.create()
                .withIssuer(JwtConstants.ISSUER) //签发人
                .withIssuedAt(now) //签发时间
                .withExpiresAt(new Date(now.getTime() + JwtConstants.TOKEN_EXPIRE_TIME * 1000)) //过期时间
                .withClaim("userId", userId.toString()) //保存身份标识
                .withClaim("ip", ip) //保存当前的IP
                .sign(algorithm);
        return token;
    }

    /**
        * 验证token
        */
    public static boolean verify(String token){
        try {
            Algorithm algorithm = Algorithm.HMAC256(JwtConstants.SECRET_KEY); //算法
            JWTVerifier verifier = JWT.require(algorithm)
                    .withIssuer(JwtConstants.ISSUER)
                    .build();
            verifier.verify(token);
            return true;
        } catch (Exception ex){
            ex.printStackTrace();
        }
        return false;
    }

    /**
        * 从token获取userId
        */
    public static Integer getUserId(String token) throws Exception{
        return Integer.parseInt(JWT.decode(token).getClaim("userId").asString());
    }


    /**
        * 从token获取ip
        */
    public static String getIP(String token) throws Exception{
        return JWT.decode(token).getClaim("ip").asString();
    }
}
