package com.jdk21.academia.features.profesor.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.jdk21.academia.domain.Profesor;
import com.jdk21.academia.features.profesor.dto.ProfesorDto;

@Mapper
public interface ProfesorMapper {
    ProfesorMapper INSTANCE = Mappers.getMapper(ProfesorMapper.class);

    ProfesorDto toDto(Profesor profesor);

    Profesor toEntity(ProfesorDto profesorDto);
}