package com.jdk21.academia.features.curso.service;

import com.jdk21.academia.domain.Curso;
import com.jdk21.academia.features.curso.dto.CursoDto;
import com.jdk21.academia.features.curso.mapper.CursoMapper;
import com.jdk21.academia.features.curso.repository.CursoRepository;
import com.jdk21.academia.features.materia.repository.MateriaRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CursoService {

    private final CursoRepository cursoRepository;
    private final MateriaRepository materiaRepository;
    private final CursoMapper cursoMapper = CursoMapper.INSTANCE;

    public CursoDto crearCurso(CursoDto dto) {
        // Validar que idMateria no sea null
        if (dto.getIdMateria() == null) {
            throw new IllegalArgumentException("El ID de la materia es obligatorio");
        }
        // Validar que la Materia existe
        if (!materiaRepository.existsById(dto.getIdMateria())) {
            throw new IllegalArgumentException("La materia con ID " + dto.getIdMateria() + " no existe.");
        }
        Curso curso = cursoMapper.toEntity(dto);
        // Ignorar idCurso al crear (se genera automáticamente)
        curso.setIdCurso(null);
        curso.setActivo(true);
        curso.setFechaCreacion(LocalDateTime.now());
        curso.setFechaActualizacion(LocalDateTime.now());
        
        Curso guardado = cursoRepository.save(curso);
        return cursoMapper.toDto(guardado);
    }

    public Optional<CursoDto> actualizarCurso(Long id, CursoDto dto) {
        return cursoRepository.findById(id).map(curso -> {
            Curso actualizado = cursoMapper.toEntity(dto);
            actualizado.setIdCurso(curso.getIdCurso());
            actualizado.setFechaActualizacion(LocalDateTime.now());
            actualizado.setActivo(curso.getActivo());
            Curso guardado = cursoRepository.save(actualizado);
            return cursoMapper.toDto(guardado);
        });
    }

    public Optional<CursoDto> eliminarCurso(Long id) {
        return cursoRepository.findById(id).map(curso -> {
            curso.setActivo(false);
            curso.setFechaActualizacion(LocalDateTime.now());
            Curso guardado = cursoRepository.save(curso);
            return cursoMapper.toDto(guardado);
        });
    }

    public List<CursoDto> obtenerTodos() {
        return cursoRepository.findAll()
                .stream()
                .map(cursoMapper::toDto)
                .collect(Collectors.toList());
    }

    public Optional<CursoDto> obtenerPorId(Long id) {
        return cursoRepository.findById(id)
                .map(cursoMapper::toDto);
    }
}
