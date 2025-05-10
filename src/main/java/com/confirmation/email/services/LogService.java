package com.confirmation.email.services;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;

import com.confirmation.email.models.LogModel;
import com.confirmation.email.repositories.LogRepository;

@Service
public class LogService {
    
    private LogRepository logRepository;

    public LogService(LogRepository logRepository){
        this.logRepository = logRepository;
    }

    public void registrarLog(String acao, String detalhes, String usuarioEmail, String status){
        LogModel log = new LogModel();
        log.setAcao(acao);
        log.setDetalhes(detalhes);
        log.setUsuarioEmail(usuarioEmail);
        log.setStatus(status);
        log.setDataHora(LocalDateTime.now());
        logRepository.save(log);
    }
}
