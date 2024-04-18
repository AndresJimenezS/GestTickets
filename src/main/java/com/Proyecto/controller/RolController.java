package com.Proyecto.controller;

import org.springframework.ui.Model;
import com.Proyecto.domain.Rol;
import com.Proyecto.service.RolService;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@Slf4j
@RequestMapping("/rol")
public class RolController {

    @Autowired
    RolService rolService;

    @GetMapping("/listado")
    public String inicio(Model model) {
        log.info("Consumiendo el recurso /rol/listado");
        List<Rol> roles = rolService.getRoles();

        model.addAttribute("roles", roles);
        return "/rol/listado";
    }
   

}