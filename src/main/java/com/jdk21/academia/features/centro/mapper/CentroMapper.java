package com.jdk21.academia.features.centro.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.factory.Mappers;

import com.jdk21.academia.domain.Centro;
import com.jdk21.academia.features.centro.dto.CentroDTO;
import com.jdk21.academia.features.centro.dto.CreateCentroDTO;
import com.jdk21.academia.features.centro.dto.UpdateCentroDTO;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface CentroMapper {
    CentroMapper INSTANCE = Mappers.getMapper(CentroMapper.class);
    
    // ==========
    // ENTITY -> DTO
    // ==========
    
    @Mapping(target = "id_Centro", source = "id")
    @Mapping(target = "capacidad_maxima", ignore = true)
    CentroDTO toDTO(Centro centro);
    
    List<CentroDTO> toDTOList(List<Centro> centros);
    
    // ==========
    // CREATE DTO -> ENTITY
    // ==========
    
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "codigo_postal", ignore = false)
    //@Mapping(target = "id_empresa", ignore = true)
    //@Mapping(target = "id_comunidad", ignore = true)
    @Mapping(target = "fechaCreacion", ignore = true)
    @Mapping(target = "fechaActualizacion", ignore = true)
    @Mapping(target = "activo", ignore = true)
    Centro toEntity(CreateCentroDTO createCentroDTO);
    
    // ==========
    // UPDATE DTO -> ENTITY (patch)
    // ==========
    
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "codigo_postal",ignore = true)
    @Mapping(target = "id_empresa", ignore = true)
    @Mapping(target = "id_comunidad", ignore = true)
    @Mapping(target = "fechaCreacion", ignore = true)
    @Mapping(target = "fechaActualizacion", ignore = true)
    void updateEntityFromDTO(UpdateCentroDTO updateDTO, @MappingTarget Centro centro);
}