package com.jdk21.academia.features.materia.service;

import com.jdk21.academia.domain.Materia;
import com.jdk21.academia.features.materia.dto.MateriaDto;
import com.jdk21.academia.features.materia.mapper.MateriaMapper;
import com.jdk21.academia.features.materia.repository.MateriaRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MateriaService {

    private final MateriaRepository materiaRepository;
    private final MateriaMapper materiaMapper = MateriaMapper.INSTANCE;

    public MateriaDto crearMateria(MateriaDto dto) {
        Materia materia = materiaMapper.toEntity(dto);
        materia.setActivo(true);
        materia.setFechaCreacion(LocalDateTime.now());
        materia.setFechaActualizacion(LocalDateTime.now());
        Materia saved = materiaRepository.save(materia);
        return materiaMapper.toDto(saved);
    }

    public Optional<MateriaDto> actualizarMateria(Long id, MateriaDto dto) {
        return materiaRepository.findById(id).map(materia -> {
            Materia actualizada = materiaMapper.toEntity(dto);
            actualizada.setIdMateria(materia.getIdMateria());
            actualizada.setFechaCreacion(materia.getFechaCreacion());
            actualizada.setFechaActualizacion(LocalDateTime.now());
            actualizada.setActivo(materia.getActivo());
            Materia saved = materiaRepository.save(actualizada);
            return materiaMapper.toDto(saved);
        });
    }

    public Optional<MateriaDto> eliminarMateria(Long id) {
        return materiaRepository.findById(id).map(materia -> {
            materia.setActivo(false);
            materia.setFechaActualizacion(LocalDateTime.now());
            Materia saved = materiaRepository.save(materia);
            return materiaMapper.toDto(saved);
        });
    }

    public List<MateriaDto> obtenerTodas() {
        return materiaRepository.findAll()
                .stream()
                .map(materiaMapper::toDto)
                .collect(Collectors.toList());
    }

    public Optional<MateriaDto> obtenerPorId(Long id) {
        return materiaRepository.findById(id)
                .map(materiaMapper::toDto);
    }
}
