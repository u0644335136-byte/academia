package com.jdk21.academia.features.convocatoria.dto;

import java.time.LocalDate;

public record ConvocatoriaResponseDTO(
    Long id,
    String codigo,
    LocalDate fechaInicio,
    LocalDate fechaFin,
    Long idCurso,
    String cursoNombre,
    Long idCatalogo,
    Long idProfesor,
    Long idCentro,
    String centroNombre
){} 