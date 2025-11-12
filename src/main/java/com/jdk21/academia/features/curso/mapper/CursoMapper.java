package com.jdk21.academia.features.curso.mapper;

import com.jdk21.academia.domain.Curso;
import com.jdk21.academia.features.curso.dto.CursoDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface CursoMapper {

    CursoMapper INSTANCE = Mappers.getMapper(CursoMapper.class);

    CursoDTO toDto(Curso curso);

    Curso toEntity(CursoDTO cursoDto);
}
