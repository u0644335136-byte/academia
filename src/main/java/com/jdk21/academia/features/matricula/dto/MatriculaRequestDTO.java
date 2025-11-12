package com.jdk21.academia.features.matricula.dto;

import java.sql.Date;

public record MatriculaRequestDTO(
    Date fecha,
    String codigo,
    int precio,
    Long idConvocatoria,
    Long idAlumno
) {}
