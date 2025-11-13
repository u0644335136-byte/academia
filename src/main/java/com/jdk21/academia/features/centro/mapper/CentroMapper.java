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
    CentroDTO toDTO (Centro centro);
    Centro toEntity(CentroDTO centroDTO);
}

/*
    // ==========
    // ENTITY -> DTO
    // ==========

    @Mapping(target = "id_centro")
    // campo raro en entidad
    @Mapping(target = "codigoPostal", source = "codigo_postal") 
    CentroDTO toDTO(Centro centro);

    List<CentroDTO> toDTOList(List<Centro> centros);

    // ==========
    // CREATE DTO -> ENTITY
    // ==========

    @Mapping(target = "idCentro", source = "id_centro")
    @Mapping(target = "codigoPostal", source = "codigo_postal")
    @Mapping(target = "fechaActualiza", source = "fechaActualizacion")
    @Mapping(target = "capacidadMax", ignore = true)
    @Mapping(target = "idempresa", ignore = true)
    @Mapping(target = "idcomunidad", ignore = true)
    @Mapping(target = "empresaNombre", ignore = true)
    Centro toEntity(CreateCentroDTO createCentroDTO);

    // ==========
    // UPDATE DTO -> ENTITY (patch)
    // ==========

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "codigo_postal", source = "codigoPostal")
    @Mapping(target = "idempresa", ignore = true)
    @Mapping(target = "idcomunidad", ignore = true)
    @Mapping(target = "empresaNombre", ignore = true)
    @Mapping(target = "fechaCreacion", ignore = true)
    @Mapping(target = "fechaActualizacion", ignore = true)
    void updateEntityFromDTO(UpdateCentroDTO updateDTO, @MappingTarget Centro centro);
}

/*
 * package com.jdk21.academia.features.centro.mapper;
 * 
 * import org.mapstruct.Mapper;
 * import org.mapstruct.Mapping;
 * import org.mapstruct.MappingTarget;
 * import org.mapstruct.NullValuePropertyMappingStrategy;
 * import com.jdk21.academia.domain.Centro;
 * import com.jdk21.academia.features.centro.dto.CentroDTO;
 * import com.jdk21.academia.features.centro.dto.CreateCentroDTO;
 * import com.jdk21.academia.features.centro.dto.UpdateCentroDTO;
 * 
 * 
 * @Mapper(
 * componentModel = "spring",
 * nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
 * )
 * public interface CentroMapper {
 * /// Mapeo de Centro a CentroDTO
 * 
 * @Mapping(target = "idCentro", source = "id")
 * 
 * @Mapping(target = "codigoPostal", source = "codigo_postal")
 * 
 * @Mapping(target = "fechaActualiza", source = "fechaActualizacion")
 * 
 * @Mapping(target = "capacidadMax", ignore = true)
 * 
 * @Mapping(target = "idempresa", ignore = true)
 * 
 * @Mapping(target = "idcomunidad", ignore = true)
 * 
 * @Mapping(target = "empresaNombre", ignore = true)
 * CentroDTO toDTO(Centro entity);
 * 
 * 
 * // CREATE DTO
 * 
 * @Mapping(target = "id", ignore = true)
 * 
 * @Mapping(target = "codigo_centro", source = "codigo_centro")
 * 
 * @Mapping(target = "codigo_postal", source = "codigoPostal")
 * 
 * @Mapping(target = "fechaActualizacion", ignore = true)
 * 
 * @Mapping(target = "fechaCreacion", ignore = true)
 * Centro toEntity (CreateCentroDTO createCentroDTO);
 * 
 * // UPDATE DTO
 * 
 * @Mapping(target = "id", ignore = true)
 * 
 * @Mapping(target = "codigo_postal", source = "codigoPostal")
 * 
 * @Mapping(target = "fechaActualizacion", ignore = true)
 * 
 * @Mapping(target = "fechaCreacion", ignore = true)
 * void updateEntityFromDTO(UpdateCentroDTO updateDTO, @MappingTarget Centro
 * centro);
 * 
 * 
 * 
 * 
 * }
 */