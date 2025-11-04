package com.jdk21.academia.features.empresa.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jdk21.academia.domain.Empresa;

public interface EmpresaRepository extends JpaRepository<Empresa, Long> {

}
