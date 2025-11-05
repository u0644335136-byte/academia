package com.jdk21.academia.features.curso.service;

import com.jdk21.academia.domain.Curso;
import com.jdk21.academia.features.curso.repository.CursoRepository;
import com.jdk21.academia.features.curso.dto.CursoDto;
import com.jdk21.academia.features.curso.mapper.CursoMapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CursoService {

    private final CursoRepository cursoRepository;
    private final CursoMapper cursoMapper = CursoMapper.INSTANCE;

    public Curso crearCurso(CursoDto dto) {
        Curso curso = cursoMapper.toEntity(dto);
        curso.setActivo(true);
        curso.setFechaCreacion(LocalDateTime.now());
        curso.setFechaActualizacion(LocalDateTime.now());
        return cursoRepository.save(curso);
    }

    public Optional<Curso> actualizarCurso(Long id, CursoDto dto) {
        return cursoRepository.findById(id).map(curso -> {
            Curso actualizado = cursoMapper.toEntity(dto);
            actualizado.setIdCurso(curso.getIdCurso());
            actualizado.setFechaCreacion(curso.getFechaCreacion());
            actualizado.setFechaActualizacion(LocalDateTime.now());
            actualizado.setActivo(curso.getActivo());
            return cursoRepository.save(actualizado);
        });
    }

    public Optional<Curso> eliminarCurso(Long id) {
        return cursoRepository.findById(id).map(curso -> {
            curso.setActivo(false);
            curso.setFechaActualizacion(LocalDateTime.now());
            return cursoRepository.save(curso);
        });
    }
}
