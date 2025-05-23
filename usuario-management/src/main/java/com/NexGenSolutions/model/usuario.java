package com.NexGenSolutions.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "usuario")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class usuario {
    @Id
    private int run;

    @Column
    private String nombre;

    @Column
    private String apellido;
    
    @Column(nullable = false)
    private String correo;

    @Column(length = 9)
    private String telefono;

    @Column
    private int edad;

    @Column(name = "fecha_nacimiento")
    private LocalDate fechaNacimiento;

    @Enumerated(EnumType.STRING) // Almacena el rol como texto en la base de datos
    @Column(nullable = false)
    private String role; // Nuevo atributo para el rol del usuario
}





