package com.example.backendstore.infraestructure.security;

import com.example.backendstore.domain.User;
import com.example.backendstore.utils.JwtUtil;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import jakarta.servlet.ServletException;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.crypto.SecretKey;
import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;

import static com.example.backendstore.utils.JwtUtil.SECRET_KEY;

@Data
@Component
public class JwtTokenFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUtil jwtUtil;

    private User extractUserFromToken(String token) throws IOException {
        SecretKey key = Keys.hmacShaKeyFor(SECRET_KEY.getBytes());
        Claims claims;
        try {
                claims = Jwts.parser()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
        } catch (JwtException e) {
            throw new IOException("Invalid JWT token");
        }
        String userId = claims.getId();
        String email = claims.getSubject();
        String role = claims.get("role").toString();

        User user = new User();  // No arguments passed

        user.setId(userId);
        user.setEmail(email);
        user.setRole(role);

        return user;

    }

    @Override
    protected void doFilterInternal(jakarta.servlet.http.HttpServletRequest request,
                                    jakarta.servlet.http.HttpServletResponse response,
                                    jakarta.servlet.FilterChain filterChain
    ) throws IOException, ServletException {

        String token = request.getHeader("Authorization");

        if (token == null || !token.startsWith("Bearer ")) {
            // Handle missing or invalid token format
            throw new IOException("Missing or invalid Authorization header");
        }

        token = token.substring(7);

        User user = extractUserFromToken(token);

        filterChain.doFilter(request, response);
    }
}

