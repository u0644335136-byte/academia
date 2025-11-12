package com.jdk21.academia.features.materia.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MateriaDto {

    @Schema(description = "ID de la materia (solo para actualización)", example = "1")
    @Positive(message = "El ID de la materia debe ser positivo")
    private Long idMateria;

    @Schema(description = "Nombre de la materia", example = "Matemáticas Aplicadas")
    @NotBlank(message = "El nombre de la materia no puede estar vacío")
    @Size(max = 150, message = "El nombre de la materia no puede superar 150 caracteres")
    private String nombre;

    @Schema(description = "Descripción de la materia", example = "Curso introductorio de matemáticas aplicadas al análisis de datos")
    private String descripcion;

    @Schema(description = "Indica si la materia está activa", example = "true")
    private Boolean activo;

    @Column(name = "fecha_creacion", insertable = false, updatable = false)
    @Schema(description = "Fecha de creación de la materia (automática)", example = "2025-11-05T10:30:00")
    private LocalDateTime fechaCreacion;

    @Column(name = "fecha_actualizacion", insertable = false, updatable = false)
    @Schema(description = "Fecha de última actualización de la materia (automática)", example = "2025-11-05T10:30:00")
    private LocalDateTime fechaActualizacion;
}
