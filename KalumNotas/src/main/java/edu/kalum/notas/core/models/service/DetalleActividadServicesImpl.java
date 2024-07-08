package edu.kalum.notas.core.models.service;

import edu.kalum.notas.core.models.dao.IDetalleActividadDao;
import edu.kalum.notas.core.models.entity.DetalleActividad;
import edu.kalum.notas.core.models.entity.Seminario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DetalleActividadServicesImpl implements IDetalleActividadService {
    @Autowired
    private IDetalleActividadDao detalleActividadDao;

    @Override
    public List<DetalleActividad> findAll() {
        return detalleActividadDao.findAll();
    }

    @Override
    public DetalleActividad findById(String id) {
        return detalleActividadDao.findById(id).orElse(null);
    }

    @Override
    public DetalleActividad save(DetalleActividad detalleActividad) {
        return detalleActividadDao.save(detalleActividad);
    }

    @Override
    public void delete(DetalleActividad detalleActividad) {
        detalleActividadDao.delete(detalleActividad);
    }

    @Override
    public void deleteById(String id) {
        detalleActividadDao.deleteById(id);
    }

    @Override
    public List<DetalleActividad> findBySeminarios(Seminario seminario) {
        return detalleActividadDao.findBySeminarios(seminario);
    }
}
