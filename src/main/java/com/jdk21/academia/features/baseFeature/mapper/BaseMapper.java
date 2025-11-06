package com.jdk21.academia.features.baseFeature.mapper;

public interface BaseMapper<E, D> {
    E toEntity(D dto);
    D toDto(E entity);
}

