package com.example.backendstore.infraestructure.controller;

import com.example.backendstore.infraestructure.dto.LoginRequestDTO;
import com.example.backendstore.infraestructure.dto.LoginResponseDTO;
import com.example.backendstore.infraestructure.service.JWTService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
@Slf4j
@RestController
@RequestMapping("api/v1")
public class LoginController {

    final ReactiveUserDetailsService users;

    final JWTService jwtService;

    final PasswordEncoder encoder;

    public LoginController(ReactiveUserDetailsService users, JWTService jwtService, PasswordEncoder encoder) {
        this.users = users;
        this.jwtService = jwtService;
        this.encoder = encoder;
    }

    @GetMapping("/auth")
    public Mono<LoginResponseDTO> auth() {
        return Mono.just(
                new LoginResponseDTO("", HttpStatus.OK.value(), "Access Success"));
    }

    @PostMapping("/login")
    public Mono<LoginResponseDTO> login(@RequestBody LoginRequestDTO loginRequest) {
        Mono<UserDetails> foundUser = users.findByUsername(loginRequest.getEmail()).defaultIfEmpty(new UserDetails() {
            @Override
            public Collection<? extends GrantedAuthority> getAuthorities() {
                return List.of();
            }

            @Override
            public String getPassword() {
                return "";
            }

            @Override
            public String getUsername() {
                return "";
            }
        });

        return foundUser.flatMap(user -> {

            if (user != null && (Objects.equals(user.getUsername(), loginRequest.getEmail()))) {
                if (encoder.matches(loginRequest.getPassword(),(user.getPassword()))) {
                   return Mono.just(
                            new LoginResponseDTO(jwtService.generate(user.getUsername()), HttpStatus.OK.value(), "Access Success")
                    );
                }
                return Mono.just(
                        new LoginResponseDTO("", HttpStatus.UNAUTHORIZED.value(), "Access Denied"));
            }
            return Mono.just(
                    new LoginResponseDTO("", HttpStatus.UNAUTHORIZED.value(), "Access Denied"));
        });
    }
}
