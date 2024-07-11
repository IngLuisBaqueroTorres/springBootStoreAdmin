package com.example.backendstore.utils;

import com.example.backendstore.domain.User;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.fasterxml.jackson.databind.util.Converter;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwt;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collections;

public class JwtTokenConverterUtil implements Converter<Jwt, AbstractAuthenticationToken> {
    @Override
    public AbstractAuthenticationToken convert(Jwt jwt) {

        Claims claims = (Claims) jwt.getBody();

        String username = (String) claims.get("username");
        String role = (String) claims.get("role");

        // Create an appropriate AuthenticationToken based on extracted information
        UserDetails userDetails = (UserDetails) new User(username, "", Collections.singletonList(new SimpleGrantedAuthority(role)));
        return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
    }

    @Override
    public JavaType getInputType(TypeFactory typeFactory) {
        return null;
    }

    @Override
    public JavaType getOutputType(TypeFactory typeFactory) {
        return null;
    }
}
