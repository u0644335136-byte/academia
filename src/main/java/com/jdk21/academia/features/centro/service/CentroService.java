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
}

/*
    // ==================
    // MUTATIONS
    // ==================

    @Transactional
    public CentroDTO createCentro(CreateCentroDTO createCentroDTO) {
        try {
            log.info("Creando nuevo centro con código: {}", createCentroDTO.codigo_centro());
            // validar que el codigo no exista
            if (centroRepository.findByCodigo(createCentroDTO.codigo_centro()).isPresent()) {
                throw new IllegalArgumentException(
                        "Ya existe un centro con el código: " + createCentroDTO.codigo_centro());
            }

            // Validar unicidad de código de centro
            Optional<Centro> existingByCodigo = centroRepository.findByCodigo(createCentroDTO.codigo_centro());
            if (existingByCodigo.isPresent()) {
                throw new IllegalArgumentException(
                        "Ya existe un centro con el código: " + createCentroDTO.codigo_centro());
            }
        
            // Convertir DTO a Entity
            Centro centro = centroMapper.toEntity(createCentroDTO);

            // Establecer valores por defecto
            if (centro.getActivo() == null) {
                centro.setActivo(true);
            }

            // Guardar el centro
            Centro centroGuardado = centroRepository.save(centro);
            log.info("Centro creado exitosamente con ID: {}", centroGuardado.getId());

            return centroMapper.toDTO(centroGuardado);
        } catch (Exception e) {
            log.error("Error al crear centro: {}", e.getMessage(), e);
            throw new RuntimeException("Error interno al crear centro: " + e.getMessage());

        }
    }

@Transactional
    public CentroDTO updateCentro(Long id, UpdateCentroDTO updatecentroDTO) {
        log.info("Actualizando centro con ID: {}", id);

        Centro centro = centroRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("No se encontró el centro con ID: " + id));

        // Validar unicidad de código si se está cambiando
        if (!updatecentroDTO.codigo_centro().equals(centro.getCodigo_centro())) {
            Optional<Centro> existingByCodigo = centroRepository.findByCodigo(updatecentroDTO.codigo_centro());
            if (existingByCodigo.isPresent()) {
                throw new IllegalArgumentException(
                        "Ya existe otro centro con el código: " + updatecentroDTO.codigo_centro());
            }
        }

    @Transactional
    public CentroDTO desactivarCentro(Long id) {
        log.info("Desactivando centro con ID: {}", id);

        Centro centro = centroRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("No se encontró el centro con ID: " + id));

        centro.setActivo(false);
        Centro desactivado = centroRepository.save(centro);

        log.info("Centro desactivado exitosamente con ID: {}", id);
        return centroMapper.toDTO(desactivado);
    }
}

*/




