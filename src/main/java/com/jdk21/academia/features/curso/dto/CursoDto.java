package com.jdk21.academia.features.curso.dto;

import lombok.Data;

@Data
public class CursoDto {
    private Long idCurso;
    private String nombre;
    private String descripcion;
    private Boolean activo;
    private Integer duracionHoras;
}
