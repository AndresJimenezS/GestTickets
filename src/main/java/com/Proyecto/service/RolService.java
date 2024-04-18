package com.Proyecto.service;


import com.Proyecto.domain.Rol;
import java.util.List;

public interface RolService {
    
//    Método que retorna la lista de roles
    public List<Rol> getRoles();
    
    // Se obtiene un Rol, a partir del id de un rol
    public Rol getRol(Rol rol);
    
    // Se inserta un nuevo rol si el id del rol esta vacío
    // Se actualiza un rol si el id del rol NO esta vacío
    public void save(Rol rol);
    
    // Se elimina el rol que tiene el id pasado por parámetro
    public void delete(Rol rol);
}
