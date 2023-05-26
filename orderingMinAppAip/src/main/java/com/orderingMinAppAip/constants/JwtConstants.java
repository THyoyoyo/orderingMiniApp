package com.orderingMinAppAip.constants;

public interface JwtConstants {

    String SECRET_KEY = "hH3OUPXYS0M42ZL6"; // 秘钥
    Long TOKEN_EXPIRE_TIME = 7L * 24L * 60L * 60L;// token过期时间 毫秒
    Long REFRESH_TOKEN_EXPIRE_TIME = 7L * 24L * 60L * 60L; // refreshToken过期时间 毫秒
    String ISSUER = "SOFTISLAND.ACCOUNT"; // 签发人
    String JWT_REDIS_KEY = "JWT_REDIS_TABLE";// 用于存放在redis中的hash表名
    String JWT_REFRESH_REDIS_KEY = "JWT_REFRESH_REDIS_TABLE";// 用于存放在redis中的hash表名
    /** 存于 cookie 里面的token **/
    String COOKIE_TOKEN = "cookieToken";
    /** 存于 cookie 里面的token **/
    String COOKIE_REFRESH_TOKEN = "cookieRefreshToken";
}
