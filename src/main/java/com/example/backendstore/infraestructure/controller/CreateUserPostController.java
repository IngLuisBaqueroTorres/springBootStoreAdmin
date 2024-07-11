package com.example.backendstore.infraestructure.controller;

import com.example.backendstore.domain.User;
import com.example.backendstore.domain.UserData;
import com.example.backendstore.infraestructure.dto.UserDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@Slf4j
@RestController
@RequestMapping("api/v1/user")
public class CreateUserPostController {

    @CrossOrigin(origins = "http://localhost:4200")
    @PostMapping("create")
    public UserData createUser(
            ServerHttpRequest request,
            @RequestBody UserDTO userDTO
    ) {
        final var user = new User();
        user.setId(userDTO.getId());
        user.setName(userDTO.getName());
        user.setEmail(userDTO.getEmail());
        user.setPassword(userDTO.getPassword());
        user.setRole(userDTO.getRole());
        user.setStatus(userDTO.getStatus());
        user.setCreatedAt(userDTO.getCreatedAt() != null ? userDTO.getCreatedAt() : String.valueOf(LocalDateTime.now()));
        user.setUpdatedAt(userDTO.getUpdatedAt() != null ? userDTO.getUpdatedAt() : "");
        user.setDeletedAt(userDTO.getDeletedAt() != null ? userDTO.getDeletedAt() : "");
        user.setLastLogin(userDTO.getLastLogin() != null ? userDTO.getLastLogin() : "");
        user.setLastLogout(userDTO.getLastLogout() != null ? userDTO.getLastLogout() : "");

        return UserData.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .password(user.getPassword())
                .role(user.getRole())
                .status(user.getStatus())
                .createdAt(user.getCreatedAt())
                .updatedAt(user.getUpdatedAt())
                .deletedAt(user.getDeletedAt())
                .lastLogin(user.getLastLogin())
                .lastLogout(user.getLastLogout())
                .build();
    }
}

