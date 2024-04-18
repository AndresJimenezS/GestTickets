package com.Proyecto.service;


import com.Proyecto.domain.Canton;
import java.util.List;

public interface CantonService {
    
//    Método que retorna la lista de cantones
    public List<Canton> getCantones();
    
    // Se obtiene un Canton, a partir del id de un canton
    public Canton getCanton(Canton canton);
    
    // Se inserta un nuevo canton si el id del canton esta vacío
    // Se actualiza un canton si el id del canton NO esta vacío
    public void save(Canton canton);
    
    // Se elimina el canton que tiene el id pasado por parámetro
    public void delete(Canton canton);
}
