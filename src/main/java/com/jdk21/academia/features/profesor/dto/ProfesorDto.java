package com.jdk21.academia.features.profesor.dto;

import java.time.LocalDate;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProfesorDto {
    @Schema(description = "ID del curso (solo para actualización)", example = "1")
    @Positive(message = "El ID del profesor debe ser positivo")
    private Long idProfesor;

    @Schema(description = "Nombre del profesor", example = "Cristian")
    @NotBlank(message = "El nombre del profesor no puede estar vacío")
    @Size(max = 150, message = "El nombre del profesor no puede superar 150 caracteres")
    private String nombre;

    @Schema(description = "Apellidos del profesor", example = "Palomino Espinoza")
    @NotBlank(message = "Los apellidos del profesor no puede estar vacío")
    @Size(max = 150, message = "Los apellidos del profesor no puede superar 150 caracteres")
    private String apellidos;

    @Schema(description = "Teléfono del profesor", example = "+34 666 55 44 33")
    @Size(max = 50, message = "El teléfono del profesor no puede superar 50 caracteres")
    private String telefono;

    @Schema(description = "Email del profesor", example = "profesor@academia.com")
    @NotBlank(message = "El email del profesor no puede estar vacío")
    @Size(max = 150, message = "El email del profesor no puede superar 150 caracteres")
    private String email;

    @Schema(description = "Dirección del profesor", example = "avenida de la avenida")
    @Size(max = 150, message = "La dirección del profesor no puede superar 150 caracteres")
    private String direccion;

    @Schema(description = "Localidad del profesor", example = "Ventas")
    @Size(max = 150, message = "La localidad del profesor no puede superar 150 caracteres")
    private String localidad;

    @Schema(description = "Provincia del profesor", example = "Madrid")
    @Size(max = 30, message = "La dirección del profesor no puede superar 30 caracteres")
    private String provincia;
    private boolean activo;

    
    private LocalDate fechaNacimiento;

    
    private String contrasenia;
}