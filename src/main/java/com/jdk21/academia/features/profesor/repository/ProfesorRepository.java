package com.jdk21.academia.features.profesor.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.jdk21.academia.domain.Profesor;

@Repository
public interface ProfesorRepository extends JpaRepository <Profesor,Long>{
    boolean existsByEmail(String email);
}
