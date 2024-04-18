package com.Proyecto.controller;

import org.springframework.ui.Model;
import com.Proyecto.domain.Prioridad;
import com.Proyecto.service.PrioridadService;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@Slf4j
@RequestMapping("/prioridad")
public class PrioridadController {

    @Autowired
    PrioridadService prioridadService;

    @GetMapping("/listado")
    public String inicio(Model model) {
        log.info("Consumiendo el recurso /prioridad/listado");
        List<Prioridad> prioridades = prioridadService.getPrioridades();

        model.addAttribute("prioridades", prioridades);
        return "/prioridad/listado";
    }
   

}