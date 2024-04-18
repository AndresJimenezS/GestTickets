package com.Proyecto.controller;

import com.Proyecto.domain.EstadoTicket;
import com.Proyecto.domain.Incidencia;
import com.Proyecto.domain.Prioridad;
import org.springframework.ui.Model;
import com.Proyecto.domain.Ticket;
import com.Proyecto.domain.Usuario;
import com.Proyecto.service.EstadoTicketService;
import com.Proyecto.service.IncidenciaService;
import com.Proyecto.service.PrioridadService;
import com.Proyecto.service.TicketService;
import com.Proyecto.service.UsuarioService;
import java.time.LocalDateTime;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@Slf4j
@RequestMapping("/ticket")
public class TicketController {

    @Autowired
    TicketService ticketService;
    
    @Autowired
    UsuarioService usuarioService;
    
    @Autowired
    EstadoTicketService estadoTicketService;
    
    @Autowired
    IncidenciaService incidenciaService;
    
    @Autowired
    PrioridadService prioridadService;

    @GetMapping("/listado")
    public String inicio(Model model) {
        log.info("Consumiendo el recurso /ticket/listado");
        List<Ticket> tickets = ticketService.getTickets();
//        var tickets = ticketService.getTickets();
        model.addAttribute("tickets", tickets);
        return "UsuarioTickets/historicoTickets";
//        return "UsuarioTickets/ticketsRegistradosUsuario";
    }
    
    
    @GetMapping("/nuevo")
    public String solicitudNueva(Ticket ticket, Model model) {
        List<Incidencia> incidencias = incidenciaService.getIncidencias();
        List<EstadoTicket> estadoTickets = estadoTicketService.getEstadoTickets();
        List<Prioridad> prioridades = prioridadService.getPrioridades();
        List<Usuario> usuarios = usuarioService.getUsuarios();
        model.addAttribute("usuarios", usuarios);
        model.addAttribute("incidencias", incidencias);
        model.addAttribute("estadoTickets", estadoTickets);
        model.addAttribute("prioridades", prioridades);
        model.addAttribute("tecnicos", usuarios);
//        model.addAttribute("fechaRegistraTecnico", fecha);
        return "UsuarioTickets/CrearSolicitud";
    }
    
    @PostMapping("/guardar")
    public String ticketGuardar(Ticket ticket) {
        ticketService.save(ticket);
        return "redirect:/";
    }
    
    @PostMapping("/guardarSP")
    public String insertarTicket(
        @RequestParam("usuario.idUsuario") Long idUsuario,
        @RequestParam("incidencia.incidencia") Long tipoIncidencia,
        @RequestParam("titulo") String titulo,
        @RequestParam("descripcion") String descripcion,
        @RequestParam("comentariosUsuario") String comentariosUsuario,
        @RequestParam("comentarioTecnico") String comentarioTecnico
    ) {
        ticketService.insertarTicket(idUsuario, tipoIncidencia, titulo, descripcion, comentariosUsuario, comentarioTecnico);
        return "redirect:/ticket/listado/ticketsEnProceso"; // o a cualquier otra página que desees
    }
    
    
    @GetMapping("/eliminarSP/{idTicket}")
    public String eliminarTicket(
        Ticket ticket) {
        ticketService.eliminarTicket(ticket.getIdTicket());
        return "redirect:/ticket/listado/ticketsEnProceso";
    }
    
    
    
    @PostMapping("/actualizarSP")
    public String editarTicket(
        @RequestParam("idTicket") Long idTicket,
        @RequestParam("usuario.idUsuario") Long idUsuario,
        @RequestParam("estado.estadoTicket") Long estado,
        @RequestParam("incidencia.incidencia") Long tipoIncidencia,
        @RequestParam("prioridad.prioridad") Long prioridad,
        @RequestParam("titulo") String titulo,
        @RequestParam("descripcion") String descripcion,
        @RequestParam("comentariosUsuario") String comentariosUsuario,
        @RequestParam("fechaRegistroUsuario") LocalDateTime fechaRegistroUsuario,
        @RequestParam("fechaRegistraTecnico") LocalDateTime fechaRegistraTecnico,
        @RequestParam("tecnico.idUsuario") Long idTecnico,
        @RequestParam("comentarioTecnico") String comentarioTecnico
    ) {
        ticketService.editarTicket(idTicket, idUsuario, estado, tipoIncidencia, prioridad, titulo, descripcion, comentariosUsuario,
                fechaRegistroUsuario, fechaRegistraTecnico, idTecnico, comentarioTecnico);
        return "redirect:/ticket/listado/ticketsEnProceso"; // o a cualquier otra página que desees
    }
    
    @GetMapping("/atencion/{idTicket}")
    public String ticketModificar(Ticket ticket, Model model) {
        ticket = ticketService.getTicket(ticket);
        List<Usuario> usuarios = usuarioService.getUsuarios();
        List<EstadoTicket> estadoTickets = estadoTicketService.getEstadoTickets();
        List<Incidencia> incidencias = incidenciaService.getIncidencias();
        List<Prioridad> prioridades = prioridadService.getPrioridades();
        model.addAttribute("ticket", ticket);
        model.addAttribute("usuarios", usuarios);
        model.addAttribute("estadoTickets", estadoTickets);
        model.addAttribute("incidencias", incidencias);
        model.addAttribute("prioridades", prioridades);
        model.addAttribute("tecnicos", usuarios);
        return "UsuarioTickets/atencionTicket"; //modificar este return
    }
    
    @PostMapping("/query1")
    public String consultaQuery(@RequestParam(value = "fecha") String fecha, Model model) {
        var tickets = ticketService.metodoFecha(fecha);
        model.addAttribute("tickets", tickets);
        model.addAttribute("fecha", fecha);
        return "UsuarioTickets/historicoTickets";
    }

