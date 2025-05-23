package com.NexGenSolutions.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.NexGenSolutions.model.usuario;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface usuarioRepository extends JpaRepository<usuario, Integer> { // Cambiado de String a Integer

    // Busca usuario por su correo
    usuario findByCorreo(String correo);

    // Busca usuarios por nombre y apellido
    List<usuario> findByNombreAndApellido(String nombre, String apellido);

    // Busca usuarios por edad
    List<usuario> findByEdad(int edad);

    // Consulta por correo
    @Query(value = "SELECT * FROM usuario WHERE correo = :correo", nativeQuery = true)
    usuario buscarPorCorreo(@Param("correo") String correo);

    // Consulta por apellido (devuelve lista)
    @Query(value = "SELECT * FROM usuario WHERE apellido = :apellido", nativeQuery = true)
    List<usuario> buscarPorApellido(@Param("apellido") String apellido);
}

