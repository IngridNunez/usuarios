package com.NexGenSolutions.service;

import com.NexGenSolutions.model.usuario;
import com.NexGenSolutions.repository.usuarioRepository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Transactional
@Service

public class usuarioservice {
    @Autowired
    private usuarioRepository usuarioRepository;

    public List<usuario> findAll(){
        return usuarioRepository.findAll();
    }

    public usuario FindById(String run){
        return usuarioRepository.FindById(run).get(0);

    }


    public usuario save(usuario usuario){
        return usuarioRepository.save(usuario);

    }


    public void delete(String run){
        usuarioRepository.deleteById(run);

    }



}
