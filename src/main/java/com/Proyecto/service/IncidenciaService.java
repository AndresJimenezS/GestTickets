package com.Proyecto.service;


import com.Proyecto.domain.Incidencia;
import java.util.List;

public interface IncidenciaService {
    
//    Método que retorna la lista de incidencias
    public List<Incidencia> getIncidencias();
    
    // Se obtiene un Incidencia, a partir del id de un incidencia
    public Incidencia getIncidencia(Incidencia incidencia);
    
    // Se inserta un nuevo incidencia si el id del incidencia esta vacío
    // Se actualiza un incidencia si el id del incidencia NO esta vacío
    public void save(Incidencia incidencia);
    
    // Se elimina el incidencia que tiene el id pasado por parámetro
    public void delete(Incidencia incidencia);
}
