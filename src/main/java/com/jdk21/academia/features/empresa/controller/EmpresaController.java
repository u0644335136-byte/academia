package com.jdk21.academia.features.empresa.controller;

import com.jdk21.academia.domain.Empresa;
import com.jdk21.academia.features.baseFeature.controller.BaseController;
import com.jdk21.academia.features.empresa.dto.EmpresaRequestDTO;
import com.jdk21.academia.features.empresa.dto.EmpresaResponseDTO;
import com.jdk21.academia.features.empresa.service.EmpresaService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/empresas")
public class EmpresaController extends BaseController<Empresa, EmpresaRequestDTO, EmpresaResponseDTO, Long> {

    public EmpresaController(EmpresaService service) {
        super(service);
    }
}
