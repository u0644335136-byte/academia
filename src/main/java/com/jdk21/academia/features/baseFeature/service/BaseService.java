package com.jdk21.academia.features.baseFeature.service;


import com.jdk21.academia.domain.BaseEntity;
import com.jdk21.academia.features.baseFeature.repository.BaseRepository;
import com.jdk21.academia.features.baseFeature.mapper.BaseMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

//El objetivo de esta clase es simplificar el CRUD
@Transactional
public abstract class BaseService<E extends BaseEntity, D, ID> {

    @Autowired
    protected BaseRepository<E> repository;

    @Autowired
    protected BaseMapper<E, D> mapper;

    // CREAR
    public D create(D dto) {
        E entity = mapper.toEntity(dto);
        entity.setActivo(true);
        return mapper.toDto(repository.save(entity));
    }

    // ACTUALIZAR
    public D update(ID id, D dto) {
        E existing = repository.findById((Long) id)
                .orElseThrow(() -> new RuntimeException("Entity not found"));
        E updated = mapper.toEntity(dto);
        updated.setId(existing.getId());
        updated.setActivo(existing.getActivo());
        return mapper.toDto(repository.save(updated));
    }

    // SOFT DELETE
    public void delete(ID id) {
        E entity = repository.findById((Long) id)
                .orElseThrow(() -> new RuntimeException("Entity not found"));
        entity.setActivo(false);
        repository.save(entity);
    }

    // GET BY ID
    public Optional<D> getById(ID id) {
        return repository.findById((Long) id)
                .filter(BaseEntity::getActivo)
                .map(mapper::toDto);
    }

    // GET ALL (solo va a retornar los activos)
    public List<D> getAll() {
        return repository.findAll().stream()
                .filter(BaseEntity::getActivo)
                .map(mapper::toDto)
                .collect(Collectors.toList());
    }
}
