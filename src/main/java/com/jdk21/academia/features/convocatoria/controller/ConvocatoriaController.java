package com.jdk21.academia.features.convocatoria.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jdk21.academia.domain.Convocatoria;
import com.jdk21.academia.features.baseFeature.controller.BaseController;
import com.jdk21.academia.features.convocatoria.dto.ConvocatoriaRequestDTO;
import com.jdk21.academia.features.convocatoria.dto.ConvocatoriaResponseDTO;
import com.jdk21.academia.features.convocatoria.service.ConvocatoriaService;

@RestController
@RequestMapping("/api/convocatoria")
public class ConvocatoriaController  extends BaseController<Convocatoria, ConvocatoriaRequestDTO, ConvocatoriaResponseDTO, Long> {

    public ConvocatoriaController(ConvocatoriaService service) {
        super(service);
    }

}
