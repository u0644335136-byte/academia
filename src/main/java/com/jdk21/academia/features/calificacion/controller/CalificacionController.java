package com.jdk21.academia.features.calificacion.controller;


import com.jdk21.academia.domain.Calificacion;
import com.jdk21.academia.features.baseFeature.controller.BaseController;
import com.jdk21.academia.features.calificacion.dto.CalificacionRequestDTO;
import com.jdk21.academia.features.calificacion.dto.CalificacionResponseDTO;
import com.jdk21.academia.features.calificacion.service.CalificacionService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;



@RestController
@RequestMapping("/api/Calificacions")
public class CalificacionController extends BaseController<Calificacion, CalificacionRequestDTO, CalificacionResponseDTO, Long> {

    public CalificacionController(CalificacionService service) {
        super(service);
    }
}