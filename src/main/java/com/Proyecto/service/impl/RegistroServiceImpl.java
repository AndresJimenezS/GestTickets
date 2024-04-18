package com.Proyecto.service.impl;

import com.Proyecto.dao.UsuarioDao;
import com.Proyecto.domain.Usuario;
import com.Proyecto.service.CorreoService;
import com.Proyecto.service.RegistroService;
import com.Proyecto.service.UsuarioService;
import jakarta.mail.MessagingException;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;

@Service
public class RegistroServiceImpl implements RegistroService {

    @Autowired
    private CorreoService correoService;
    @Autowired
    private UsuarioService usuarioService;
    @Autowired
    private MessageSource messageSource;  
    @Autowired
    private UsuarioDao usuarioDao;


    @Override
    public Model activar(Model model, String username, String clave) {
        Usuario usuario =  usuarioDao.findByUsername(username);
        if (usuario != null) {
            model.addAttribute("usuario", usuario);
            System.out.println("\n\n\n\n\n\n\n\n\n\n------------------------\n"+ usuario.getUsername()+"\n"+usuario.getContrasena());
        } else {
            model.addAttribute(
                    "titulo", 
                    messageSource.getMessage(
                            "registro.activar", 
                            null,  Locale.getDefault()));
            model.addAttribute(
                    "mensaje", 
                    messageSource.getMessage(
                            "registro.activar.error", 
                            null, Locale.getDefault()));
        }
        return model;
    }

    @Override
    public void activar(Usuario usuario) {
        var codigo = new BCryptPasswordEncoder();
        usuario.setContrasena(codigo.encode(usuario.getContrasena()));
//        usuario.setEstadoUsuario(estadoUsuario);

//        if (!imagenFile.isEmpty()) {
//            usuarioService.save(usuario, false);
//            usuario.setRutaImagen(

//                    firebaseStorageService.cargaImagen(
//                            imagenFile, 
//                            "usuarios", 
//                            usuario.getIdUsuario()));
//        }
        usuarioService.save(usuario);
    }

    @Override
    public Model crearUsuario(Model model, Usuario usuario) 
            throws MessagingException {
        String mensaje;
        if (!usuarioService.
                existeUsuarioPorUsernameOEmail(
                        usuario.getUsername(), 
                        usuario.getEmail())) {
            String clave = demeClave();
            usuario.setContrasena(clave);
            usuarioService.save(usuario);
            enviaCorreoActivar(usuario, clave);
            mensaje = String.format(
                    messageSource.getMessage(
                            "registro.mensaje.activacion.ok", 
                            null, 
                            Locale.getDefault()),
                    usuario.getEmail());
        } else {
            mensaje = String.format(
                    messageSource.getMessage(
                            "registro.mensaje.usuario.o.correo", 
                            null, 
                            Locale.getDefault()),
                    usuario.getUsername(), usuario.getEmail());
        }
        model.addAttribute(
                "titulo", 
                messageSource.getMessage(
                        "registro.activar", 
                        null, 
                        Locale.getDefault()));
        model.addAttribute(
                "mensaje", 
                mensaje);
        return model;
    }

    @Override
    public Model recordarUsuario(Model model, Usuario usuario) 
            throws MessagingException {
        String mensaje;
        Usuario usuario2 = usuarioService.getUsuarioPorUsernameOEmail(
                usuario.getUsername(), 
                usuario.getEmail());
        if (usuario2 != null) {
            String clave = demeClave();
            usuario2.setContrasena(clave);
            usuarioService.save(usuario2);
            enviaCorreoRecordar(usuario2, clave);
            mensaje = String.format(
                    messageSource.getMessage(
                            "registro.mensaje.recordar.ok", 
                            null, 
                            Locale.getDefault()),
                    usuario.getEmail());
        } else {
            mensaje = String.format(
                    messageSource.getMessage(
                            "registro.mensaje.no.existe", 
                            null, 
                            Locale.getDefault()),
                    usuario.getUsername(), usuario.getEmail());
        }
        model.addAttribute(
                "titulo", 
                messageSource.getMessage(
                        "registro.activar", 
                        null, 
                        Locale.getDefault()));
        model.addAttribute(
                "mensaje", 
                mensaje);
        return model;
    }

    private String demeClave() {
        String tira = "ABCDEFGHIJKLMNOPQRSTUXYZabcdefghijklmnopqrstuvwxyz0123456789_*+-";
        String clave = "";
        for (int i = 0; i < 40; i++) {
            clave += tira.charAt((int) (Math.random() * tira.length()));
        }
        return clave;
    }

    //Ojo cómo le lee una informacion del application.properties
    @Value("${servidor.http}")
    private String servidor;

    private void enviaCorreoActivar(Usuario usuario, String clave) throws MessagingException {
        String mensaje = messageSource.getMessage(
                "registro.correo.activar", 
                null, Locale.getDefault());
        mensaje = String.format(
                mensaje, usuario.getUsername(), servidor, 
                usuario.getUsername(), clave);
        String asunto = messageSource.getMessage(
                "registro.mensaje.activacion", 
                null, Locale.getDefault());
        correoService.enviarCorreoHtml(usuario.getEmail(), asunto, mensaje);
    }

    private void enviaCorreoRecordar(Usuario usuario, String clave) throws MessagingException {
        String mensaje = messageSource.getMessage(""
                + "registro.correo.recordar", 
                null, 
                Locale.getDefault());
        mensaje = String.format(
                mensaje, usuario.getNombreCompleto(), 
                servidor, 
                usuario.getUsername(), clave);
        String asunto = messageSource.getMessage(
                "registro.mensaje.recordar", 
                null, Locale.getDefault());
        correoService.enviarCorreoHtml(
                usuario.getEmail(), 
                asunto, mensaje);
    }

    @Override
    public Model insertarUsuario(Model model, Usuario usuario, String username, String contrasena, String nombreCompleto, Long cedula, String email, Long telefono, String sexo, String pais, Long provincia, Long canton, Long distrito, String otrasSenas, Long rol, Long estadoUsuario, Long terminosCondiciones) 
    {
        String mensaje;
        if (!usuarioService.
                existeUsuarioPorUsernameOEmail(
                        usuario.getUsername(), 
                        usuario.getEmail())) {
            String clave = demeClave();
            usuario.setContrasena(clave);
            //usuarioService.save(usuario);
            usuarioDao.insertarUsuario(username, contrasena, nombreCompleto, cedula, email, telefono, sexo, pais, provincia, canton,  distrito, otrasSenas, rol, estadoUsuario, terminosCondiciones);
            try {
                enviaCorreoActivar(usuario, clave);
            } catch (MessagingException ex) {
                Logger.getLogger(RegistroServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
            }
            mensaje = String.format(
                    messageSource.getMessage(
                            "registro.mensaje.activacion.ok", 
                            null, 
                            Locale.getDefault()),
                    usuario.getEmail());
        } else {
            mensaje = String.format(
                    messageSource.getMessage(
                            "registro.mensaje.usuario.o.correo", 
                            null, 
                            Locale.getDefault()),
                    usuario.getUsername(), usuario.getEmail());
        }
        model.addAttribute(
                "titulo", 
                messageSource.getMessage(
                        "registro.activar", 
                        null, 
                        Locale.getDefault()));
        model.addAttribute(
                "mensaje", 
                mensaje);
        return model;
    }
    
}


