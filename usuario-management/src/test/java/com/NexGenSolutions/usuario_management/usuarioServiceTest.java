package com.NexGenSolutions.usuario_management;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.time.LocalDate;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.NexGenSolutions.exeption.UnautorizedExeption;
import com.NexGenSolutions.model.usuario;
import com.NexGenSolutions.repository.usuarioRepository;
import com.NexGenSolutions.service.usuarioservice;

@ExtendWith(MockitoExtension.class)
public class usuarioServiceTest {

    @Mock
    private usuarioRepository usuarioRepository;

    @InjectMocks
    private usuarioservice usuarioService;

    private usuario usuarioRegular;

    @BeforeEach
    void setUp() {
        // Usuario regular para las pruebas
        usuarioRegular = new usuario(19876543, "Juan", "Pérez", "juan.perez@duocuc.cl", 
                                    "987654321", 30, LocalDate.of(1990, 8, 20), "Usuario");
    }

    @Test
    public void testFindAll_CuandoUsuarioNoEsAdministrador_DeberiaLanzarUnautorizedExeption() {
        // Arrange
        when(usuarioRepository.findById(19876543)).thenReturn(Optional.of(usuarioRegular));

        // Act & Assert
        UnautorizedExeption exception = assertThrows(UnautorizedExeption.class, () -> {
            usuarioService.findAll(19876543);
        });

        assertEquals("Acceso no autorizado", exception.getMessage());
        verify(usuarioRepository).findById(19876543);
        verify(usuarioRepository, never()).findAll();
    }
}

    


