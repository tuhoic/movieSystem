package com.example.movie.utils;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtTokenUtil {

    // 注入失败
//    @Value("${jwt.secret}")
//    private String secret;
    private final String secret = "cca795e051ef4f0996ebc063fbf12aab";

    private static final long EXPIRATION_TIME = 24 * 60 * 60 * 1000L; // 24 hours

    public String generateToken(String username) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("sub", username);
        claims.put("iat", new Date());
        claims.put("exp", new Date(System.currentTimeMillis() + EXPIRATION_TIME));
        return Jwts.builder().setClaims(claims).signWith(SignatureAlgorithm.HS512, secret).compact();
    }


    public String getUsernameFromToken(String token) {
        return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody().getSubject();
    }
}
