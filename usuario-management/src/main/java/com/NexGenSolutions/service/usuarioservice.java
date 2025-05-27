package com.NexGenSolutions.service;

import com.NexGenSolutions.exeption.UnautorizedExeption;
import com.NexGenSolutions.model.usuario;
import com.NexGenSolutions.repository.usuarioRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
@Transactional
@Service

public class usuarioservice {
    @Autowired
    private usuarioRepository usuarioRepository;
    //obtiene todos los usuarios
    public List<usuario> findAll(Integer run) throws UnautorizedExeption, RuntimeException{
        List<usuario> usrs =  usuarioRepository.findAll();
        usuario user = findById(run);
        if (user.getRole().equals("Administrador")){
            return usrs;
        }
        else{
            throw new UnautorizedExeption("Acceso no autorizado");
        }
    }

    // Busca un usuario por run 
    public usuario findById(int run) throws RuntimeException{
        Optional<usuario> usuarioOpt = usuarioRepository.findById(run); // Busca y puede no encontrar
        if (usuarioOpt.isPresent()) {
            return usuarioOpt.get(); 
        } else {
            throw new RuntimeException("Usuario no encontrado"); 
    }

}

    //guarda y actualiza un usuario
    public usuario save(usuario usuario){
        return usuarioRepository.save(usuario);

    }

    //eliminaa un usuario por id
    public void delete(int run){
        usuarioRepository.deleteById(run);

    }



}
