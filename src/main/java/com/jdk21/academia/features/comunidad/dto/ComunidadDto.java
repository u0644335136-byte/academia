package com.jdk21.academia.features.comunidad.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ComunidadDto {

    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    private Long idComunidad;

    private String codigo;
    private String nombre;
    private String capital;

    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    private LocalDateTime fechaCreacion;

    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    private LocalDateTime fechaActualizacion;

    private Boolean activo;
}

