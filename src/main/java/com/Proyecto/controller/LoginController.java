
package com.Proyecto.controller;

import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@Slf4j
@RequestMapping("/login")
public class LoginController {
    @GetMapping("/")
    public String inicio(Model model) {
//        log.info("Consumiendo /categoria/listado");
//        List<Categoria> categorias=categoriaService.getCategorias(false);
//        model.addAttribute("attribute", "value");
//        model.addAttribute("categorias", categorias);
//        model.addAttribute("totalCategorias", categorias.size());
        return "login";
    }
    
}
