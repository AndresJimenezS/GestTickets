package com.Proyecto.controller;

import org.springframework.ui.Model;
import com.Proyecto.domain.EstadoTicket;
import com.Proyecto.service.EstadoTicketService;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@Slf4j
@RequestMapping("/estadoTicket")
public class EstadoTicketController {

    @Autowired
    EstadoTicketService estadoTicketService;

    @GetMapping("/listado")
    public String inicio(Model model) {
        log.info("Consumiendo el recurso /estadoTicket/listado");
        List<EstadoTicket> estadoTickets = estadoTicketService.getEstadoTickets();

        model.addAttribute("estadoTickets", estadoTickets);
        return "/estadoTicket/listado";
    }
   

}