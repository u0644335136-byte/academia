package com.jdk21.academia.features.comunidad.mapper;

import com.jdk21.academia.domain.Comunidad;
import com.jdk21.academia.features.comunidad.dto.ComunidadDto; // <--- ¡Asegúrate de importar tu DTO de SALIDA!
import com.jdk21.academia.features.comunidad.dto.CreateComunidadDTO;
import com.jdk21.academia.features.comunidad.dto.UpdateComunidadDTO;

import org.springframework.stereotype.Component;

@Component
public class ComunidadMapper {

    // ==============================================
    // 1. Mapeo de Entidad a DTO de SALIDA (FALTANTE)
    //    Usado por los métodos GET y Mutaciones
    // ==============================================
    public ComunidadDto toDto(Comunidad entidad) {
        if (entidad == null) return null;
        return ComunidadDto.builder()
                .idComunidad(entidad.getIdComunidad())
                .codigo(entidad.getCodigo())
                .nombre(entidad.getNombre())
                .capital(entidad.getCapital())
                .fechaCreacion(entidad.getFechaCreacion())
                .fechaActualizacion(entidad.getFechaActualizacion())
                .activo(entidad.isActivo()) // Asumiendo que la entidad usa isActivo()
                .build();
    }
    
    // ==============================================
    // 2. Mapeo de DTO de SALIDA a Entidad (FALTANTE)
    //    Usado a veces, aunque se prefiere usar CreateDTO para crear
    // ==============================================
    public Comunidad toEntity(ComunidadDto dto) {
        if (dto == null) return null;
        Comunidad entidad = new Comunidad();
        
        // El ID solo se setea para actualización, no para creación.
        if (dto.getIdComunidad() != null) {
            entidad.setIdComunidad(dto.getIdComunidad());
        }

        entidad.setCodigo(dto.getCodigo());
        entidad.setNombre(dto.getNombre());
        entidad.setCapital(dto.getCapital());
        entidad.setActivo(dto.getActivo() != null && dto.getActivo());
        
        return entidad;
    }


    // ==============================================
    // 3. Mapeo de CreateDTO a Entity (YA LO TENÍAS)
    //    Usado por el método createComunidad()
    // ==============================================
    public Comunidad toEntity(CreateComunidadDTO dto) {
        if (dto == null) return null;
        Comunidad entidad = new Comunidad();
        entidad.setCodigo(dto.getCodigo());
        entidad.setNombre(dto.getNombre());
        entidad.setCapital(dto.getCapital());
        entidad.setActivo(dto.getActivo() != null && dto.getActivo());
        return entidad;
    }

    // ==============================================
    // 4. Mapeo de UpdateDTO (YA LO TENÍAS)
    //    Usado por el método updateComunidad()
    // ==============================================
    public void updateEntity(Comunidad entidad, UpdateComunidadDTO dto) {
        if (dto.getCodigo() != null) entidad.setCodigo(dto.getCodigo());
        if (dto.getNombre() != null) entidad.setNombre(dto.getNombre());
        if (dto.getCapital() != null) entidad.setCapital(dto.getCapital());
        if (dto.getActivo() != null) entidad.setActivo(dto.getActivo());
    }
}