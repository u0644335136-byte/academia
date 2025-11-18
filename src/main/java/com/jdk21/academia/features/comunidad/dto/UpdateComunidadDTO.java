package com.jdk21.academia.features.comunidad.dto;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class UpdateComunidadDTO {
    String codigo;
    String nombre;
    String capital;
    Boolean activo;
}
