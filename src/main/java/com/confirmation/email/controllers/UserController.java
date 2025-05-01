package com.confirmation.email.controllers;

import java.util.List;

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
    public ResponseEntity<UserModel> criarNovoUsuario(@RequestBody UserModel criarUsuario){
        criarUsuario.setId(null);
        
        UserModel salvo = userRepository.save(criarUsuario);

        tokenService.gerarTokenParaUsuario(salvo);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/atualizar")
    public ResponseEntity<UserModel> atualizarUsuario(@RequestBody UserModel atualizarUsuario){
        return new ResponseEntity<>(userRepository.save(atualizarUsuario), HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<UserModel> deletarUsuario(@PathVariable Long id){
        userRepository.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
