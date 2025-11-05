package com.jdk21.academia.features.convocatoria.dto;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record CrearMatriculaDTO(

    @NotBlank(message = "El nombre es obligatorio")
    String nombre,
    
    @NotBlank(message = "El apellido es obligatorio")
    String apellido

){}
    


