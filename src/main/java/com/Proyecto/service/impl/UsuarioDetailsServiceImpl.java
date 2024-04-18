package com.Proyecto.service.impl;

import com.Proyecto.dao.UsuarioDao;
import com.Proyecto.domain.Rol;
import com.Proyecto.domain.Usuario;
import com.Proyecto.service.UsuarioDetailsService;
import jakarta.servlet.http.HttpSession;
import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("userDetailsService")
public class UsuarioDetailsServiceImpl implements UsuarioDetailsService, UserDetailsService {

    @Autowired
    private UsuarioDao usuarioDao;

//    @Autowired
//    private HttpSession session;
    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Buscar en BD el usuario
        Usuario usuario = usuarioDao.findByUsername(username);

        if (usuario == null) {
            throw new UsernameNotFoundException("El usuario " + username + " no existe");
        }

//        // Variables de sesión
//        session.removeAttribute("usuarioImagen");
//        session.setAttribute("usuarioImagen", usuario.getRutaImagen());
        // Extraer los roles
        var roles = new ArrayList<GrantedAuthority>();
//        for (Rol rol : usuario.getRoles()){
//            roles.add(new SimpleGrantedAuthority(rol.getDescripcion()));
//        }
        roles.add(new SimpleGrantedAuthority(usuario.getRol().getDescripcion()));

        return new User(usuario.getUsername(), usuario.getContrasena(), roles);
    }

}
