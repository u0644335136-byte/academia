package com.jdk21.academia.features.comunidad.service;

import com.jdk21.academia.features.comunidad.dto.ComunidadDto;
import com.jdk21.academia.features.comunidad.mapper.ComunidadMapper;
import com.jdk21.academia.features.comunidad.repository.ComunidadRepository;
import com.jdk21.academia.domain.Comunidad;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ComunidadService {

    private final ComunidadRepository repository;
    private final ComunidadMapper mapper;

    public List<ComunidadDto> listarTodas() {
        return repository.findAll()
                .stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
    }

    public ComunidadDto obtenerPorId(Long idComunidad) {
        return repository.findById(idComunidad)
                .map(mapper::toDto)
                .orElse(null);
    }

    public ComunidadDto guardar(ComunidadDto dto) {
        Comunidad entidad = mapper.toEntity(dto);
        Comunidad guardada = repository.save(entidad);
        return mapper.toDto(guardada);
    }

    public void eliminar(Long idComunidad) {
        repository.deleteById(idComunidad);
    }
}
