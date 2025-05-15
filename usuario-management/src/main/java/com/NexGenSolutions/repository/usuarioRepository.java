package com.NexGenSolutions.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.NexGenSolutions.model.usuario;


@Repository

public interface usuarioRepository extends JpaRepository<usuario, Long> {

 //encuentra usuarios por apellido


 List<usuario> FindById( String run);

 // encuentra usuario por su correo
  
  usuario findByCorreo(String correo);



  //encuentra pacientes por nombre y apellido

  List<usuario> finByNombreApellido(String nombre, String apellidos);






  


}

