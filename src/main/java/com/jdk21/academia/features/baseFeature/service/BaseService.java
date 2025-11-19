package com.jdk21.academia.features.baseFeature.service;


import com.jdk21.academia.domain.BaseEntity;
import com.jdk21.academia.features.baseFeature.repository.BaseRepository;
import com.jdk21.academia.features.baseFeature.mapper.BaseMapper;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;



//El objetivo de esta clase es simplificar el CRUD
@Transactional
public abstract class BaseService<E extends BaseEntity, I, O, ID> {

    protected final BaseRepository<E> repository;
    protected final BaseMapper<E, I, O> mapper;

    protected BaseService(BaseRepository<E> repository, BaseMapper<E, I, O> mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    // Crear
    public O create(I dto) {
        E entity = mapper.toEntity(dto);
        // System.out.println("Mapped entity: " + entity);
        return mapper.toDto(repository.save(entity));
    }

    // Actualizar
    public O update(ID id, I dto) {
        E existing = repository.findById((Long) id)
                .orElseThrow(() -> new RuntimeException("Entidad no encontrada"));

        mapper.updateEntityFromDto(dto, existing);

        return mapper.toDto(repository.save(existing));
    }

    // SOFT DELETE (no elimina registro sino que cambia el activo a 0)
    public void delete(ID id) {
        E entity = repository.findById((Long) id)
                .orElseThrow(() -> new RuntimeException("Entidad inexistente"));
        entity.setActivo(false);
        repository.save(entity);
    }

    // Recoge todos por ID
    public Optional<O> getById(ID id) {
        return repository.findById((Long) id)
                .filter(BaseEntity::getActivo)
                .map(mapper::toDto);
    }

    // Recoge todos los datos ordenados por fecha de creación descendente
public List<O> getAll() {
    return repository.findAll(Sort.by(Sort.Direction.DESC, "fechaCreacion")).stream()
            .filter(BaseEntity::getActivo)
            .map(mapper::toDto)
            .collect(Collectors.toList());
}
}
