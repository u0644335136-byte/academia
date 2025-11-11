package com.jdk21.academia.features.convocatoria.mapper;
import com.jdk21.academia.domain.Centro;
import com.jdk21.academia.domain.Convocatoria;
import com.jdk21.academia.domain.Curso;
import com.jdk21.academia.features.baseFeature.mapper.BaseMapper;
import com.jdk21.academia.features.convocatoria.dto.ConvocatoriaRequestDTO;
import com.jdk21.academia.features.convocatoria.dto.ConvocatoriaResponseDTO;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;


@Mapper(componentModel = "spring")
public interface ConvocatoriaMapper extends BaseMapper<Convocatoria, ConvocatoriaRequestDTO, ConvocatoriaResponseDTO> {

    @Override
    @Mapping(target = "curso", source = "idCurso")
    @Mapping(target = "centro", source = "idCentro")
    Convocatoria toEntity(ConvocatoriaRequestDTO dto);

    @Override
    @Mapping(target = "idCurso", source = "curso.idCurso")
    @Mapping(target = "cursoNombre", source = "curso.nombre")
    @Mapping(target = "idCentro", source = "centro.id")
    @Mapping(target = "centroNombre", source = "centro.nombre")
    ConvocatoriaResponseDTO toDto(Convocatoria entity);

    @Override
    void updateEntityFromDto(ConvocatoriaRequestDTO dto, @MappingTarget Convocatoria entity);

    // Mapeo entidades/llaves foraneas
    default Curso mapCurso(Long id) {
        if (id == null) return null;
        return Curso.builder().idCurso(id).build();
    }

    default Centro mapCentro(Long id) {
        if (id == null) return null;
        Centro c = new Centro();
        c.setId(id);
        return c;
    }


}