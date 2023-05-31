package com.orderingMinAppAip.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.orderingMinAppAip.constants.JwtConstants;
import com.orderingMinAppAip.exception.authorityException;


import java.util.Date;
import java.util.Map;

public class JWTUtil {

    /**
        * 生成签名
        */
    public static String generateToken(String userName, Integer userId,String ip){
        Date now = new Date();
        Algorithm algorithm = Algorithm.HMAC256(JwtConstants.SECRET_KEY); //算法
        String token = JWT.create()
                .withIssuer(JwtConstants.ISSUER) //签发人
                .withIssuedAt(now) //签发时间
                .withExpiresAt(new Date(now.getTime() + JwtConstants.TOKEN_EXPIRE_TIME * 1000)) //过期时间
                .withClaim("userId", userId.toString()) //保存身份标识
                .withClaim("ip", ip) //保存当前的IP
                .withClaim("userName", userName) //保存当前的IP
                .sign(algorithm);
        return token;
    }

    /**
        * 验证token
        */
    public static boolean verify(String token){
        try {
            if(token == "" || token ==null){
                return false; // 令牌无
            }
            Algorithm algorithm = Algorithm.HMAC256(JwtConstants.SECRET_KEY); //算法
            JWTVerifier verifier = JWT.require(algorithm)
                    .withIssuer(JwtConstants.ISSUER)
                    .build();
            verifier.verify(token);

            DecodedJWT decodedJWT = verifier.verify(token);

            // 获取令牌的相关信息
            Map<String, Claim> claims = decodedJWT.getClaims();

            // 验证过期时间
            Claim expirationClaim = claims.get("exp");

            if (expirationClaim != null) {
                Date expirationDate = expirationClaim.asDate();
                Date now = new Date();
                if (expirationDate.before(now)) {
                    return false; // 令牌已过期
                }
            }

            // 验证生效时间
            Claim notBeforeClaim = claims.get("nbf");
            if (notBeforeClaim != null) {
                Date notBeforeDate = notBeforeClaim.asDate();
                Date now = new Date();
                if (notBeforeDate.after(now)) {
                    return false; // 令牌尚未生效
                }
            }

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
        * 从token获取userName
        */
    public static String getUserName(String token) throws Exception{
        return JWT.decode(token).getClaim("userName").asString();
    }


    /**
        * 从token获取ip
        */
    public static String getIP(String token) throws Exception{
        return JWT.decode(token).getClaim("ip").asString();
    }
}
