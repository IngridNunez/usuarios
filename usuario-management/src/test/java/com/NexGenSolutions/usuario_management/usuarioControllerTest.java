package com.NexGenSolutions.usuario_management;

import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import com.NexGenSolutions.controller.usuarioController;
import com.NexGenSolutions.model.usuario;
import com.NexGenSolutions.service.usuarioservice;
import com.fasterxml.jackson.databind.ObjectMapper;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(usuarioController.class)
public class usuarioControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private usuarioservice service;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("GET /api/v0/usuario devuelve lista de usuario si eres administrador")
    public void testListar()throws Exception{
        List<usuario> lista = Arrays.asList(
            new usuario(20571368, "Ingrid", "Núñez", "ing.nunez@duocuc.cl", "945745623", 24, LocalDate.of(1993, 4, 15), "Administrador"),
            new usuario(12345678,"Juan","Pérez","juan.perez@example.com","123456789",30,LocalDate.of(2001, 5, 31),"usuario")
        );
        usuario admin = new usuario(20571368, "Ingrid", "Núñez", "ing.nunez@duocuc.cl", "945745623", 24, LocalDate.of(1993, 4, 15), "Administrador");

        when(service.findAll(admin.getRun())).thenReturn(lista);


        mockMvc.perform(get("/api/v0/usuario")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(admin)))
                        .andExpect(status().isOk())
                        .andExpect(jsonPath("$.length()").value(2))
                        .andExpect(jsonPath("$[0].nombre").value("Ingrid"));
        
    }



}
