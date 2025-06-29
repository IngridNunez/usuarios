package com.NexGenSolutions.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.hateoas.RepresentationModel;

import java.time.LocalDate;

@Entity
@Table(name = "usuario")
@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Entidad Usuario con enlaces HATEOAS")
public class usuario extends RepresentationModel<usuario> {
    
    @Id
    @Schema(description = "RUN del usuario", example = "19876543")
    private int run;

    @Column
    @Schema(description = "Nombre del usuario", example = "Juan")
    private String nombre;

    @Column
    @Schema(description = "Apellido del usuario", example = "Pérez")
    private String apellido;
    
    @Column(nullable = false)
    @Schema(description = "Correo electrónico", example = "juan.perez@duocuc.cl")
    private String correo;

    @Column(length = 9)
    @Schema(description = "Teléfono", example = "987654321")
    private String telefono;

    @Column
    @Schema(description = "Edad", example = "30")
    private int edad;

    @Column(name = "fecha_nacimiento")
    @Schema(description = "Fecha de nacimiento", example = "1990-08-20")
    private LocalDate fechaNacimiento;

    @Column(nullable = false)
    @Schema(description = "Rol del usuario", allowableValues = {"Usuario", "Trabajador", "Administrador"})
    private String role;
}





