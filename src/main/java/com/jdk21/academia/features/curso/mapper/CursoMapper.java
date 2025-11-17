package com.jdk21.academia.features.curso.mapper;

import com.jdk21.academia.domain.Curso;
import com.jdk21.academia.features.curso.dto.CursoDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface CursoMapper {

    CursoMapper INSTANCE = Mappers.getMapper(CursoMapper.class);

    CursoDto toDto(Curso curso);

    @Mapping(target = "fechaCreacion", ignore = true) // Gestionado por BD.
    @Mapping(target = "fechaActualizacion", ignore = true) // Gestionado por BD.
    Curso toEntity(CursoDto cursoDto);
}
