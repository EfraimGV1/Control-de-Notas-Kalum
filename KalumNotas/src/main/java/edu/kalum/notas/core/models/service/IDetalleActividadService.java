package edu.kalum.notas.core.models.service;

import edu.kalum.notas.core.models.entity.DetalleActividad;
import edu.kalum.notas.core.models.entity.Modulo;
import edu.kalum.notas.core.models.entity.Seminario;

import java.util.List;

public interface IDetalleActividadService {

    public List<DetalleActividad> findAll();

    public DetalleActividad findById(String id);

    public DetalleActividad save(DetalleActividad detalleActividad);

    public  void  delete(DetalleActividad detalleActividad);

    public void deleteById(String id);

    public  List<DetalleActividad> findBySeminarios(Seminario seminario);
}
