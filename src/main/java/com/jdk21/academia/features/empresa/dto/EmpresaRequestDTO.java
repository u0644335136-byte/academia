package com.jdk21.academia.features.empresa.dto;

public record EmpresaRequestDTO (
        String cif,
        String nombreLegal,
        String razonSocial,
        String representante) { 
}
