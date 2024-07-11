package com.example.backendstore.utils;

import com.example.backendstore.domain.User;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtUtil {

    public static final String SECRET_KEY = "secret";

    public static String generateToken(User user) {
        long now = System.currentTimeMillis();
        long expiry = now + 1000 * 60 * 60 * 10;
        return Jwts.builder()
                .id(String.valueOf(user.getId()))
                .subject(user.getEmail())
                .issuedAt(new Date(now))
                .expiration(new Date(expiry))
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                .compact();
    }
}
