package com.NexGenSolutions.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.NexGenSolutions.exeption.UnautorizedExeption;
import com.NexGenSolutions.model.usuario;
import com.NexGenSolutions.service.usuarioservice;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestParam;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

enum Role {
    USUARIO,
    TRABAJADOR,
    ADMINISTRADOR,
}

@RestController
@RequestMapping("/api/v0/usuario")
@Tag(name = "Usuario Management", description = "API para gestión de usuarios")
public class usuarioController {

    @Autowired
    private usuarioservice usuarioservice;
    
    // Método auxiliar para agregar enlaces HATEOAS
    private void addUsuarioLinks(usuario usuario) {
        // Link para ver el usuario (self) - solo el enlace de información
        usuario.add(linkTo(methodOn(usuarioController.class).buscarPorId(usuario.getRun()))
                .withSelfRel()
                .withTitle("Ver información del usuario")
                .withType("GET"));
    }

    // Método para verificar privilegios
    @Operation(summary = "Verificar privilegios por rol", 
               description = "Verifica los privilegios de acceso según el rol del usuario")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Privilegios verificados exitosamente"),
        @ApiResponse(responseCode = "403", description = "Rol no reconocido")
    })
    @GetMapping("/privilegios")
    public ResponseEntity<String> verificarPrivilegios(
            @Parameter(description = "Rol del usuario", required = true)
            @RequestParam Role role) {
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
    @Operation(summary = "Listar todos los usuarios", 
               description = "Obtiene todos los usuarios. Solo accesible para administradores.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista de usuarios obtenida exitosamente",
                    content = @Content(schema = @Schema(implementation = usuario.class))),
        @ApiResponse(responseCode = "204", description = "No hay usuarios registrados"),
        @ApiResponse(responseCode = "401", description = "No autorizado - Solo administradores"),
        @ApiResponse(responseCode = "404", description = "Usuario administrador no encontrado")
    })
    @GetMapping
    public ResponseEntity<?> listar(
            @Parameter(description = "Usuario administrador que realiza la consulta", required = true)
            @RequestBody usuario admin){
        List<usuario> usuarios;
        try {
            usuarios = usuarioservice.findAll(admin.getRun());
            if(usuarios.isEmpty()){
                return ResponseEntity.noContent().build();
            }
            
            // Agregar enlaces HATEOAS a cada usuario
            usuarios.forEach(this::addUsuarioLinks);
                    
            CollectionModel<usuario> collectionModel = CollectionModel.of(usuarios);
            collectionModel.add(linkTo(methodOn(usuarioController.class).listar(admin)).withSelfRel());
            
            return ResponseEntity.ok(collectionModel);
        } catch (UnautorizedExeption e) {
            return new ResponseEntity<Object>(e.getMessage(), HttpStatus.UNAUTHORIZED);
        } catch(RuntimeException e){
            return new ResponseEntity<Object>("No existe el usuario Admin", HttpStatus.NOT_FOUND);
        }
    }
    //guardar nuevo usuario
    @Operation(summary = "Crear nuevo usuario", 
               description = "Crea un nuevo usuario en el sistema")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Usuario creado exitosamente",
                    content = @Content(schema = @Schema(implementation = usuario.class))),
        @ApiResponse(responseCode = "400", description = "Datos inválidos del usuario")
    })
    @PostMapping 
    public ResponseEntity<usuario> guardar(
            @Parameter(description = "Datos del usuario a crear", required = true)
            @RequestBody usuario usuario){
        usuario nuevoUsuario = usuarioservice.save(usuario);
        addUsuarioLinks(nuevoUsuario);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevoUsuario);
    }
    //buscar usuario por id si encuentra responde con 200ok y si no con 404NotFound
    @Operation(summary = "Buscar usuario por RUN", 
               description = "Busca un usuario específico por su RUN")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Usuario encontrado",
                    content = @Content(schema = @Schema(implementation = usuario.class))),
        @ApiResponse(responseCode = "404", description = "Usuario no encontrado")
    })
    @GetMapping("/{run}")
    public ResponseEntity<usuario> buscarPorId(
            @Parameter(description = "RUN del usuario", required = true, example = "19876543")
            @PathVariable int run){
        try {
            usuario usuarioExistente = usuarioservice.findById(run);
            addUsuarioLinks(usuarioExistente);
            return ResponseEntity.ok(usuarioExistente);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
    //actualizar usuario por id
    @Operation(summary = "Actualizar usuario", 
               description = "Actualiza los datos de un usuario existente")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Usuario actualizado exitosamente",
                    content = @Content(schema = @Schema(implementation = usuario.class))),
        @ApiResponse(responseCode = "404", description = "Usuario no encontrado")
    })
    @PutMapping("/{run}")
    public ResponseEntity<usuario> actualizar(
            @Parameter(description = "RUN del usuario a actualizar", required = true, example = "19876543")
            @PathVariable int run,
            @Parameter(description = "Nuevos datos del usuario", required = true)
            @RequestBody usuario usuario){
        try {
            usuario usuarioExistente = usuarioservice.findById(run);
            usuarioExistente.setRun(usuario.getRun());
            usuarioExistente.setNombre(usuario.getNombre());
            usuarioExistente.setApellido(usuario.getApellido());
            usuarioExistente.setCorreo(usuario.getCorreo());
            usuarioExistente.setTelefono(usuario.getTelefono());
            usuarioExistente.setEdad(usuario.getEdad());
            usuarioExistente.setFechaNacimiento(usuario.getFechaNacimiento());
            usuarioExistente.setRole(usuario.getRole());

            usuario usuarioActualizado = usuarioservice.save(usuarioExistente);
            addUsuarioLinks(usuarioActualizado);
            return ResponseEntity.ok(usuarioActualizado);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
    //eliminar usuario por id
    @Operation(summary = "Eliminar usuario", 
               description = "Elimina un usuario del sistema")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Usuario eliminado exitosamente"),
        @ApiResponse(responseCode = "404", description = "Usuario no encontrado")
    })
    @DeleteMapping("/{run}")
    public ResponseEntity<?> eliminar(
            @Parameter(description = "RUN del usuario a eliminar", required = true, example = "19876543")
            @PathVariable int run){
        try {
            usuarioservice.delete(run);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    // Página simple del usuario
 


    @Operation(summary = "Administrar cuentas", 
               description = "Funcionalidad exclusiva para administradores")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Acción de administración realizada exitosamente"),
        @ApiResponse(responseCode = "403", description = "Acceso denegado - Solo administradores")
    })
    @GetMapping("/admin/cuentas")
    public ResponseEntity<String> administrarCuentas(
            @Parameter(description = "RUN del administrador", required = true, example = "12345678")
            @RequestParam int run) {
        usuario usuario = usuarioservice.findById(run);
        if ("ADMINISTRADOR".equals(usuario.getRole())) {
            return ResponseEntity.ok("Acción de administración de cuentas realizada con éxito.");
        } else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body("Acceso denegado. Solo el administrador puede realizar esta acción.");
        }
    }

}
