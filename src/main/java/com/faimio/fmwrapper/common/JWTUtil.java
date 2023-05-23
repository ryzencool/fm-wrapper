package com.faimio.fmwrapper.common;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;
import java.util.Map;

/**
 * Jwt工具类
 *
 * @author lucky
 * @version 1.0
 */
public class JWTUtil {

    private JWTUtil() {

    }

    private static final String KEY = "12qwaszx!@QWASZX";

    public static String create(String id, String subject, Map<String, Object> claims, long ttl) {
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

        long nowMills = System.currentTimeMillis();

        Date now = new Date(nowMills);

        JwtBuilder builder = Jwts.builder()
                .setClaims(claims)
                .setId(id)
                .setIssuedAt(now)
                .setSubject(subject)
                .signWith(signatureAlgorithm, JWTUtil.KEY);

        if (ttl >= 0) {
            long expMills = nowMills + ttl;
            Date exp = new Date(expMills);
            builder.setExpiration(exp);
        }
        return builder.compact();
    }

    public static Claims parse(String jwt) {
        return Jwts.parser()
                .setSigningKey(JWTUtil.KEY)
                .parseClaimsJws(jwt)
                .getBody();
    }


}