    @PostMapping("/query2")
    public String consultaQuery2(@RequestParam(value = "estado") String estado, Model model) {
        var tickets = ticketService.metodoEstado(estado);
        model.addAttribute("tickets", tickets);
        model.addAttribute("estado", estado);
        return "UsuarioTickets/historicoTickets";
    }

    @PostMapping("/query3")
    public String consultaQuery3(@RequestParam(value = "incidente") String incidente, Model model) {
        var tickets = ticketService.metodoIncidente(incidente);
        model.addAttribute("tickets", tickets);
        model.addAttribute("incidente", incidente);
        return "UsuarioTickets/historicoTickets";
    }

    @GetMapping("/listado/asignados")
    public String asignados(Model model) {
        log.info("Consumiendo el recurso /ticket/listado/asignados");
//        List<Ticket> tickets = ticketService.getTickets();
        var tickets = ticketService.getTickets();
        model.addAttribute("tickets", tickets);
        return "UsuarioTickets/ticketsAsignados";
    }
    
    @GetMapping("/listado/historicoUsuario")
    public String historicoUsuario(Model model) {
//        List<Ticket> tickets = ticketService.getTickets();
        var tickets = ticketService.getTickets();
        model.addAttribute("tickets", tickets);
        return "UsuarioTickets/historicoUsuario";
    }
    
    @GetMapping("/listado/ticketsEnProceso")
    public String ticketsAbiertos(Model model) {
//        List<Ticket> tickets = ticketService.getTickets();
        var tickets = ticketService.getTickets();
        model.addAttribute("tickets", tickets);
        return "UsuarioTickets/solicitudEnProceso";
    }
    
    @GetMapping("/listado/historicoAtencion")
    public String historicoAtencion(Model model) {
//        List<Ticket> tickets = ticketService.getTickets();
        var tickets = ticketService.getTickets();
        model.addAttribute("tickets", tickets);
        return "UsuarioTickets/historicoTecnico";
    }
    
    @GetMapping("/listado/solicitudesRegistradas")
    public String solicitudesRegistradas(Model model) {
//        List<Ticket> tickets = ticketService.getTickets();
        var tickets = ticketService.getTickets();
        model.addAttribute("tickets", tickets);
        return "UsuarioTickets/asignacionTecnico";
    }

    @PostMapping("/query4")
    public String consultaQuery4(@RequestParam(value = "tecnico") String tecnico, Model model) {
        var tickets = ticketService.metodoTecnico(tecnico);
        model.addAttribute("tickets", tickets);
        model.addAttribute("tecnico", tecnico);
        return "UsuarioTickets/ticketsAsignados";
    }
    
    
    @GetMapping("/editar/{idTicket}")
    public String ticketModificarTicket(Ticket ticket, Model model) {
        ticket = ticketService.getTicket(ticket);
        List<Usuario> usuarios = usuarioService.getUsuarios();
        List<EstadoTicket> estadoTickets = estadoTicketService.getEstadoTickets();
        List<Incidencia> incidencias = incidenciaService.getIncidencias();
        List<Prioridad> prioridades = prioridadService.getPrioridades();
        model.addAttribute("ticket", ticket);
        model.addAttribute("usuarios", usuarios);
        model.addAttribute("estadoTickets", estadoTickets);
        model.addAttribute("incidencias", incidencias);
        model.addAttribute("prioridades", prioridades);
        model.addAttribute("tecnicos", usuarios);
        return "UsuarioTickets/modifica"; //modificar este return
    }
    
    @GetMapping("/ver/{idTicket}")
    public String verTicket(Ticket ticket, Model model) {
        ticket = ticketService.getTicket(ticket);
        List<Usuario> usuarios = usuarioService.getUsuarios();
        List<EstadoTicket> estadoTickets = estadoTicketService.getEstadoTickets();
        List<Incidencia> incidencias = incidenciaService.getIncidencias();
        List<Prioridad> prioridades = prioridadService.getPrioridades();
        model.addAttribute("ticket", ticket);
        model.addAttribute("usuarios", usuarios);
        model.addAttribute("estadoTickets", estadoTickets);
        model.addAttribute("incidencias", incidencias);
        model.addAttribute("prioridades", prioridades);
        model.addAttribute("tecnicos", usuarios);
        return "UsuarioTickets/verTicket"; //modificar este return
    }
    @GetMapping("/listado/solicitudesRegistradas/asignar/{idTicket}")
    public String asignarTecnico(Ticket ticket, Model model) {
        ticket = ticketService.getTicket(ticket);
        List<Usuario> usuarios = usuarioService.getUsuarios();
        List<EstadoTicket> estadoTickets = estadoTicketService.getEstadoTickets();
        List<Incidencia> incidencias = incidenciaService.getIncidencias();
        List<Prioridad> prioridades = prioridadService.getPrioridades();
        model.addAttribute("ticket", ticket);
        model.addAttribute("usuarios", usuarios);
        model.addAttribute("estadoTickets", estadoTickets);
        model.addAttribute("incidencias", incidencias);
        model.addAttribute("prioridades", prioridades);
        model.addAttribute("tecnicos", usuarios);
        return "UsuarioTickets/asignaTecnicoPrioridad"; //modificar este return
    }
    
//    @GetMapping("/listado/asignados/atencion")
//    public String atencionTicket(Model model) {
//        log.info("Consumiendo el recurso /ticket/listado/asignados/atencion");
////        List<Ticket> tickets = ticketService.getTickets();
//        var tickets = ticketService.getTickets();
//        model.addAttribute("tickets", tickets);
//        return "UsuarioTickets/atencionTicket";
//    }
    
}
