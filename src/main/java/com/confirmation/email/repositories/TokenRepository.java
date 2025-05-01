package com.confirmation.email.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.confirmation.email.models.TokenModel;

public interface TokenRepository extends JpaRepository<TokenModel, Long> {
    Optional<TokenModel> findByToken(String token);
}
