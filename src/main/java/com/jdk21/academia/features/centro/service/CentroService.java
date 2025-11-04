package com.jdk21.academia.features.centro.service;

import java.util.List;
import org.springframework.stereotype.Service;
import com.jdk21.academia.domain.Centro;
import com.jdk21.academia.features.centro.repository.CentroRepository;
import com.jdk21.academia.features.empresa.repository.EmpresaRepository;
import jakarta.transaction.Transactional;

@Transactional
@Service
public class CentroService {

    private final CentroRepository centroRepository;
    private final EmpresaRepository empresaRepository; // Inyección añadida

    // Constructor con inyección de dependencias
    public CentroService(CentroRepository centroRepository, EmpresaRepository empresaRepository) {
        this.centroRepository = centroRepository;
        this.empresaRepository = empresaRepository;
    }

    // Listar un centro
    public List<Centro> listar() {
        return centroRepository.findAll();
    }

    // Crar centro
    public Centro creaCentro(Centro centro) {
        return centroRepository.save(centro);
    }




}
