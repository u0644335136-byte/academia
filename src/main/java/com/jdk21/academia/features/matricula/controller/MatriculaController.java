package com.jdk21.academia.features.matricula.controller;

import com.jdk21.academia.domain.Matricula;
import com.jdk21.academia.features.baseFeature.controller.BaseController;
import com.jdk21.academia.features.matricula.dto.MatriculaRequestDTO;
import com.jdk21.academia.features.matricula.dto.MatriculaResponseDTO;
import com.jdk21.academia.features.matricula.service.MatriculaService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;



@RestController
@RequestMapping("/api/matriculas")
public class MatriculaController extends BaseController<Matricula, MatriculaRequestDTO, MatriculaResponseDTO, Long> {

    public MatriculaController(MatriculaService service) {
        super(service);
    }
}