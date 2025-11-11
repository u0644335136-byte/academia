package com.jdk21.academia.features.calificacion.dto;

import java.sql.Date;

public record CalificacionRequestDTO(    
    Date fecha,
    int nota,
    Long idMatricula
) {}
