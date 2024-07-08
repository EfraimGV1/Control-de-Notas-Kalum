package edu.kalum.notas.core.models.dao;

import edu.kalum.notas.core.models.entity.Alumno;
import edu.kalum.notas.core.models.entity.DetalleActividad;
import edu.kalum.notas.core.models.entity.DetalleNota;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IDetalleNotaDao extends JpaRepository<DetalleNota,String> {

    public List<DetalleNota> findByAlumno(Alumno alumno);

    public List<DetalleNota> findByDetalleActividad(DetalleActividad detalleActividad);
}
