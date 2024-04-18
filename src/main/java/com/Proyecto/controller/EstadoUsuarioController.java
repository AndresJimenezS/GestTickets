package com.Proyecto.controller;

import org.springframework.ui.Model;
import com.Proyecto.domain.EstadoUsuario;
import com.Proyecto.service.EstadoUsuarioService;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@Slf4j
@RequestMapping("/estadoUsuario")
public class EstadoUsuarioController {

    @Autowired
    EstadoUsuarioService estadoUsuarioService;

    @GetMapping("/listado")
    public String inicio(Model model) {
        log.info("Consumiendo el recurso /estadoUsuario/listado");
        List<EstadoUsuario> estadoUsuarios = estadoUsuarioService.getEstadoUsuarios();

        model.addAttribute("estadoUsuarios", estadoUsuarios);
        return "/estadoUsuario/listado";
    }
   

}