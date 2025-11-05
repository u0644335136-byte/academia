package com.jdk21.academia.features.centro.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CreateCentroDTO(
    @NotBlank(message = "El código del centro es obligatorio")
    String codigoCentro,
    
    @NotBlank(message = "El nombre del centro es obligatorio")
    String nombre,
    
    @NotBlank(message = "El responsable es obligatorio")
    String responsable,
    
    @NotNull(message = "La empresa es obligatoria")
    Long empresaId,
    
    Integer capacidadMax,
    Boolean activo,
    String telefono,
    String email,
    String web,
    String direccion,
    String numero,
    String piso,
    String puerta,
    String codigoPostal,
    String localidad,
    String provincia
) {}
