package com.jdk21.academia.features.comunidad.service;

import com.jdk21.academia.domain.Comunidad;
import com.jdk21.academia.features.comunidad.dto.ComunidadDto;
import com.jdk21.academia.features.comunidad.mapper.ComunidadMapper;
import com.jdk21.academia.features.comunidad.repository.ComunidadRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ComunidadServiceImpl implements ComunidadService {

    private final ComunidadRepository repository;
    private final ComunidadMapper mapper;

    @Override
    public List<ComunidadDto> listarTodas() {
        return repository.findAll()
                .stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public ComunidadDto obtenerPorId(Long idComunidad) {
        return repository.findById(idComunidad)
                .map(mapper::toDto)
                .orElse(null);
    }

    @Override
    public ComunidadDto guardar(ComunidadDto dto) {
        Comunidad entidad = mapper.toEntity(dto);
        entidad.setIdComunidad(null); // dejar que la BD genere el ID
        Comunidad guardada = repository.save(entidad);
        return mapper.toDto(guardada);
    }

    @Override
    public ComunidadDto actualizar(Long idComunidad, ComunidadDto dto) {
        return repository.findById(idComunidad)
                .map(existente -> {
                    existente.setCodigo(dto.getCodigo());
                    existente.setNombre(dto.getNombre());
                    existente.setCapital(dto.getCapital());
                    existente.setActivo(dto.getActivo() != null ? dto.getActivo() : existente.isActivo());
                    Comunidad actualizada = repository.save(existente);
                    return mapper.toDto(actualizada);
                })
                .orElse(null);
    }

    @Override
    public void eliminar(Long idComunidad) {
        if (repository.existsById(idComunidad)) {
            repository.deleteById(idComunidad);
        }
    }
}

