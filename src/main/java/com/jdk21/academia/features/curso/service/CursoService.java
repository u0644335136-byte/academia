package com.jdk21.academia.features.curso.service;

import com.jdk21.academia.domain.Curso; 
import com.jdk21.academia.features.curso.repository.CursoRepository;
import com.jdk21.academia.features.curso.dto.CursoDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CursoService {

    private final CursoRepository cursoRepository;

    public Curso crearCurso(CursoDto dto) {
        Curso curso = Curso.builder()
                .idMateria(dto.getIdMateria())
                .idFormato(dto.getIdFormato())
                .nombre(dto.getNombre())
                .descripcion(dto.getDescripcion())
                .duracionHoras(dto.getDuracionHoras())
                .activo(true)
                .fechaCreacion(LocalDateTime.now())
                .fechaActualizacion(LocalDateTime.now())
                .build();
        return cursoRepository.save(curso);
    }

    public Optional<Curso> actualizarCurso(Long id, CursoDto dto) {
        return cursoRepository.findById(id).map(curso -> {
            curso.setNombre(dto.getNombre());
            curso.setDescripcion(dto.getDescripcion());
            curso.setIdMateria(dto.getIdMateria());
            curso.setIdFormato(dto.getIdFormato());
            curso.setDuracionHoras(dto.getDuracionHoras());
            curso.setFechaActualizacion(LocalDateTime.now());
            return cursoRepository.save(curso);
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
