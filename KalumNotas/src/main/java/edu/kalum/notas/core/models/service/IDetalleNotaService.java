package edu.kalum.notas.core.models.service;

import edu.kalum.notas.core.models.entity.Alumno;
import edu.kalum.notas.core.models.entity.DetalleActividad;
import edu.kalum.notas.core.models.entity.DetalleNota;

import java.util.List;

public interface IDetalleNotaService {

    public List<DetalleNota> findAll();

    public DetalleNota findById(String id);

    public DetalleNota save(DetalleNota detalleNota);

    public void delete(DetalleNota detalleNota);

    public void deleteById(String id);

    public List<DetalleNota> findByAlumno(Alumno alumno);

    public List<DetalleNota> findByDetalleActividad(DetalleActividad detalleActividad);
}
