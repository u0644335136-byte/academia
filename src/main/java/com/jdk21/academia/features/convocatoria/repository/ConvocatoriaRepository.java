package com.jdk21.academia.features.convocatoria.repository;

import org.springframework.stereotype.Repository;

import com.jdk21.academia.domain.Convocatoria;

import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface ConvocatoriaRepository extends JpaRepository<Convocatoria, Long> {
}
