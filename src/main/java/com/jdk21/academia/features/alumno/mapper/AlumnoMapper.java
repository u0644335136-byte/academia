package com.jdk21.academia.features.alumno.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import com.jdk21.academia.domain.Alumno;
import com.jdk21.academia.features.alumno.dto.AlumnoCreateInputDto;
import com.jdk21.academia.features.alumno.dto.AlumnoDto;

@Mapper(componentModel = "spring")
public interface AlumnoMapper {

    // Instancia estática generada por MapStruct.
    AlumnoMapper INSTANCE = Mappers.getMapper(AlumnoMapper.class);

    // @Mapping: Ignoramos campos que no mapean directamente, como id (generado), fechas auto (ignoradas en DTO).
    @Mapping(target = "id_alumno", ignore = true) // Ignorado porque es generado por BD.
    @Mapping(target = "fechaCreacion", ignore = true) // Gestionado por BD.
    @Mapping(target = "fechaActualizacion", ignore = true) // Gestionado por BD.
    Alumno toEntityFromCreateInput(AlumnoCreateInputDto dto);

    // Mapeo inverso: De entidad a DTO.
    @Mapping(source = "id_alumno", target = "id_alumno")
    AlumnoDto toDTO(Alumno entity);

    // Para listas: Convierte List<Alumno> a List<AlumnoDTO>.
    List<AlumnoDto> toDTOList(List<Alumno> entities);
}
