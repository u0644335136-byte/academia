package com.jdk21.academia.features.persona.dto;

import java.io.Serializable;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record AsignarRolDTO(
    @NotNull(message = "El ID de la persona es obligatorio")
    Long idPersona,
    
    @NotBlank(message = "El código del rol es obligatorio")
    @Pattern(regexp = "^[A-Z_]+$", message = "El código del rol debe estar en mayúsculas")
    String codigoRol
) implements Serializable {
    
    private static final long serialVersionUID = 1L;
}

