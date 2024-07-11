package com.example.backendstore.infraestructure.service;

import com.example.backendstore.domain.UserData;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

@Service
public class JWTService {
    final private SecretKey secretKey;
    final private JwtParser jwtParser;

    public JWTService(@Value("${jwt.secret}") String secret) {
        this.secretKey = Keys.hmacShaKeyFor(secret.getBytes());
        this.jwtParser = Jwts.parser().setSigningKey(this.secretKey).build();
    }

    public String generate(String userName){
        return Jwts.builder()
                .setSubject(userName)
                .setIssuedAt(Date.from(Instant.now()))
                .setExpiration(Date.from(Instant.now().plus(1, ChronoUnit.HOURS)))
                .signWith(this.secretKey)
                .compact();
    }

    public String getUserName(String token){
        Claims claims = jwtParser.parseClaimsJws(token).getBody();
        return claims.getSubject();
    }

    public boolean validate(UserDetails user, String token){
        Claims claims = jwtParser.parseClaimsJws(token).getBody();
        boolean unexpired = claims.getExpiration().after(Date.from(Instant.now()));

        return unexpired && user.getUsername().equals(claims.getSubject());
    }
}
