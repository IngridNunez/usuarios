package com.NexGenSolutions.controller;


import java.util.List;

import  org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.NexGenSolutions.model.usuario;
import com.NexGenSolutions.service.usuarioservice;

import org.springframework.web.bind.annotation.GetMapping;




@RestController
@RequestMapping("/api/v0/usuario")
public class usuarioController {

    @Autowired
    private usuarioservice usuarioservice;

    @GetMapping
    public ResponseEntity<List<usuario>>listar(){
        List<usuario> usuario = usuarioservice.findAll();
        if(usuario.isEmpty()){
            return ResponseEntity.noContent().build();

        }
        return ResponseEntity.ok(usuario);
   


    }
    

}
