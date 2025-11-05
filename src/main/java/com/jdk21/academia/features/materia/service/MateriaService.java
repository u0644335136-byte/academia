package com.jdk21.academia.features.materia.service;

import com.jdk21.academia.domain.Materia;
import com.jdk21.academia.features.materia.dto.MateriaDto;
import com.jdk21.academia.features.materia.mapper.MateriaMapper;
import com.jdk21.academia.features.materia.repository.MateriaRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MateriaService {

    private final MateriaRepository materiaRepository;
    private final MateriaMapper materiaMapper = MateriaMapper.INSTANCE;

    public Materia crearMateria(MateriaDto dto) {
        Materia materia = materiaMapper.toEntity(dto);
        materia.setActivo(true);
        materia.setFechaCreacion(LocalDateTime.now());
        materia.setFechaActualizacion(LocalDateTime.now());
        return materiaRepository.save(materia);
    }

    public Optional<Materia> actualizarMateria(Long id, MateriaDto dto) {
        return materiaRepository.findById(id).map(materia -> {
            Materia actualizada = materiaMapper.toEntity(dto);
            actualizada.setIdMateria(materia.getIdMateria());
            actualizada.setFechaCreacion(materia.getFechaCreacion());
            actualizada.setFechaActualizacion(LocalDateTime.now());
            actualizada.setActivo(materia.getActivo());
            return materiaRepository.save(actualizada);
        });
    }

    public Optional<Materia> eliminarMateria(Long id) {
        return materiaRepository.findById(id).map(materia -> {
            materia.setActivo(false);
            materia.setFechaActualizacion(LocalDateTime.now());
            return materiaRepository.save(materia);
        });
    }
}
