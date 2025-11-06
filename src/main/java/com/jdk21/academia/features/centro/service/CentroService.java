package com.jdk21.academia.features.centro.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.jdk21.academia.domain.Centro;
import com.jdk21.academia.features.centro.dto.CentroDTO;
import com.jdk21.academia.features.centro.dto.CreateCentroDTO;
import com.jdk21.academia.features.centro.dto.UpdateCentroDTO;
import com.jdk21.academia.features.centro.mapper.CentroMapper;
import com.jdk21.academia.features.centro.repository.CentroRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class CentroService {

    private final CentroRepository centroRepository;
    private final CentroMapper centroMapper;

    @Transactional
    public CentroDTO createCentro(CreateCentroDTO createDTO) {
        log.info("Creando nuevo centro con código: {}", createDTO.codigoCentro());
        
        // Validar unicidad de código de centro
        Optional<Centro> existingByCodigo = centroRepository.findByCodigoCentro(createDTO.codigoCentro());
        if (existingByCodigo.isPresent()) {
            throw new IllegalArgumentException("Ya existe un centro con el código: " + createDTO.codigoCentro());
        }
        
        // Convertir DTO a Entity
        Centro centro = centroMapper.toEntity(createDTO);
        /*
        // Asignar empresa
        Empresa empresa = empresaRepository.findById(createDTO.empresaId())
            .orElseThrow(() -> new IllegalArgumentException("No se encontró la empresa con ID: " + createDTO.empresaId()));
        centro.setEmpresa(empresa);
        */

        // Establecer valores por defecto
        if (centro.getActivo() == null) {
            centro.setActivo(true);
        }
        
        // Guardar el centro
        Centro centroGuardado = centroRepository.save(centro);
        log.info("Centro creado exitosamente con ID: {}", centroGuardado.getIdCentro());
        
        return centroMapper.toDTO(centroGuardado);
    }

    @Transactional(readOnly = true)
    public List<CentroDTO> getAllCentros() {
        log.info("Obteniendo todos los centros");
        return centroRepository.findAll().stream()
                .map(centroMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public CentroDTO getCentroById(Long id) {
        log.info("Buscando centro por ID: {}", id);
        Centro centro = centroRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("No se encontró el centro con ID: " + id));
        return centroMapper.toDTO(centro);
    }

    @Transactional
    public CentroDTO updateCentro(Long id, UpdateCentroDTO updateDTO) {
        log.info("Actualizando centro con ID: {}", id);
        
        Centro centro = centroRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("No se encontró el centro con ID: " + id));
        
        // Validar unicidad de código si se está cambiando
        if (!updateDTO.codigoCentro().equals(centro.getCodigo_centro())) {
            Optional<Centro> existingByCodigo = centroRepository.findByCodigoCentro(updateDTO.codigoCentro());
            if (existingByCodigo.isPresent()) {
                throw new IllegalArgumentException("Ya existe otro centro con el código: " + updateDTO.codigoCentro());
            }
        }
        
        // Actualizar campos del DTO
        centroMapper.updateEntityFromDTO(updateDTO, centro);
        
        Centro centroActualizado = centroRepository.save(centro);
        log.info("Centro actualizado exitosamente con ID: {}", id);
        
        return centroMapper.toDTO(centroActualizado);
    }

    @Transactional
    public void deleteCentro(Long id) {
        log.info("Eliminando centro con ID: {}", id);
        
        if (!centroRepository.existsById(id)) {
            throw new IllegalArgumentException("No se encontró el centro con ID: " + id);
        }
        
        // Soft delete - desactivar en lugar de eliminar
        Centro centro = centroRepository.findById(id).get();
        centro.setActivo(false);
        centroRepository.save(centro);
        
        log.info("Centro desactivado exitosamente con ID: {}", id);
    }

    // ========== MÉTODOS ADICIONALES ==========

    @Transactional(readOnly = true)
    public CentroDTO getCentroByCodigo(String codigoCentro) {
        log.info("Buscando centro por código: {}", codigoCentro);
        Centro centro = centroRepository.findByCodigoCentro(codigoCentro)
            .orElseThrow(() -> new IllegalArgumentException("No se encontró el centro con código: " + codigoCentro));
        return centroMapper.toDTO(centro);
    }

    @Transactional(readOnly = true)
    public List<CentroDTO> getCentrosActivos() {
        log.info("Obteniendo centros activos");
        return centroRepository.findByActivoTrue().stream()
            .map(centroMapper::toDTO)
            .collect(Collectors.toList());
    }
/* 
    @Transactional(readOnly = true)
    public List<CentroDTO> getCentrosByEmpresa(Long idEmpresa) {
        log.info("Obteniendo centros por empresa ID: {}", idEmpresa);
        
        if (!empresaRepository.existsById(idEmpresa)) {
            throw new IllegalArgumentException("No se encontró la empresa con ID: " + idEmpresa);
        }
        
        return centroRepository.findById(idEmpresa).stream()
            .map(centroMapper::toDTO)
            .collect(Collectors.toList());
    }
*/
    @Transactional(readOnly = true)
    public boolean existsByCodigoCentro(String codigoCentro) {
        return centroRepository.existsByCodigoCentro(codigoCentro);
    }
    
    @Transactional
    public CentroDTO desactivarCentro(Long id) {
        log.info("Desactivando centro con ID: {}", id);
        
        Centro centro = centroRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("No se encontró el centro con ID: " + id));
        
        centro.setActivo(false);
        Centro centroDesactivado = centroRepository.save(centro);
        
        log.info("Centro desactivado exitosamente con ID: {}", id);
        return centroMapper.toDTO(centroDesactivado);
    }
}
