package com.jdk21.academia.features.calificacion.service;

import com.jdk21.academia.domain.Calificacion;
import com.jdk21.academia.features.baseFeature.repository.BaseRepository;
import com.jdk21.academia.features.baseFeature.service.BaseService;
import com.jdk21.academia.features.calificacion.dto.CalificacionRequestDTO;
import com.jdk21.academia.features.calificacion.dto.CalificacionResponseDTO;
import com.jdk21.academia.features.calificacion.mapper.CalificacionMapper;

import org.springframework.stereotype.Service;

@Service
public class CalificacionService extends BaseService<Calificacion, CalificacionRequestDTO, CalificacionResponseDTO, Long> {

    public CalificacionService(BaseRepository<Calificacion> repository, CalificacionMapper mapper) {
        super(repository, mapper);
    }
}