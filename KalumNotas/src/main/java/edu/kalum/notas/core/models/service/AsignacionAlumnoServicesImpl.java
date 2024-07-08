package edu.kalum.notas.core.models.service;

import edu.kalum.notas.core.models.dao.IAsignacionAlumnoDao;
import edu.kalum.notas.core.models.entity.Alumno;
import edu.kalum.notas.core.models.entity.AsignacionAlumno;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AsignacionAlumnoServicesImpl implements IAlumnoAsignacionService {

    @Autowired
    private IAsignacionAlumnoDao asignacionAlumnoDao;

    @Override
    public List<AsignacionAlumno> findAll() {
        return asignacionAlumnoDao.findAll();
    }

    @Override
    public AsignacionAlumno findById(String id) {
        return asignacionAlumnoDao.findById(id).orElse(null);
    }

    @Override
    public AsignacionAlumno save(AsignacionAlumno asignacionAlumno) {
        return asignacionAlumnoDao.save(asignacionAlumno);
    }

    @Override
    public void delete(AsignacionAlumno asignacionAlumno) {
        asignacionAlumnoDao.delete(asignacionAlumno);
    }

    @Override
    public void deleteById(String id) {
        asignacionAlumnoDao.deleteById(id);
    }

    @Override
    public List<AsignacionAlumno> findByAlumno(Alumno alumno) {
        return asignacionAlumnoDao.findByAlumno(alumno);
    }
}
