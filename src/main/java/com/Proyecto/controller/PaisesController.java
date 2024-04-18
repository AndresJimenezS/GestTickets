package com.Proyecto.controller;

import com.Proyecto.domain.Usuario;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@Slf4j
@RequestMapping("/Paises")
public class PaisesController {

    @GetMapping("/listado")
    public String inicio(Model model) {
//        log.info("Consumiendo el recurso /usuario/listado");
//        List<Usuario> usuarios = usuarioService.getUsuarios();
//
//        model.addAttribute("usuarios", usuarios);
        return "/Admin/Paises";
    }

}
