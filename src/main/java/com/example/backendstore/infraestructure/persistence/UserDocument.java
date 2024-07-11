package com.example.backendstore.infraestructure.persistence;


import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Document(collection = "users")
public class UserDocument {

    @Id
    private String id;

    private String name;

    private String email;

    private String password;

    private String role;

    private String status;

    private Date createdAt;

    private Date updatedAt;

    private Date deletedAt;

    private Date lastLogin;

    private Date lastLogout;

}
