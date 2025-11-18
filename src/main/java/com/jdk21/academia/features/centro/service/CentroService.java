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

@Service
@RequiredArgsConstructor
@Slf4j
public class CentroService {

    private final CentroRepository centroRepository;
    private final CentroMapper centroMapper;

    // ==================
    // QUERIES
    // ==================

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

    @Transactional(readOnly = true)
    public CentroDTO getCentroByCodigo(String codigoCentro) {
        log.info("Buscando centro por código: {}", codigoCentro);
        Centro centro = centroRepository.findByCodigo_centro(codigoCentro) // ajusta el nombre si hace falta
                .orElseThrow(() -> new IllegalArgumentException(
                        "No se encontró el centro con código: " + codigoCentro));
        return centroMapper.toDTO(centro);
    }

    @Transactional(readOnly = true)
    public List<CentroDTO> getCentrosActivos() {
        log.info("Obteniendo centros activos");
        return centroRepository.findByActivoTrue().stream()
                .map(centroMapper::toDTO)
                .collect(Collectors.toList());
    }

    // ==================
    // MUTATIONS
    // ==================

    @Transactional
    public CentroDTO createCentro(CreateCentroDTO createCentroDTO) {
        log.info("Creando nuevo centro con código: {}", createCentroDTO.codigo_centro());
        
        // Validar que el código no exista
        Optional<Centro> existingCentro = centroRepository.findByCodigo_centro(createCentroDTO.codigo_centro());
        if (existingCentro.isPresent()) {
            throw new IllegalArgumentException(
                    "Ya existe un centro con el código: " + createCentroDTO.codigo_centro());
        }

        // Convertir DTO a Entity
        Centro centro = centroMapper.toEntity(createCentroDTO);

        // Establecer valores por defecto
        if (centro.getActivo() == null) {
            centro.setActivo(true);
        }
        
        // Establecer id_empresa si es necesario (ajusta según tu lógica de negocio)
        if (centro.getId_empresa() == null) {
            centro.setId_empresa(1L); // Valor por defecto, ajusta según necesites
        }

        // Guardar el centro
        Centro centroGuardado = centroRepository.save(centro);
        log.info("Centro creado exitosamente con ID: {}", centroGuardado.getId());

        return centroMapper.toDTO(centroGuardado);
    }

    @Transactional
    public CentroDTO updateCentro(Long id, UpdateCentroDTO updateCentroDTO) {
        log.info("Actualizando centro con ID: {}", id);

        Centro centro = centroRepository.findById(id.longValue())
                .orElseThrow(() -> new IllegalArgumentException("No se encontró el centro con ID: " + id));

        // Validar unicidad de código si se está cambiando
        if (updateCentroDTO.codigo_centro() != null &&
            !updateCentroDTO.codigo_centro().equals(centro.getCodigo_centro())) {
            Optional<Centro> existingByCodigo = centroRepository.findByCodigo_centro(updateCentroDTO.codigo_centro());
            if (existingByCodigo.isPresent()) {
                throw new IllegalArgumentException(
                        "Ya existe otro centro con el código: " + updateCentroDTO.codigo_centro());
            }
        }

        // Actualizar campos del DTO
        centroMapper.updateEntityFromDTO(updateCentroDTO, centro);

        Centro centroActualizado = centroRepository.save(centro);
        log.info("Centro actualizado exitosamente con ID: {}", id);

        return centroMapper.toDTO(centroActualizado);
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