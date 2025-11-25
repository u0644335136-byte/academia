package com.jdk21.academia.features.convocatoria.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jdk21.academia.domain.Convocatoria;
import com.jdk21.academia.features.alumno.dto.AlumnoDto;
import com.jdk21.academia.features.alumno.mapper.AlumnoMapper;
import com.jdk21.academia.features.alumno.repository.AlumnoRepository;
import com.jdk21.academia.features.baseFeature.repository.BaseRepository;
import com.jdk21.academia.features.baseFeature.service.BaseService;
import com.jdk21.academia.features.convocatoria.dto.ConvocatoriaRequestDTO;
import com.jdk21.academia.features.convocatoria.dto.ConvocatoriaResponseDTO;
import com.jdk21.academia.features.convocatoria.mapper.ConvocatoriaMapper;
import com.jdk21.academia.features.convocatoria.repository.ConvocatoriaRepository;
import com.jdk21.academia.features.matricula.service.MatriculaService;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ConvocatoriaService extends BaseService<Convocatoria, ConvocatoriaRequestDTO, ConvocatoriaResponseDTO, Long> {

    @Autowired
    private MatriculaService matriculaService;
    
    @Autowired
    private AlumnoRepository alumnoRepository;
    
    @Autowired
    private AlumnoMapper alumnoMapper;
    
    private final ConvocatoriaRepository convocatoriaRepository;

    public ConvocatoriaService(BaseRepository<Convocatoria> repository, ConvocatoriaMapper mapper) {
        super(repository, mapper);
        // Necesitamos el repositorio específico para usar métodos personalizados
        this.convocatoriaRepository = (ConvocatoriaRepository) repository;
    }
    
    /**
     * ============================================
     * SOBRESCRITURA DEL MÉTODO getById
     * ============================================
     * Sobrescribimos el método del BaseService para cargar explícitamente
     * las relaciones (curso, profesor, centro) cuando se obtiene una convocatoria.
     * 
     * ¿POR QUÉ SOBRESCRIBIR?
     * - El BaseService.getById() usa repository.findById() que NO carga relaciones lazy
     * - Necesitamos las relaciones cargadas para que el mapper pueda acceder a:
     *   - curso.nombre → cursoNombre
     *   - centro.nombre → centroNombre
     *   - profesor.email → profesorEmail
     * 
     * ¿POR QUÉ NO CAMBIAR A EAGER?
     * - EAGER carga SIEMPRE las relaciones, incluso cuando no las necesitas
     * - Si solo necesitas el ID y código de la convocatoria, EAGER haría JOINs innecesarios
     * - Con este método, cargas las relaciones SOLO cuando las necesitas
     * - Mantiene el lazy loading por defecto (eficiente para otros casos)
     * ============================================
     */
    @Override
    public Optional<ConvocatoriaResponseDTO> getById(Long id) {
        // Usamos el método personalizado que carga las relaciones con @EntityGraph
        // Esto ejecuta una query SQL con JOINs para cargar curso, profesor y centro
        return convocatoriaRepository.findByIdWithRelations(id)
                .filter(Convocatoria::getActivo)  // Filtramos solo activas
                .map(mapper::toDto);  // Ahora el mapper puede acceder a curso.nombre, centro.nombre, profesor.email
    }
    
    /**
     * ============================================
     * MÉTODO PARA QUERIES ANIDADAS (NUEVA FUNCIONALIDAD)
     * ============================================
     * Obtiene todos los alumnos únicos que tienen matrícula en una convocatoria específica.
     * 
     * ¿POR QUÉ ES MEJOR QUE REST?
     * - En REST necesitarías múltiples peticiones:
     *   1. GET /convocatoria/123
     *   2. GET /matriculas?convocatoriaId=123
     *   3. Para cada matrícula: GET /alumnos/{id}
     * 
     * - Con GraphQL: Una sola query anidada:
     *   query {
     *     convocatoriaPorId(id: "123") {
     *       alumnos {  # ← Obtiene todos los alumnos directamente
     *         nombre
     *         apellidos
     *         email
     *       }
     *     }
     *   }
     * 
     * VENTAJAS:
     * 1. Una sola petición HTTP en lugar de N+1 peticiones
     * 2. El cliente decide qué campos del alumno necesita
     * 3. Menos latencia (especialmente importante en móviles)
     * 4. Menos ancho de banda (solo pides los campos que necesitas)
     * ============================================
     */
    public List<AlumnoDto> getAlumnosByConvocatoriaId(Long convocatoriaId) {
        // 1. Obtenemos todas las matrículas de esta convocatoria
        var matriculas = matriculaService.getMatriculasByConvocatoriaId(convocatoriaId);
        
        // 2. Extraemos los IDs únicos de alumnos (usando Set para evitar duplicados)
        Set<Long> alumnoIds = matriculas.stream()
                .map(matricula -> matricula.idAlumno())
                .filter(id -> id != null)
                .collect(Collectors.toSet());
        
        // 3. Obtenemos los alumnos y los convertimos a DTOs
        return alumnoIds.stream()
                .map(id -> alumnoRepository.findById(id))
                .filter(opt -> opt.isPresent())
                .map(opt -> alumnoMapper.toDTO(opt.get()))
                .collect(Collectors.toList());
    }
}
