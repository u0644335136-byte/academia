package com.jdk21.academia.features.comunidad.dto;

import lombok.Builder;
import lombok.Value;

// Usamos Record o @Value/@Builder para hacerlo inmutable, como es común en DTOs de entrada
@Value
@Builder
public class CreateComunidadDTO {
    String codigo; // Corresponde al input de GraphQL
    String nombre;
    String capital;
    Boolean activo;
}
