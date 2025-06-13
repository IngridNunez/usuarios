package com.NexGenSolutions.usuario_management;

import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import com.NexGenSolutions.model.usuario;
import com.NexGenSolutions.repository.usuarioRepository;
import com.NexGenSolutions.service.usuarioservice;

@SpringBootTest
@WebMvcTest(usuarioservice.class)
public class usuarioServiceTest {

    @Autowired
    private usuarioservice usuarioService;


    @MockitoBean
    private usuarioRepository usuarioRepository;

    @Test

    public void testFindAll(){
        //define el comportamiento del mock cuando se llama a findAll
        List<usuario> lista = Arrays.asList(
            new usuario(20571368, "Ingrid", "Núñez", "ing.nunez@duocuc.cl", "945745623", 24, LocalDate.of(1993, 4, 15), "Administrador")
        );
    }




    }

    


