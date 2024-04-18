package com.Proyecto.controller;

import org.springframework.ui.Model;
import com.Proyecto.domain.Canton;
import com.Proyecto.domain.Provincia;
import com.Proyecto.service.CantonService;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@Slf4j
@RequestMapping("/canton")
public class CantonController {

    @Autowired
    CantonService cantonService;

    @GetMapping("/listado")
    public String inicio(Model model) {
        log.info("Consumiendo el recurso /canton/listado");
        List<Canton> cantones = cantonService.getCantones();

        model.addAttribute("cantones", cantones);
        return "Admin/Canton";
    }
   
    @PostMapping("/guardar")
    public String categoriaGuardar(Canton canton) {

        cantonService.save(canton);
        return "redirect:/canton/listado";
    }

    @GetMapping("/eliminar/{canton}")
    public String categoriaEliminar(Canton canton) {
        cantonService.delete(canton);
        return "redirect:/canton/listado";
    }

}