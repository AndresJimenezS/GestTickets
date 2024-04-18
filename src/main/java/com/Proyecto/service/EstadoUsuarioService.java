package com.Proyecto.service;


import com.Proyecto.domain.EstadoUsuario;
import java.util.List;

public interface EstadoUsuarioService {
    
//    Método que retorna la lista de estadoUsuarios
    public List<EstadoUsuario> getEstadoUsuarios();
    
    // Se obtiene un EstadoUsuario, a partir del id de un estadoUsuario
    public EstadoUsuario getEstadoUsuario(EstadoUsuario estadoUsuario);
    
    // Se inserta un nuevo estadoUsuario si el id del estadoUsuario esta vacío
    // Se actualiza un estadoUsuario si el id del estadoUsuario NO esta vacío
    public void save(EstadoUsuario estadoUsuario);
    
    // Se elimina el estadoUsuario que tiene el id pasado por parámetro
    public void delete(EstadoUsuario estadoUsuario);
}
