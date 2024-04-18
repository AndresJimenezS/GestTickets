package com.Proyecto.controller;

import org.springframework.ui.Model;
import com.Proyecto.domain.Incidencia;
import com.Proyecto.service.IncidenciaService;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@Slf4j
@RequestMapping("/incidencia")
public class IncidenciaController {

    @Autowired
    IncidenciaService incidenciaService;

    @GetMapping("/listado")
    public String inicio(Model model) {
        log.info("Consumiendo el recurso /incidencia/listado");
        List<Incidencia> incidencias = incidenciaService.getIncidencias();

        model.addAttribute("incidencias", incidencias);
        return "/incidencia/listado";
    }
   

}