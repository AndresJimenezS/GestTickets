package com.Proyecto.service;


import com.Proyecto.domain.Prioridad;
import java.util.List;

public interface PrioridadService {
    
//    Método que retorna la lista de prioridades
    public List<Prioridad> getPrioridades();
    
    // Se obtiene un Prioridad, a partir del id de un prioridad
    public Prioridad getPrioridad(Prioridad prioridad);
    
    // Se inserta un nuevo prioridad si el id del prioridad esta vacío
    // Se actualiza un prioridad si el id del prioridad NO esta vacío
    public void save(Prioridad prioridad);
    
    // Se elimina el prioridad que tiene el id pasado por parámetro
    public void delete(Prioridad prioridad);
}
