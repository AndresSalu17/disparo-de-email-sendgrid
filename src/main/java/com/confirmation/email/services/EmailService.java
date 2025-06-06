package com.confirmation.email.services;

import java.io.IOException;

import org.springframework.stereotype.Service;

import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.Response;
import com.sendgrid.SendGrid;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Content;
import com.sendgrid.helpers.mail.objects.Email;

import io.github.cdimascio.dotenv.Dotenv;

@Service
public class EmailService {
    
    Dotenv dotenv = Dotenv.load();
    private final String API_KEY = dotenv.get("API_KEY");
    private LogService logService;

    public EmailService(LogService logService){
        this.logService = logService;
    }

    public String enviarConfirmacaoEmail(String destino, String token){

        Email de = new Email("contato.andres.salu@gmail.com");
        String assunto = "Confirmação de email";
        Email para = new Email(destino);
        
        String link = "http://localhost:8080/confirmar?token=" + token;
        String conteudoEmail = "Olá!\n\nCadastro realizado com sucesso!\n\nClique no link abaixo para confirmar seu cadastro:\n\n" + link;

        Content content = new Content("text/plain", conteudoEmail);
        Mail mail = new Mail(de, assunto, para, content);
        
        SendGrid sg = new SendGrid(API_KEY);
        Request request = new Request();
    
        try{
            request.setMethod(Method.POST);
            request.setEndpoint("mail/send");
            request.setBody(mail.build());

            Response response = sg.api(request);

            logService.registrarLog(
                "Envio de e-mail",
                "Email de confirmação enviado com sucesso para: " + destino,
                destino,
                "SUCESSO"
            );

            return String.format("Status Code: %d%nBody: %s%nHeaders: %s",
                response.getStatusCode(),
                response.getBody(),
                response.getHeaders());

        }catch(IOException e){
            logService.registrarLog("Envio de e-mail", 
            "Falha ao enviar o e-mail: " + e.getMessage(), 
            destino, 
            "ERRO");
            throw new RuntimeException("Erro ao salvar o email: ", e);
        }
    }
}
