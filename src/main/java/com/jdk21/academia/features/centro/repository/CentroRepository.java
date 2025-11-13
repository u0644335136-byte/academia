package com.jdk21.academia.features.centro.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.jdk21.academia.domain.Centro;
import com.jdk21.academia.features.baseFeature.repository.BaseRepository;

public interface CentroRepository extends JpaRepository<Centro, Long> {

Optional<Centro> findByCodigo_centro(String codigo_centro);

    List<Centro> findByActivoTrue();
    /*

    @Query("SELECT c FROM Centro c WHERE c.codigo_centro = :codigo")
    Optional<Centro> findByCodigo(@Param("codigo") String codigo_centro);

    @Query("SELECT CASE WHEN COUNT(c) > 0 THEN true ELSE false END FROM Centro c WHERE c.codigo_centro = :codigo")
    boolean existsByCodigo_centro(@Param("codigo") String codigo_centro);

     */

}
