package com.jdk21.academia.features.convocatoria.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jdk21.academia.domain.Matricula;

public interface MatriculaRepository extends JpaRepository<Matricula, Long> {
    

}
