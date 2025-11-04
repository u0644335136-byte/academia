package com.jdk21.academia.features.persona.dto;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Set;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record CreatePersonaDTO(
    @NotBlank(message = "El nombre es obligatorio")
    String nombre,
    
    @NotBlank(message = "El apellido es obligatorio")
    String apellido,
    
    Boolean activo,
    
    LocalDate fechaNacimiento,
    
    @NotNull(message = "El teléfono es obligatorio")
    Integer telefono,
    
    @Email(message = "El email debe tener un formato válido")
    String email,
    
    String web,
    String direccion,
    Integer numero,
    Integer piso,
    String puerta,
    Integer codigoPostal,
    String localidad,
    String provincia,
    Long idDiscapacidad,

    Set<@Pattern(regexp = "^[A-Z_]+$", message = "El código del rol debe estar en mayúsculas") String> roles
) implements Serializable {
    
    private static final long serialVersionUID = 1L;
}

