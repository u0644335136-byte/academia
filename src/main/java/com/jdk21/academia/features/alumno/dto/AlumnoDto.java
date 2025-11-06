package com.jdk21.academia.features.alumno.dto;

import java.io.Serializable;
import java.time.LocalDate;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record AlumnoDto(
    Long id_alumno,

    @NotBlank(message = "El nombre es obligatorio")
    @Size(max = 50, message = "El nombre no puede exceder 50 caracteres")
    String nombre,

    @NotBlank(message = "Los apellidos son obligatorios")
    @Size(max = 100, message = "Los apellidos no pueden exceder 100 caracteres")
    String apellidos,

    @NotNull(message = "El teléfono es obligatorio")
    @Pattern(regexp = "^[6-9]\\d{8}$", message = "El teléfono debe ser un número válido de 9 dígitos")
    String telefono,

    @NotBlank(message = "El email es obligatorio")
    @Email(message = "El email debe ser válido")
    @Size(max = 100, message = "El email no puede exceder 100 caracteres")
    String email,
    
    @NotBlank(message = "La contraseña es obligatoria")
    @Size(min = 8, message = "La contraseña debe tener al menos 8 caracteres")
    String contrasenia,

    @NotNull(message = "La fecha de nacimiento es obligatoria")
    @Past(message = "La fecha de nacimiento debe ser en el pasado")
    LocalDate fecha_nacimiento,

    @NotBlank(message = "La localidad es obligatoria")
    @Size(max = 50, message = "La localidad no puede exceder 50 caracteres")
    String localidad,

    @NotBlank(message = "La provincia es obligatoria")
    @Size(max = 50, message = "La provincia no puede exceder 50 caracteres")
    String provincia,

    @NotNull(message = "El estado activo es obligatorio")
    Boolean activo

    ) implements Serializable {

}
