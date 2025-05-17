package com.confirmation.email.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.confirmation.email.models.UserModel;
import com.confirmation.email.repositories.UserRepository;
import com.confirmation.email.services.TokenService;


@RestController
@RequestMapping
@CrossOrigin("*")

public class UserController {
    
    private UserRepository userRepository;
    private TokenService tokenService;

    public UserController(UserRepository userRepository, TokenService tokenService){
        this.userRepository = userRepository;
        this.tokenService = tokenService;
    }

    @GetMapping("/listar")
    public ResponseEntity<List<UserModel>> listarTodosUsuarios(){
        return new ResponseEntity<>(userRepository.findAll(), HttpStatus.OK);
    }

    @PostMapping("/criar")
    public ResponseEntity<HashMap<String, String>> criarNovoUsuario(@RequestBody UserModel criarUsuario){
        criarUsuario.setId(null);

        Optional<UserModel> existente = userRepository.findByEmail(criarUsuario.getEmail());

        if(existente.isPresent()){
            HashMap<String, String> erro = new HashMap<>();
            erro.put("error", "E-mail e/ou senha já utilizados.");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(erro);
        }
        
        UserModel salvo = userRepository.save(criarUsuario);

        tokenService.gerarTokenParaUsuario(salvo);

        HashMap<String, String> resposta = new HashMap<>();
        resposta.put("mensagem", "Usuário criado com sucesso. Verifique seu e-mail.");
        return ResponseEntity.status(HttpStatus.CREATED).body(resposta);
    }

    @PutMapping("/atualizar")
    public ResponseEntity<HashMap<String, String>> atualizarUsuario(@RequestBody UserModel atualizarUsuario){
        HashMap<String, String> response = new HashMap<>();

        try {
            userRepository.save(atualizarUsuario);
            response.put("response", "Usuário atualizado com sucesso!");
            return ResponseEntity.status(HttpStatus.OK).body(response);
            
        } catch (Exception e) {
            response.put("error", "Falha ao atualizar o usuário!");
            response.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        } 
    }

    @DeleteMapping("{id}")
    public ResponseEntity<HashMap<String, String>> deletarUsuario(@PathVariable Long id){
        HashMap<String, String> response = new HashMap<>();

        Optional<UserModel> user = userRepository.findById(id);

        if(user.isPresent()){
            userRepository.delete(user.get());
            response.put("response", "Usuário deletado com sucesso!");
            return ResponseEntity.status(HttpStatus.OK).body(response);

        } else{
        
            response.put("error", "Erro ao deletar o usuário!");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }
}

//PAREI AQUI, CONFIGURANDO O RETORNO EM JSON
