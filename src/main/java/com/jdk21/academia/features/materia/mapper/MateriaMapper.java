package com.jdk21.academia.features.materia.mapper;

import com.jdk21.academia.domain.Materia;
import com.jdk21.academia.features.materia.dto.MateriaDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface MateriaMapper {

    MateriaMapper INSTANCE = Mappers.getMapper(MateriaMapper.class);

    Materia toEntity(MateriaDTO dto);

    MateriaDTO toDto(Materia materia);
}
