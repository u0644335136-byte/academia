package com.jdk21.academia.features.comunidad.mapper;

import com.jdk21.academia.domain.Comunidad;
import com.jdk21.academia.features.comunidad.dto.ComunidadDto;
import org.springframework.stereotype.Component;

@Component
public class ComunidadMapper {

    public ComunidadDto toDto(Comunidad entidad) {
        if (entidad == null) return null;
        return ComunidadDto.builder()
                .idComunidad(entidad.getIdComunidad())
                .codigo(entidad.getCodigo())
                .nombre(entidad.getNombre())
                .capital(entidad.getCapital())
                .fechaCreacion(entidad.getFechaCreacion())
                .fechaActualizacion(entidad.getFechaActualizacion())
                .activo(entidad.isActivo())
                .build();
    }

    public Comunidad toEntity(ComunidadDto dto) {
        if (dto == null) return null;
        Comunidad entidad = new Comunidad();

        // ⚠️ Solo asignamos ID si existe (PUT), nunca en POST
        if (dto.getIdComunidad() != null) {
            entidad.setIdComunidad(dto.getIdComunidad());
        }

        entidad.setCodigo(dto.getCodigo());
        entidad.setNombre(dto.getNombre());
        entidad.setCapital(dto.getCapital());
        entidad.setActivo(dto.getActivo() != null && dto.getActivo());

        // Fechas manejadas por la BD, no se setean manualmente
        return entidad;
    }
}




