
package com.Proyecto.service.impl;

import com.Proyecto.dao.EstadoUsuarioDao;
import com.Proyecto.domain.EstadoUsuario;
import com.Proyecto.service.EstadoUsuarioService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class EstadoUsuarioServiceImpl implements EstadoUsuarioService{
    
    @Autowired
    private EstadoUsuarioDao estadoUsuarioDao;
    
    
    @Override
    @Transactional(readOnly = true)
    public List<EstadoUsuario> getEstadoUsuarios() {
        List<EstadoUsuario> lista = estadoUsuarioDao.findAll();
        return lista;
    }
    
    @Override
    @Transactional(readOnly = true)
    public EstadoUsuario getEstadoUsuario(EstadoUsuario estadoUsuario) {
        return estadoUsuarioDao.findById(estadoUsuario.getEstadoUsuario()).orElse(null);
    }

    @Override
    @Transactional
    public void save(EstadoUsuario estadoUsuario) {
        estadoUsuarioDao.save(estadoUsuario);
    }

    @Override
    @Transactional
    public void delete(EstadoUsuario estadoUsuario) {
        estadoUsuarioDao.delete(estadoUsuario);
    }
}
