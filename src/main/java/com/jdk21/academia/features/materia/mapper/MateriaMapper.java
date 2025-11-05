package com.jdk21.academia.features.materia.mapper;

import com.jdk21.academia.domain.Materia;
import com.jdk21.academia.features.materia.dto.MateriaDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface MateriaMapper {
    MateriaMapper INSTANCE = Mappers.getMapper(MateriaMapper.class);
    MateriaDto toDto(Materia materia);
    Materia toEntity(MateriaDto dto);
}
