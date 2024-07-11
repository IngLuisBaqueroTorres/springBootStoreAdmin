package com.example.backendstore.infraestructure.persistence;

import com.example.backendstore.domain.User;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User, String> {

    User findByEmailAndPassword(String email, String password); // For login

    User findByEmail(String email); // For checking if email exists during registration

}
