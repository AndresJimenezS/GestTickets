
package com.Proyecto.domain;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import oracle.jdbc.OracleTypes;

@Repository
public class UsuarioCursor {

    @PersistenceContext
    private EntityManager em;

    public List<Usuario> obtenerUsuarios() {
        List<Usuario> usuarios = new ArrayList<>();
        Session session = em.unwrap(Session.class);
        session.doWork(connection -> {
            try (CallableStatement function = connection.prepareCall(
                "{ ? = call getUsuarios() }")) {
                function.registerOutParameter(1, OracleTypes.CURSOR);
                function.execute();
                ResultSet rs = (ResultSet) function.getObject(1);
                while (rs.next()) {
                    Usuario usuario = new Usuario(rs.getLong("id_usuario"), rs.getString("nombre_completo"), rs.getInt("cedula"));
                    usuarios.add(usuario);
                }
            } catch (SQLException ex) {
                Logger.getLogger(UsuarioCursor.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        return usuarios;
    }
}
