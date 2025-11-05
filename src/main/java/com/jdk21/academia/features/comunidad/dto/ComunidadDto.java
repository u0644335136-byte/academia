package com.jdk21.academia.features.comunidad.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ComunidadDto {
    private Long idComunidad;
    private String codigo;
    private String nombre;
    private String capital;
    private LocalDateTime fechaCreacion;
    private LocalDateTime fechaActualizacion;
    private Boolean activo;
}