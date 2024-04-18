package com.Proyecto.service;

import com.Proyecto.domain.Usuario;
import jakarta.mail.MessagingException;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;

public interface RegistroService {

    public Model activar(Model model, String usuario, String clave);

    public Model crearUsuario(Model model, Usuario usuario) throws MessagingException;
    
    public void activar(Usuario usuario);
    
    public Model recordarUsuario(Model model, Usuario usuario) throws MessagingException;

    // Método Insertar en Oracle
    public Model insertarUsuario(Model model, Usuario usuario, String username, String contrasena, String nombreCompleto, Long cedula, String email, Long telefono, String sexo, String pais, Long provincia, Long canton, Long distrito, String otrasSenas, Long rol, Long estadoUsuario, Long terminosCondiciones);

}
