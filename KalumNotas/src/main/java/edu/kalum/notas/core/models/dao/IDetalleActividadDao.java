package edu.kalum.notas.core.models.dao;

import edu.kalum.notas.core.models.entity.DetalleActividad;
import edu.kalum.notas.core.models.entity.Seminario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IDetalleActividadDao extends JpaRepository<DetalleActividad,String> {

    public List<DetalleActividad> findBySeminarios(Seminario seminarios);

}
