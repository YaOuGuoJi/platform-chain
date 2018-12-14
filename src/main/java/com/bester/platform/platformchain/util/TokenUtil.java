package com.bester.platform.platformchain.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.apache.commons.lang3.ArrayUtils;
import org.joda.time.DateTime;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.Map;

/**
 * @author liuwen
 * @date 2018/11/6
 */
public class TokenUtil {

    private static final String SECRET = "yaouguoji";
    private static final String COOKIE_KEY = "token";

    /**
     * 生成新token并设置cookie
     *
     * @param response
     * @param dataMap
     * @throws UnsupportedEncodingException
     */
    public static void updateToken2Cookie(HttpServletResponse response,
                                          Map<String, String> dataMap) throws UnsupportedEncodingException {
        Date expireTime = new DateTime().plusDays(7).toDate();
        JWTCreator.Builder builder = JWT.create();
        for (Map.Entry<String, String> entry : dataMap.entrySet()) {
            builder.withClaim(entry.getKey(), entry.getValue());
        }
        String token = builder.withExpiresAt(expireTime).withIssuedAt(new Date()).sign(Algorithm.HMAC256(SECRET));
        Cookie cookie = new Cookie(COOKIE_KEY, token);
        cookie.setPath("/");
        cookie.setMaxAge(15 * 24 * 3600);
        response.addCookie(cookie);
    }

    /**
     * 验证token
     *
     * @param token
     * @return
     * @throws UnsupportedEncodingException
     */
    public static Map<String, Claim> verifyToken(String token) throws UnsupportedEncodingException {
        JWTVerifier verifier = JWT.require(Algorithm.HMAC256(SECRET)).build();
        DecodedJWT verify = verifier.verify(token);
        return verify.getClaims();
    }

    public static String getToken(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (ArrayUtils.isNotEmpty(cookies)) {
            for (Cookie cookie : cookies) {
                if (COOKIE_KEY.equals(cookie.getName())) {
                    return cookie.getValue();
                }
            }
        }
        return null;
    }
}
