package com.jdk21.academia.features.matricula.dto;

import java.sql.Date;

public record MatriculaResponseDTO (
    Long id,
    Date fecha,
    String codigo,
    int precio,
    Long idConvocatoria,
    String convocatoriaCodigo,
    Long idAlumno,
    String alumnoEmail,
    int nota
) {}

