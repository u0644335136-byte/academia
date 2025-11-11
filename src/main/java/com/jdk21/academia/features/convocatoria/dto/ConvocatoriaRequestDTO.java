package com.jdk21.academia.features.convocatoria.dto;

import java.time.LocalDate;

public record ConvocatoriaRequestDTO(
    String codigo,
    LocalDate fechaInicio,
    LocalDate fechaFin,
    Long idCurso,
    Long idCatalogo,
    Long idProfesor,
    Long idCentro
) {

}
