package com.jdk21.academia.features.matricula.repository;

import com.jdk21.academia.domain.Matricula;
import com.jdk21.academia.features.baseFeature.repository.BaseRepository;
import java.util.List;

public interface MatriculaRepository extends BaseRepository<Matricula> {
    
    /**
     * ============================================
     * MÉTODO OPTIMIZADO PARA QUERIES ANIDADAS
     * ============================================
     * Este método permite obtener matrículas por convocatoria de forma eficiente.
     * Spring Data JPA genera automáticamente la query SQL basándose en el nombre del método.
     * 
     * La query generada será algo como:
     *   SELECT * FROM matricula WHERE convocatoria_id = ?
     * 
     * Esto es MÁS EFICIENTE que filtrar en memoria (como en el servicio),
     * especialmente cuando hay muchas matrículas.
     * ============================================
     */
    List<Matricula> findByConvocatoriaId(Long convocatoriaId);

}
