package com.jdk21.academia.features.comunidad.service;

import com.jdk21.academia.features.comunidad.dto.ComunidadDto; 
import com.jdk21.academia.features.comunidad.dto.CreateComunidadDTO;
import com.jdk21.academia.features.comunidad.dto.UpdateComunidadDTO;
import com.jdk21.academia.features.comunidad.mapper.ComunidadMapper;
import com.jdk21.academia.features.comunidad.repository.ComunidadRepository;
import com.jdk21.academia.domain.Comunidad;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ComunidadService {

    private final ComunidadRepository repository;
    private final ComunidadMapper mapper;

    // Renombramos listarTodas() para ser más claro para GraphQL
    public List<ComunidadDto> getAllComunidades() { // <--- CORREGIDO: Usar ComunidadDto
        return repository.findAll()
                .stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
    }

    // Renombramos obtenerPorId()
    public ComunidadDto getComunidadById(Long idComunidad) { // <--- CORREGIDO: Usar ComunidadDto
        return repository.findById(idComunidad)
                .map(mapper::toDto)
                .orElse(null);
    }
    
    // Nuevo método de servicio para buscar por código
    public ComunidadDto getComunidadByCodigo(String codigo) { // <--- CORREGIDO: Usar ComunidadDto
        Comunidad comunidad = repository.findByCodigo(codigo);
        return mapper.toDto(comunidad);
    }


    // Ajustamos 'guardar' para usar CreateComunidadDTO
    public ComunidadDto createComunidad(CreateComunidadDTO dto) { // <--- CORREGIDO: Usar ComunidadDto
        Comunidad entidad = mapper.toEntity(dto);
        Comunidad guardada = repository.save(entidad);
        return mapper.toDto(guardada);
    }

    // Ajustamos 'actualizar' para usar UpdateComunidadDTO y el mapper para merge
    public ComunidadDto updateComunidad(Long idComunidad, UpdateComunidadDTO dto) { // <--- CORREGIDO: Usar ComunidadDto
        return repository.findById(idComunidad)
                .map(existing -> {
                    mapper.updateEntity(existing, dto);
                    Comunidad actualizada = repository.save(existing);
                    return mapper.toDto(actualizada);
                })
                .orElse(null);
    }

    // Método de soft delete (Asumo que lo tienes implementado)
    public void deleteComunidad(Long idComunidad) {
        Comunidad existingComunidad = repository.findById(idComunidad)
                .orElseThrow(() -> new RuntimeException("Comunidad no encontrada con id: " + idComunidad));

        if (existingComunidad.isActivo()) {
            existingComunidad.setActivo(false);
            repository.save(existingComunidad);
        }
    }
}