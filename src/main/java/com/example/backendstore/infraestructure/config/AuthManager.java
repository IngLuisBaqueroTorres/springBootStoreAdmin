package com.example.backendstore.infraestructure.config;

import com.example.backendstore.infraestructure.service.JWTService;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import reactor.core.publisher.Mono;

import java.util.Collection;
import java.util.List;

public class BearerTokenAuthenticationToken implements ReactiveAuthenticationManager {

    final JWTService jwtService;
    final ReactiveUserDetailsService users;
    public BearerTokenAuthenticationToken(JWTService jwtService, ReactiveUserDetailsService users) {
        this.jwtService = jwtService;
        this.users = users;
    }

    @Override
    public Mono<Authentication> authenticate(Authentication authentication) {
        return Mono.justOrEmpty(authentication.getCredentials())
                .cast(BearerToken.class)
                .flatMap(auth ->{
                    String userName = jwtService.getUserName(auth.getCredentials());
                    Mono<UserDetails> foundUser = users.findByUsername(userName).defaultIfEmpty(new UserDetails() {
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

                        @Override
                        public boolean isAccountNonExpired() {
                            return false;
                        }

                        @Override
                        public boolean isAccountNonLocked() {
                            return false;
                        }

                        @Override
                        public boolean isCredentialsNonExpired() {
                            return false;
                        }

                        @Override
                        public boolean isEnabled() {
                            return false;
                        }
                    });
                    return foundUser.flatMap(u -> {
                      if(u.getUsername() == null){
                          return Mono.error(new RuntimeException("User not found"));
                      }
                      if(jwtService.validate(u, auth.getCredentials())){
                          return Mono.just(new UsernamePasswordAuthenticationToken(u.getUsername(), u.getPassword(), u.getAuthorities()));
                      }
                      Mono.error(new RuntimeException("Invalid/ Expired Token"));
                        return Mono.just(new UsernamePasswordAuthenticationToken(u.getUsername(), u.getPassword(), u.getAuthorities()));
                    });
                });
    }
}
