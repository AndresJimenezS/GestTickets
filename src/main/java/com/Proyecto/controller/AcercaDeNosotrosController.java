package com.Proyecto.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/nosotros")
public class AcercaDeNosotrosController {
    @GetMapping("/acercaDeNosotros")
    public String acercaDeNosotros() {
        return "Admin/AcercadeNosotros";
    }
}
