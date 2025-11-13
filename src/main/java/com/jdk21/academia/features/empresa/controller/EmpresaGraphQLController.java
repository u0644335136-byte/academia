package com.jdk21.academia.features.empresa.controller;

import com.jdk21.academia.features.empresa.dto.EmpresaRequestDTO;
import com.jdk21.academia.features.empresa.dto.EmpresaResponseDTO;
import com.jdk21.academia.features.empresa.service.EmpresaService;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class EmpresaGraphQLController {

    private final EmpresaService empresaService;

    public EmpresaGraphQLController(EmpresaService empresaService) {
        this.empresaService = empresaService;
    }

    @QueryMapping
    public List<EmpresaResponseDTO> allEmpresas() {
        return empresaService.getAll();
    }

    @QueryMapping
    public EmpresaResponseDTO empresaById(@Argument Long id) {
        return empresaService.getById(id)
                .orElseThrow(() -> new RuntimeException("Empresa no encontrada con id " + id));
    }

    @MutationMapping
    public EmpresaResponseDTO createEmpresa(@Argument("input") EmpresaRequestDTO input) {
        return empresaService.create(input);
    }

    @MutationMapping
    public EmpresaResponseDTO updateEmpresa(@Argument Long id, @Argument("input") EmpresaRequestDTO input) {
        return empresaService.update(id, input);
    }

    @MutationMapping
    public Boolean deleteEmpresa(@Argument Long id) {
        empresaService.delete(id);
        return true;
    }
}
