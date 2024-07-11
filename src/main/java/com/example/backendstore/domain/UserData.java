package com.example.backendstore.domain;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor(access = AccessLevel.PUBLIC)
public class UserData {
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
}
