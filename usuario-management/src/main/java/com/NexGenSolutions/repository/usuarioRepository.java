package com.NexGenSolutions.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.NexGenSolutions.model.usuario;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


import java.util.List;

@Repository

public interface usuarioRepository extends JpaRepository<usuario, String> {

 //encuentra usuarios por apellido


 List<usuario> FindById( String run);

 // encuentra usuario por su correo
  usuario findByCorreo(String correo);

  //encuentra pacientes por nombre y apellido

  List<usuario> finByNombreApellido(String nombre, String apellido);
 //usando JPQL
 @Query( value = "SELECT * FROM usuario WHERE correo =: correo", nativeQuery = true)
 usuario buscarPorCorreo(@Param ("correo") String correo);
 
 //usando JPQL 
 @Query( value = "SELECT * FROM usuario WHERE apellido =: apellido", nativeQuery = true)
 usuario buscarPorApellido(@Param ("apellido") String apellido);

 


  


}

