package com.Proyecto.controller;

import com.Proyecto.domain.Canton;
import com.Proyecto.domain.Distrito;
import com.Proyecto.domain.EstadoUsuario;
import com.Proyecto.domain.Provincia;
import com.Proyecto.domain.Rol;
import com.Proyecto.domain.Usuario;
import com.Proyecto.service.CantonService;
import com.Proyecto.service.DistritoService;
import com.Proyecto.service.EstadoUsuarioService;
import com.Proyecto.service.ProvinciaService;
import com.Proyecto.service.RegistroService;
import com.Proyecto.service.RolService;
import com.Proyecto.service.UsuarioService;
import jakarta.mail.MessagingException;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
@Slf4j
@RequestMapping("/registro")
public class RegistroController {

    @Autowired
    private RegistroService registroService;
    
    @Autowired
    UsuarioService usuarioService;

    @Autowired
    ProvinciaService provinciaService;

    @Autowired
    CantonService cantonService;

    @Autowired
    DistritoService distritoService;

    @Autowired
    RolService rolService;

    @Autowired
    EstadoUsuarioService estadoUsuarioService;
    

    @GetMapping("/nuevo")
    public String nuevo(Model model, Usuario usuario) {
        return "/registro/nuevo";
    }

    @GetMapping("/recordar")
    public String recordar(Model model, Usuario usuario) {
        return "/registro/recordar";
    }

    @PostMapping("/crearUsuario")
    public String crearUsuario(Model model, Usuario usuario) 
            throws MessagingException {
//        usuario.setContrasena(new BCryptPasswordEncoder().encode(usuario.getContrasena()));
        model = registroService.crearUsuario(model, usuario);
        return "/registro/salida";
    }

     @GetMapping("/Registrarse")
    public String Registrarse(Usuario usuario, Model model) {
        List<Provincia> provincias = provinciaService.getProvincias();
        List<Canton> cantones = cantonService.getCantones();
        List<Distrito> distritos = distritoService.getDistritos();
        List<Rol> roles = rolService.getRoles();
        List<EstadoUsuario> estadoUsuarios = estadoUsuarioService.getEstadoUsuarios();
        model.addAttribute("provincias", provincias);
        model.addAttribute("cantones", cantones);
        model.addAttribute("distritos", distritos);
        model.addAttribute("roles", roles);
        model.addAttribute("estadoUsuarios", estadoUsuarios);
        return "Registrarse";
    }

//    @PostMapping("/guardar")
//    public String usuarioGuardar(Usuario usuario) {
//        usuario.setContrasena(new BCryptPasswordEncoder().encode(usuario.getContrasena()));
//        usuarioService.save(usuario);
//        return "redirect:/login";
//    }
    
    @GetMapping("/activacion/{usuario}/{id}")
    public String activar(
            Model model, 
            @PathVariable(value = "usuario") String usuario, 
            @PathVariable(value = "id") String id) {
        model = registroService.activar(model, usuario, id);
        if (model.containsAttribute("usuario")) {
            return "/registro/activa";
        } else {
            return "/registro/salida";
        }
    }

    @PostMapping("/activar")
    public String activar(
            Usuario usuario) {
        registroService.activar(usuario);
        return "redirect:/";
    }

    @PostMapping("/recordarUsuario")
    public String recordarUsuario(Model model, Usuario usuario) 
            throws MessagingException {
        model = registroService.recordarUsuario(model, usuario);
        return "/registro/salida";
    }
    
    // Ejecución Proced. Almacenados
    
    @PostMapping("/insertarUsuarioSP")
    public String insertarUsuario(
        Model model,
        Usuario usuario,
        @RequestParam("username") String username,
        @RequestParam("contrasena") String contrasena,
        @RequestParam("nombre") String nombreCompleto,
        @RequestParam("cedula") Long cedula,
        @RequestParam("email") String email,
        @RequestParam("telefono") Long telefono,
        @RequestParam("sexo") String sexo,
        @RequestParam("pais") String pais,
        @RequestParam("provincia.provincia") Provincia provincia,
        @RequestParam("canton.canton") Canton canton,
        @RequestParam("distrito.distrito") Distrito distrito,
        @RequestParam("otrasSenas") String otrasSenas,
        @RequestParam("rol.rol") Rol rol,
        @RequestParam("estadoUsuario.estadoUsuario") EstadoUsuario estadoUsuario,
        @RequestParam("terminosCondiciones") Long terminosCondiciones  
    ){
        registroService.insertarUsuario(model, usuario, username, contrasena, nombreCompleto, cedula, email, telefono, sexo, pais, provincia.getProvincia(), canton.getCanton(), 
                distrito.getDistrito(), otrasSenas, rol.getRol(), estadoUsuario.getEstadoUsuario(), terminosCondiciones);
        return "redirect:/login";
    }

}
    
