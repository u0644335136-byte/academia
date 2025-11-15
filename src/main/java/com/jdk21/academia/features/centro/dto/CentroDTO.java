package com.jdk21.academia.features.centro.dto;

import java.io.Serializable;
import java.time.LocalDateTime;

public record CentroDTO(
        Long id_Centro,
        String codigo_centro,
        String nombre,
        String responsable,
        Integer capacidad_maxima,
        Boolean activo,
        Long id_empresa,
        Long id_comunidad,
        Integer telefono,
        String email,
        String web,
        String direccion,
        Integer numero,
        Integer piso,
        String puerta,
        Integer codigo_postal,
        String localidad,
        String provincia,
        LocalDateTime fechaCreacion,
        LocalDateTime fechaActualizacion) implements Serializable {
    private static final long serialVersionUID = 1L;
}
