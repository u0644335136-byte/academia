package com.jdk21.academia.features.convocatoria.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.jdk21.academia.domain.Convocatoria;
import com.jdk21.academia.features.baseFeature.repository.BaseRepository;

import java.util.Optional;

@Repository
public interface ConvocatoriaRepository extends BaseRepository<Convocatoria> {
    
    /**
     * ============================================
     * MÉTODO OPTIMIZADO: Carga explícita de relaciones
     * ============================================
     * Este método usa @EntityGraph para cargar las relaciones lazy (curso, profesor, centro)
     * en una sola query SQL con JOINs, evitando el problema N+1.
     * 
     * ¿POR QUÉ ES MEJOR QUE EAGER?
     * - EAGER carga SIEMPRE las relaciones, incluso cuando no las necesitas
     * - Este método carga las relaciones SOLO cuando las necesitas explícitamente
     * - Mantiene el lazy loading por defecto (eficiente)
     * - Carga explícita solo cuando es necesario (flexible)
     * 
     * SQL generado (aproximado):
     *   SELECT c.*, curso.*, profesor.*, centro.*
     *   FROM convocatoria c
     *   LEFT JOIN curso ON c.id_curso = curso.id_curso
     *   LEFT JOIN profesor ON c.id_profesor = profesor.id_profesor
     *   LEFT JOIN centro ON c.id_centro = centro.id_centro
     *   WHERE c.id_convocatoria = ?
     * ============================================
     */
    @EntityGraph(attributePaths = {"curso", "profesor", "centro"})
    @Query("SELECT c FROM Convocatoria c WHERE c.id = :id")
    Optional<Convocatoria> findByIdWithRelations(@Param("id") Long id);
    
    /**
     * Método alternativo usando JOIN FETCH (mismo resultado)
     * Puedes usar cualquiera de los dos, ambos cargan las relaciones
     */
    @Query("SELECT c FROM Convocatoria c " +
           "LEFT JOIN FETCH c.curso " +
           "LEFT JOIN FETCH c.profesor " +
           "LEFT JOIN FETCH c.centro " +
           "WHERE c.id = :id")
    Optional<Convocatoria> findByIdWithRelationsJoinFetch(@Param("id") Long id);
}
