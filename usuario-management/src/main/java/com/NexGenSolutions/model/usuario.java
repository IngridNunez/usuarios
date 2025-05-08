package com.NexGenSolutions.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Table(name = "usuario")
@Data
@NoArgsConstructor
@AllArgsConstructor


public class usuario {
    @Id
    private String run;

    @Column(name = "nom")
    private String nombre;

    @Column
    private String apellido;
    
    @Column(nullable = false)
    private String correo;

    @Column(length = 9)
    private int numeroTelefono;

    @Column
    private int edad;
    




    


}





