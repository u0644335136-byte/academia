package com.jdk21.academia.features.alumno.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO para la respuesta paginada de alumnos.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AlumnoPageDto {
    private List<AlumnoDto> alumnos;
    private Long totalElements;
    private Integer totalPages;
    private Integer currentPage;
    private Integer pageSize;
}

