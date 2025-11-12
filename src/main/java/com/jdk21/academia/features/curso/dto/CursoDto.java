package com.jdk21.academia.features.curso.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CursoDto {

    @Schema(description = "ID del curso (solo para actualización, no enviar al crear)", example = "1")
    @JsonProperty("idCurso")
    private Long idCurso;

    @Schema(description = "ID de la materia", example = "10")
    @Positive(message = "El ID de la materia debe ser positivo")
    @JsonProperty("idMateria")
    private Long idMateria;

    @Schema(description = "ID del formato", example = "2")
    @Positive(message = "El ID del formato debe ser positivo")
    @JsonProperty("idFormato")
    private Long idFormato;

    @Schema(description = "Nombre del curso", example = "Introducción a Java")
    @NotBlank(message = "El nombre del curso no puede estar vacío")
    @Size(max = 150, message = "El nombre del curso no puede superar 150 caracteres")
    @JsonProperty("nombre")
    private String nombre;

    @Schema(description = "Descripción del curso", example = "Curso básico de Java para principiantes")
    @JsonProperty("descripcion")
    private String descripcion;

    @Schema(description = "Duración en horas", example = "40")
    @PositiveOrZero(message = "La duración debe ser cero o positiva")
    @Max(value = 1000, message = "La duración no puede superar 1000 horas")
    @JsonProperty("duracionHoras")
    private Integer duracionHoras;

}
