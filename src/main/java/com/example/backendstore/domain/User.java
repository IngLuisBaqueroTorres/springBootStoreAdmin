package com.example.backendstore.domain;

import lombok.*;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor

public class User {
    private String name;
    private String email;
    private String password;
    private String role;
    private String status;
    private String id;
    private String createdAt;
    private String updatedAt;
    private String deletedAt;
    private String lastLogin;
    private String lastLogout;

    public <T> User(String username, String s, List<T> ts) {
    }
}
