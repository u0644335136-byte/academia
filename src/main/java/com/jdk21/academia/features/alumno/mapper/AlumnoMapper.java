package com.jdk21.academia.features.alumno.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.jdk21.academia.domain.Alumno;
import com.jdk21.academia.features.alumno.dto.AlumnoCreateInputDto;
import com.jdk21.academia.features.alumno.dto.AlumnoDto;

@Mapper(componentModel = "spring")
public interface AlumnoMapper {

    // Convierte AlumnoCreateInputDto a entidad Alumno
    @Mapping(target = "id_alumno", ignore = true) // Ignorado porque es generado por BD
    @Mapping(target = "fechaCreacion", ignore = true) // Gestionado por BD
    @Mapping(target = "fechaActualizacion", ignore = true) // Gestionado por BD
    Alumno toEntityFromCreateInput(AlumnoCreateInputDto dto);

    // Convierte entidad Alumno a AlumnoDto
    @Mapping(source = "id_alumno", target = "id_alumno")
    AlumnoDto toDTO(Alumno entity);

    // Convierte lista de entidades a lista de DTOs
    List<AlumnoDto> toDTOList(List<Alumno> entities);
}
