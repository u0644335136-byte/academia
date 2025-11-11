package com.jdk21.academia.features.empresa.dto;

public record EmpresaResponseDTO (
        Long id,
        String cif,
        String nombreLegal,
        String razonSocial,
        String representante
){}
