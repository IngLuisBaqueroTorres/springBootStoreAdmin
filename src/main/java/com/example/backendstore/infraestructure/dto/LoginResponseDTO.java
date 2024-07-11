package com.example.backendstore.infraestructure.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class LoginResponseDTO {
    private String data;

    private Integer status;
    private String message;

}
