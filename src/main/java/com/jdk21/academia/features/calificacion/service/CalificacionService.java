package com.jdk21.academia.features.calificacion.service;

import com.jdk21.academia.domain.Calificacion;
import com.jdk21.academia.domain.Matricula;
import com.jdk21.academia.features.baseFeature.repository.BaseRepository;
import com.jdk21.academia.features.baseFeature.service.BaseService;
import com.jdk21.academia.features.calificacion.dto.CalificacionRequestDTO;
import com.jdk21.academia.features.calificacion.dto.CalificacionResponseDTO;
import com.jdk21.academia.features.calificacion.mapper.CalificacionMapper;
import com.jdk21.academia.features.matricula.repository.MatriculaRepository;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CalificacionService
        extends BaseService<Calificacion, CalificacionRequestDTO, CalificacionResponseDTO, Long> {

    private final BaseRepository<Calificacion> repository;
    private final CalificacionMapper mapper;
    private final MatriculaRepository matriculaRepository;

    public CalificacionService(BaseRepository<Calificacion> repository,
                               CalificacionMapper mapper,
                               MatriculaRepository matriculaRepository) {
        super(repository, mapper);
        this.repository = repository;
        this.mapper = mapper;
        this.matriculaRepository = matriculaRepository;
    }

    //Metodo de creacion para salvar la matrícula
    @Override
    public CalificacionResponseDTO create(CalificacionRequestDTO dto) {
        Calificacion entity = mapper.toEntity(dto);

        if (dto.idMatricula() != null) {
            Matricula matricula = matriculaRepository.findById(dto.idMatricula())
                    .orElseThrow(() -> new EntityNotFoundException("No existe matrícula con la id: " + dto.idMatricula()));
            entity.setMatricula(matricula);
        }

        Calificacion saved = repository.save(entity);
        return mapper.toDto(saved);
    }


    // Actualizar
    @Override
    public CalificacionResponseDTO update(Long id, CalificacionRequestDTO dto) {
        Calificacion existing = repository.findById((Long) id)
                .orElseThrow(() -> new RuntimeException("Entidad no encontrada"));


        if (dto.idMatricula() != null) {
            Matricula matricula = matriculaRepository.findById(dto.idMatricula())
                    .orElseThrow(() -> new EntityNotFoundException("No existe matrícula con la id: " + dto.idMatricula()));
            existing.setMatricula(matricula);
        }
        // Use MapStruct to update the existing entity in place
        mapper.updateEntityFromDto(dto, existing);



        return mapper.toDto(repository.save(existing));
    }


    //Metodo para actualizar matricula


}
