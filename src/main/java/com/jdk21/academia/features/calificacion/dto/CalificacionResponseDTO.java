package com.jdk21.academia.features.calificacion.dto;

import java.sql.Date;

public record CalificacionResponseDTO(    
    Long id,
    Date fecha,
    int nota,
    Long idMatricula,
    String matriculaCodigo
) {}
