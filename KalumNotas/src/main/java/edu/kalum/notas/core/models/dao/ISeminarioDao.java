package edu.kalum.notas.core.models.dao;


import edu.kalum.notas.core.models.entity.Modulo;
import edu.kalum.notas.core.models.entity.Seminario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface ISeminarioDao extends JpaRepository<Seminario,String> {

    public List<Seminario> findByModulos (Modulo modulos);



}
