package com.jdk21.academia.features.curso.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CursoDto {

    @Schema(description = "ID del curso (solo para actualización)", example = "1")
    private Long idCurso;

    @Schema(description = "ID de la materia", example = "10")
    private Long idMateria;

    @Schema(description = "ID del formato", example = "2")
    private Long idFormato;

    @Schema(description = "Nombre del curso", example = "Introducción a Java")
    private String nombre;

    @Schema(description = "Descripción del curso", example = "Curso básico de Java para principiantes")
    private String descripcion;

    @Schema(description = "Duración en horas", example = "40")
    private Integer duracionHoras;
}
