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
    public CentroDTO createCentro(CreateCentroDTO createcentroDTO) {
        log.info("Creando nuevo centro con código: {}", createcentroDTO.codigoCentro());

        // Validar unicidad de código de centro
        Optional<Centro> existingByCodigo = centroRepository.findByCodigoCentro(createcentroDTO.codigoCentro());
        if (existingByCodigo.isPresent()) {
            throw new IllegalArgumentException("Ya existe un centro con el código: " + createcentroDTO.codigoCentro());
        }

        // Convertir DTO a Entity
        Centro centro = centroMapper.toEntity(createcentroDTO);

        // Establecer valores por defecto
        if (centro.getActivo() == null) {
            centro.setActivo(true);
        }

        // Guardar el centro
        Centro centroGuardado = centroRepository.save(centro);
        log.info("Centro creado exitosamente con ID: {}", centroGuardado.getId());

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
    public CentroDTO updateCentro(Long id, UpdateCentroDTO updatecentroDTO) {
        log.info("Actualizando centro con ID: {}", id);

        Centro centro = centroRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("No se encontró el centro con ID: " + id));

        // Validar unicidad de código si se está cambiando
        if (!updatecentroDTO.codigoCentro().equals(centro.getCodigoCentro())) {
            Optional<Centro> existingByCodigo = centroRepository.findByCodigoCentro(updatecentroDTO.codigoCentro());
            if (existingByCodigo.isPresent()) {
                throw new IllegalArgumentException("Ya existe otro centro con el código: " + updatecentroDTO.codigoCentro());
            }
        }

        // Actualizar campos del DTO
        centroMapper.updateEntityFromDTO(updatecentroDTO, centro);

        Centro centroActualizado = centroRepository.save(centro);
        log.info("Centro actualizado exitosamente con ID: {}", id);

        return centroMapper.toDTO(centroActualizado);
    }
    // ========== MÉTODOS ADICIONALES ==========

    @Transactional(readOnly = true)
    public CentroDTO getCentroByCodigo(String codigoCentro) {
        log.info("Buscando centro por código: {}", codigoCentro);
        Centro centro = centroRepository.findByCodigoCentro(codigoCentro)
                .orElseThrow(
                        () -> new IllegalArgumentException("No se encontró el centro con código: " + codigoCentro));
        return centroMapper.toDTO(centro);
    }

    @Transactional(readOnly = true)
    public List<CentroDTO> getCentrosActivos() {
        log.info("Obteniendo centros activos");
        return centroRepository.findByActivoTrue().stream()
                .map(centroMapper::toDTO)
                .collect(Collectors.toList());
    }

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
