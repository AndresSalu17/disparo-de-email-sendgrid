package com.confirmation.email.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.confirmation.email.models.LogModel;

public interface LogRepository extends JpaRepository<LogModel, Long> {
    
}
