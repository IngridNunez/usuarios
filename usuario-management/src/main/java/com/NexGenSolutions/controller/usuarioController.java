package com.NexGenSolutions.controller;


import java.util.List;

import  org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.NexGenSolutions.model.usuario;
import com.NexGenSolutions.service.usuarioservice;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestParam;




enum Role {
    USUARIO,
    TRABAJADOR,
    ADMINISTRADOR,
}

@RestController
@RequestMapping("/api/v0/usuario")
public class usuarioController {

    @Autowired
    private usuarioservice usuarioservice;

    // Método para verificar privilegios
    @GetMapping("/privilegios")
    public ResponseEntity<String> verificarPrivilegios(@RequestParam Role role) {
        switch (role) {
            case ADMINISTRADOR:
                return ResponseEntity.ok("Acceso completo a todas las funcionalidades.");
            case TRABAJADOR:
                return ResponseEntity.ok("Acceso limitado a funcionalidades de gestión.");

            case USUARIO:
                return ResponseEntity.ok("Acceso básico.");
            default:
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Rol no reconocido.");
        }
    }
    //obtener todos los usuarios si esta vacia responde con 204NoConect y si hay usuarios responde con 200Ok
    @GetMapping
    public ResponseEntity<List<usuario>>listar(@PathVariable int run){
        List<usuario> usuario = usuarioservice.findAll(run);
        if(usuario.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(usuario);
   


    }
    //guardar nuev usuario
    @PostMapping 
    public ResponseEntity<usuario>guardar(@RequestBody usuario usuario){
        usuario nuevoUsuario = usuarioservice.save(usuario);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevoUsuario);

    }
    //buscar usuario por id si encuentra respinde con 200ok y si no con 404NotFound
    @GetMapping("/{run}")
    public ResponseEntity<usuario> buscarPorId(@PathVariable int run){
        try {
            usuario usuarioExistente = usuarioservice.findById(run);
            return ResponseEntity.ok(usuarioExistente);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }

    }
    //actualizar usuario por id
    @PutMapping("/{run}")
    public ResponseEntity<usuario> actualizar(@PathVariable int run, @RequestBody usuario usuario){
        try {
            usuario usuarioExistente = usuarioservice.findById(run);
            usuarioExistente.setRun(usuario.getRun());
            usuarioExistente.setNombre(usuario.getNombre());
            usuarioExistente.setApellido(usuario.getApellido());
            usuarioExistente.setCorreo(usuario.getCorreo());
            usuarioExistente.setTelefono(usuario.getTelefono());
            usuarioExistente.setEdad(usuario.getEdad());
            usuarioExistente.setFechaNacimiento(usuario.getFechaNacimiento());

            usuarioservice.save(usuarioExistente);//guarda el usuActualizado en la bd 
            return ResponseEntity.ok(usuarioExistente);
        } catch (Exception e) {
            return ResponseEntity.notFound().build(); //exepcion 404
        }

    }
    //eliminar usuario por id
    @DeleteMapping("/{run}")
    public ResponseEntity<?> eliminar(@PathVariable int run){
        try {
            usuarioservice.delete(run);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }


    @GetMapping("/admin/cuentas")
    public ResponseEntity<String> administrarCuentas(@RequestParam int run) {
        usuario usuario = usuarioservice.findById(run);
        if (usuario.getRole() == "ADMINISTRADOR") {
            // Lógica para administrar cuentas
            return ResponseEntity.ok("Acción de administración de cuentas realizada con éxito.");
        } else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Acceso denegado. Solo el administrador puede realizar esta acción.");
        }
    }




}
