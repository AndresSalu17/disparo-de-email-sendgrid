package com.confirmation.email.services;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.confirmation.email.models.TokenModel;
import com.confirmation.email.models.UserModel;
import com.confirmation.email.repositories.TokenRepository;

@Service
public class TokenService {

    private final TokenRepository tokenRepository;
    private final EmailService emailService;

    public TokenService(TokenRepository tokenRepository, EmailService emailService){
        this.tokenRepository = tokenRepository;
        this.emailService = emailService;
    }
    
    public void gerarTokenParaUsuario(UserModel user){
        String token = UUID.randomUUID().toString();
        LocalDateTime expiracao = LocalDateTime.now().plusHours(24);

        TokenModel novoToken = new TokenModel(token, expiracao, user);
        tokenRepository.save(novoToken);

        try {
            emailService.enviarConfirmacaoEmail(user.getEmail(), token);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao enviar o email: ", e);
        }
        
    }
}
