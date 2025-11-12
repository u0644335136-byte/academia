package com.jdk21.academia.features.calificacion.mapper;


import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import com.jdk21.academia.domain.Alumno;
import com.jdk21.academia.domain.Convocatoria;
import com.jdk21.academia.domain.Matricula;
import com.jdk21.academia.domain.Calificacion;
import com.jdk21.academia.features.baseFeature.mapper.BaseMapper;
import com.jdk21.academia.features.calificacion.dto.CalificacionRequestDTO;
import com.jdk21.academia.features.calificacion.dto.CalificacionResponseDTO;

@Mapper(componentModel = "spring")
public interface CalificacionMapper extends BaseMapper<Calificacion, CalificacionRequestDTO, CalificacionResponseDTO> {

    @Override
    @Mapping(target = "matricula", source = "idMatricula")
    Calificacion toEntity(CalificacionRequestDTO dto);

    @Override
    @Mapping(target = "idMatricula", source = "matricula.id")
    @Mapping(target = "matriculaCodigo", source = "matricula.codigo")
    CalificacionResponseDTO toDto(Calificacion entity);


    @Override
    void updateEntityFromDto(CalificacionRequestDTO dto, @MappingTarget Calificacion entity);


    // Mapeo entidades/llaves foraneas
    default Matricula mapMatricula(Long id) {
        if (id == null) return null;
        Matricula m = new Matricula();
        m.setId(id);
        return m;
    }


}

