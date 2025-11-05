package com.jdk21.academia.features.centro.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import com.jdk21.academia.domain.Centro;
import com.jdk21.academia.features.centro.dto.CentroDTO;
import com.jdk21.academia.features.centro.dto.CreateCentroDTO;
import com.jdk21.academia.features.centro.dto.UpdateCentroDTO;


@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface CentroMapper {
    
    //@Mapping(target = "idempresa",source = "empresa.id")
    @Mapping(target = "empresaNombre", source = "empresa.nombreLegal")
    
    CentroDTO toDTO(Centro centro);
    
    @Mapping(target = "idCentro", ignore = true)
    @Mapping(target = "empresa", ignore = true)
    @Mapping(target = "fechaCreacion", ignore = true)
    @Mapping(target = "fechaActualizacion", ignore = true)
    Centro toEntity(CreateCentroDTO createDTO);
    
    @Mapping(target = "idCentro", ignore = true)
    @Mapping(target = "empresa", ignore = true)
    @Mapping(target = "fechaCreacion", ignore = true)
    @Mapping(target = "fechaActualizacion", ignore = true)
    void updateEntityFromDTO(UpdateCentroDTO updateDTO, @MappingTarget Centro centro);
}
