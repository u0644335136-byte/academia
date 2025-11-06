package com.jdk21.academia.features.baseFeature.mapper;

import org.mapstruct.MappingTarget;

public interface BaseMapper<E, I, O> {
    // DTO a entidad
    E toEntity(I dto);

    //Entidad a DTO
    O toDto(E entity);

    //Actualiza la entidad desde el DTO
    void updateEntityFromDto(I dto, @MappingTarget E entity);
}
