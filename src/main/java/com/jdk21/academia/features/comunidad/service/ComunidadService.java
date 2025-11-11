package com.jdk21.academia.features.comunidad.service;

import com.jdk21.academia.features.comunidad.dto.ComunidadDto;
import java.util.List;

public interface ComunidadService {
    List<ComunidadDto> listarTodas();
    ComunidadDto obtenerPorId(Long idComunidad);
    ComunidadDto guardar(ComunidadDto dto);
    ComunidadDto actualizar(Long idComunidad, ComunidadDto dto);
    void eliminar(Long idComunidad);
}