/*
 * package com.jdk21.academia.features.centro.service;
 * 
 * import java.util.List;
 * import java.util.Optional;
 * import java.util.stream.Collectors;
 * import org.springframework.stereotype.Service;
 * import org.springframework.transaction.annotation.Transactional;
 * import com.jdk21.academia.domain.Centro;
 * import com.jdk21.academia.features.centro.dto.CentroDTO;
 * import com.jdk21.academia.features.centro.dto.CreateCentroDTO;
 * import com.jdk21.academia.features.centro.dto.UpdateCentroDTO;
 * import com.jdk21.academia.features.centro.mapper.CentroMapper;
 * import com.jdk21.academia.features.centro.repository.CentroRepository;
 * import lombok.RequiredArgsConstructor;
 * import lombok.extern.slf4j.Slf4j;
 * 
 * @Slf4j
 * 
 * @Service
 * 
 * @RequiredArgsConstructor
 * public class CentroService {
 * 
 * private final CentroRepository centroRepository;
 * private final CentroMapper centroMapper;
 * 
 * @Transactional
 * public CentroDTO createCentro(CreateCentroDTO createCentroDTO) {
 * try {
 * log.info("Creando nuevo centro con código: {}",
 * createCentroDTO.codigo_centro());
 * //validar que el codigo no exista
 * if
 * (centroRepository.findByCodigo(createCentroDTO.codigo_centro()).isPresent())
 * {
 * throw new IllegalArgumentException("Ya existe un centro con el código: " +
 * createCentroDTO.codigo_centro());
 * }
 * 
 * // Validar unicidad de código de centro
 * Optional<Centro> existingByCodigo =
 * centroRepository.findByCodigo(createCentroDTO.codigo_centro());
 * if (existingByCodigo.isPresent()) {
 * throw new IllegalArgumentException("Ya existe un centro con el código: " +
 * createCentroDTO.codigo_centro());
 * }
 * 
 * // Convertir DTO a Entity
 * Centro centro = centroMapper.toEntity(createCentroDTO);
 * 
 * // Establecer valores por defecto
 * if (centro.getActivo() == null) {
 * centro.setActivo(true);
 * }
 * 
 * // Guardar el centro
 * Centro centroGuardado = centroRepository.save(centro);
 * log.info("Centro creado exitosamente con ID: {}", centroGuardado.getId());
 * 
 * return centroMapper.toDTO(centroGuardado);
 * } catch (Exception e) {
 * log.error("Error al crear centro: {}", e.getMessage(), e);
 * throw new RuntimeException("Error interno al crear centro: " +
 * e.getMessage());
 * 
 * }
 * }
 * 
 * @Transactional(readOnly = true)
 * public List<CentroDTO> getAllCentros() {
 * log.info("Obteniendo todos los centros");
 * return centroRepository.findAll().stream()
 * .map(centroMapper::toDTO)
 * .collect(Collectors.toList());
 * }
 * 
 * @Transactional(readOnly = true)
 * public CentroDTO getCentroById(Long id) {
 * log.info("Buscando centro por ID: {}", id);
 * Centro centro = centroRepository.findById(id)
 * .orElseThrow(() -> new
 * IllegalArgumentException("No se encontró el centro con ID: " + id));
 * return centroMapper.toDTO(centro);
 * }
 * 
 * @Transactional
 * public CentroDTO updateCentro(Long id, UpdateCentroDTO updatecentroDTO) {
 * log.info("Actualizando centro con ID: {}", id);
 * 
 * Centro centro = centroRepository.findById(id)
 * .orElseThrow(() -> new
 * IllegalArgumentException("No se encontró el centro con ID: " + id));
 * 
 * // Validar unicidad de código si se está cambiando
 * if (!updatecentroDTO.codigo_centro().equals(centro.getCodigo_centro())) {
 * Optional<Centro> existingByCodigo =
 * centroRepository.findByCodigo(updatecentroDTO.codigo_centro());
 * if (existingByCodigo.isPresent()) {
 * throw new IllegalArgumentException(
 * "Ya existe otro centro con el código: " + updatecentroDTO.codigo_centro());
 * }
 * }
 * 
 * // Actualizar campos del DTO
 * centroMapper.updateEntityFromDTO(updatecentroDTO, centro);
 * 
 * Centro centroActualizado = centroRepository.save(centro);
 * log.info("Centro actualizado exitosamente con ID: {}", id);
 * 
 * return centroMapper.toDTO(centroActualizado);
 * }
 * // ========== MÉTODOS ADICIONALES ==========
 * 
 * @Transactional(readOnly = true)
 * public CentroDTO getCentroByCodigo(String codigo_centro) {
 * log.info("Buscando centro por código: {}", codigo_centro);
 * Centro centro = centroRepository.findByCodigo(codigo_centro)
 * .orElseThrow(
 * () -> new IllegalArgumentException("No se encontró el centro con código: " +
 * codigo_centro));
 * return centroMapper.toDTO(centro);
 * }
 * 
 * @Transactional(readOnly = true)
 * public List<CentroDTO> getCentrosActivos() {
 * log.info("Obteniendo centros activos");
 * return centroRepository.findByActivoTrue().stream()
 * .map(centroMapper::toDTO)
 * .collect(Collectors.toList());
 * }
 * 
 * @Transactional(readOnly = true)
 * public boolean existsByCodigo_centro(String codigo_centro) {
 * return centroRepository.existsByCodigo_centro(codigo_centro);
 * }
 * 
 * @Transactional
 * public CentroDTO desactivarCentro(Long id) {
 * log.info("Desactivando centro con ID: {}", id);
 * 
 * Centro centro = centroRepository.findById(id)
 * .orElseThrow(() -> new
 * IllegalArgumentException("No se encontró el centro con ID: " + id));
 * 
 * centro.setActivo(false);
 * Centro centroDesactivado = centroRepository.save(centro);
 * 
 * log.info("Centro desactivado exitosamente con ID: {}", id);
 * return centroMapper.toDTO(centroDesactivado);
 * }
 * }
 */