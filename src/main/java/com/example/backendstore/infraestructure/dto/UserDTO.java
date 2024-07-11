package com.example.backendstore.infraestructure.dto;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class UserDTO {
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
