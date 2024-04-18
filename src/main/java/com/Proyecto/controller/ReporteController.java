package com.Proyecto.controller;

import com.Proyecto.service.ReporteService;
import java.io.IOException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/reportes")
public class ReporteController {

    @Autowired
    ReporteService reporteService;

    @GetMapping("/historicoTickets")
    public ResponseEntity<Resource> reporteHistoricoTickets(@RequestParam String tipo)
            throws IOException {
        var reporte = "historicoTickets";
        return reporteService.generaReporte(reporte, null, tipo);
    }
}
