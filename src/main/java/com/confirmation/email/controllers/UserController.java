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

@RestController
@RequestMapping
@CrossOrigin("*")

public class UserController {
    
    private UserRepository userRepository;

    public UserController(UserRepository userRepository){
        this.userRepository = userRepository;
    }


    @GetMapping("/listar")
    public ResponseEntity<List<UserModel>> listarTodosUsuarios(){
        return new ResponseEntity<>(userRepository.findAll(), HttpStatus.OK);
    }

    @PostMapping("/criar")
    public ResponseEntity<UserModel> criarNovoUsuario(@RequestBody UserModel criarUsuario){
        criarUsuario.setId(null);
        return new ResponseEntity<>(userRepository.save(criarUsuario), HttpStatus.OK);
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
