package edu.kalum.notas.core.models.service;

import edu.kalum.notas.core.models.dao.IDetalleNotaDao;
import edu.kalum.notas.core.models.entity.Alumno;
import edu.kalum.notas.core.models.entity.DetalleActividad;
import edu.kalum.notas.core.models.entity.DetalleNota;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DetalleNotaServicesImpl implements IDetalleNotaService {

    @Autowired
    private IDetalleNotaDao detalleNotaDao;

    @Override
    public List<DetalleNota> findAll() {
        return detalleNotaDao.findAll();
    }

    @Override
    public DetalleNota findById(String id) {
        return detalleNotaDao.findById(id).orElse(null);
    }

    @Override
    public DetalleNota save(DetalleNota detalleNota) {
        return detalleNotaDao.save(detalleNota);
    }

    @Override
    public void delete(DetalleNota detalleNota) {
        detalleNotaDao.delete(detalleNota);
    }

    @Override
    public void deleteById(String id) {
        detalleNotaDao.deleteById(id);
    }

    @Override
    public List<DetalleNota> findByAlumno(Alumno alumno) {
        return detalleNotaDao.findByAlumno(alumno);
    }

    @Override
    public List<DetalleNota> findByDetalleActividad(DetalleActividad detalleActividad) {
        return detalleNotaDao.findByDetalleActividad(detalleActividad);
    }
}
