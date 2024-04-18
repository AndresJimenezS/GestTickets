
package com.Proyecto.service.impl;

import com.Proyecto.dao.UsuarioDao;
import com.Proyecto.domain.Usuario;
import com.Proyecto.domain.UsuarioCursor;
import com.Proyecto.service.UsuarioService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UsuarioServiceImpl implements UsuarioService{
    
    @Autowired
    private UsuarioDao usuarioDao;
    
    
    @Override
    @Transactional(readOnly = true)
    public List<Usuario> getUsuarios() {
        List<Usuario> lista = usuarioDao.findAll();
        return lista;
    }
    
    @Override
    @Transactional(readOnly = true)
    public Usuario getUsuario(Usuario usuario) {
        return usuarioDao.findById(usuario.getIdUsuario()).orElse(null);
    }

    @Override
    @Transactional
    public void save(Usuario usuario) {
        usuarioDao.save(usuario);
    }
  
    @Override
    @Transactional
    public void delete(Usuario usuario) {
        usuarioDao.delete(usuario);
    }
    
    @Override
    @Transactional(readOnly = true)
    public Usuario getUsuarioPorUsername(String username) {
        return usuarioDao.findByUsername(username);
    }

    @Override
    @Transactional(readOnly = true)
    public Usuario getUsuarioPorUsernameYContrasena(String username, String contrasena) {
        return usuarioDao.findByUsernameAndContrasena(username, contrasena);
    }

    @Override
    @Transactional(readOnly = true)
    public Usuario getUsuarioPorUsernameOEmail(String username, String email) {
        return usuarioDao.findByUsernameOrEmail(username, email);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existeUsuarioPorUsernameOEmail(String username, String email) {
        return usuarioDao.existsByUsernameOrEmail(username, email);
    }

    @Override
    public void editarUsuario(Long idUsuario, String email, Long telefono, String sexo, String pais,
            Long provincia, Long canton, Long distrito, String otrasSenas) {
        usuarioDao.editarUsuario(idUsuario, email, telefono, sexo, pais, provincia, canton, distrito, otrasSenas);
    }

    @Override
    public void eliminarUsuario(Long id) {
        usuarioDao.eliminarUsuario(id); 
    }

    @Override
    public void editarRoles(String username, Long rol, Long estadoUsuario) {
        usuarioDao.editarRoles(username, rol, estadoUsuario);
    }
    
    @Override
    public void exportarTecnicosCSV(String nombreArchivo) {
        usuarioDao.exportarTecnicosCSV(nombreArchivo);
    }
    
    // USUARIOS FUNCIÓN CURSOR
    @Autowired
    private UsuarioCursor usuarioCursor;

    public List<Usuario> getUsuariosCursor() {
        return usuarioCursor.obtenerUsuarios();
    }
}
