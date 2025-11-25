package com.jdk21.academia.features.matricula.service;

import com.jdk21.academia.domain.Matricula;
import com.jdk21.academia.features.baseFeature.repository.BaseRepository;
import com.jdk21.academia.features.baseFeature.service.BaseService;
import com.jdk21.academia.features.matricula.dto.MatriculaRequestDTO;
import com.jdk21.academia.features.matricula.dto.MatriculaResponseDTO;
import com.jdk21.academia.features.matricula.mapper.MatriculaMapper;

import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;
import com.jdk21.academia.features.matricula.repository.MatriculaRepository;

@Service
public class MatriculaService extends BaseService<Matricula, MatriculaRequestDTO, MatriculaResponseDTO, Long> {

    private final MatriculaRepository matriculaRepository;

    public MatriculaService(BaseRepository<Matricula> repository, MatriculaMapper mapper) {
        super(repository, mapper);
        // Necesitamos el repositorio específico para usar métodos personalizados
        this.matriculaRepository = (MatriculaRepository) repository;
    }


    public MatriculaResponseDTO actualizarCalificacion (Long id, int nota) {
        Matricula matricula = repository.findById((Long) id)
                .orElseThrow(() -> new RuntimeException("Matricula no encontrada"));

        matricula.setNota(nota);

        return mapper.toDto(repository.save(matricula));
    }

    /**
     * ============================================
     * MÉTODO PARA QUERIES ANIDADAS (NUEVA FUNCIONALIDAD)
     * ============================================
     * Este método permite obtener todas las matrículas de una convocatoria específica.
     * 
     * ¿POR QUÉ ES MEJOR QUE REST?
     * - En REST necesitarías: GET /matriculas?convocatoriaId=123
     * - Con GraphQL: convocatoriaPorId(id: "123") { matriculas { ... } }
     * - GraphQL resuelve esto automáticamente cuando el cliente pide el campo "matriculas"
     * 
     * VENTAJAS:
     * 1. El cliente decide qué necesita (puede pedir solo matrículas, o matrículas + alumnos)
     * 2. Una sola query en lugar de múltiples peticiones HTTP
     * 3. Menos latencia y ancho de banda
     * 4. Query optimizada en base de datos (no filtra en memoria)
     * ============================================
     */
    public List<MatriculaResponseDTO> getMatriculasByConvocatoriaId(Long convocatoriaId) {
        // Usamos el método del repositorio que genera una query SQL optimizada
        // Esto es MÁS EFICIENTE que cargar todas las matrículas y filtrar en memoria
        return matriculaRepository.findByConvocatoriaId(convocatoriaId).stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
    }

}