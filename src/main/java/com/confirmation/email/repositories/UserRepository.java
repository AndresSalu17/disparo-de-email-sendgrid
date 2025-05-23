package com.confirmation.email.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.confirmation.email.models.UserModel;

public interface UserRepository extends JpaRepository<UserModel, Long> {
    Optional<UserModel> findByEmail(String email);
    
}
