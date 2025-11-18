package com.jdk21.academia.features.alumno.dto;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO para la actualización de alumnos.
 * Los campos son opcionales para permitir actualizaciones parciales.
 * La fecha de nacimiento no incluye @Past porque raramente cambia y puede causar problemas
 * de validación cuando se envía la misma fecha existente.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AlumnoUpdateInputDto {

    @JsonProperty("nombre")
    @Size(max = 50, message = "El nombre no puede exceder 50 caracteres")
    private String nombre;

    @JsonProperty("apellidos")
    @Size(max = 100, message = "Los apellidos no pueden exceder 100 caracteres")
    private String apellidos;

    @JsonProperty("telefono")
    @Pattern(regexp = "^[6-9]\\d{8}$", message = "El teléfono debe ser un número válido de 9 dígitos")
    private String telefono;

    @JsonProperty("email")
    @Email(message = "El email debe ser válido")
    @Size(max = 100, message = "El email no puede exceder 100 caracteres")
    private String email;
    
    @JsonProperty("contrasenia")
    @Size(min = 8, message = "La contraseña debe tener al menos 8 caracteres")
    private String contrasenia;

    @JsonProperty("fecha_nacimiento")
    private LocalDate fecha_nacimiento;

    @JsonProperty("localidad")
    @Size(max = 50, message = "La localidad no puede exceder 50 caracteres")
    private String localidad;

    @JsonProperty("provincia")
    @Size(max = 50, message = "La provincia no puede exceder 50 caracteres")
    private String provincia;

    @JsonProperty("activo")
    private Boolean activo;
}

