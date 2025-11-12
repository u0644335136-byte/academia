package com.jdk21.academia.features.convocatoria.dto;

import java.sql.Date;

public record ConvocatoriaRequestDTO(
    String codigo,
    Date fechaInicio,
    Date fechaFin,
    Long idCurso,
    Long idCatalogo,
    Long idProfesor,
    Long idCentro
) {

}
