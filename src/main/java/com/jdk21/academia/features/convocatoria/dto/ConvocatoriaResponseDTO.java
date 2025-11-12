package com.jdk21.academia.features.convocatoria.dto;

import java.sql.Date;

public record ConvocatoriaResponseDTO(
    Long id,
    String codigo,
    Date fechaInicio,
    Date fechaFin,
    Long idCurso,
    String cursoNombre,
    Long idCatalogo,
    Long idProfesor,
    String profesorEmail,
    Long idCentro,
    String centroNombre
){} 