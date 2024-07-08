package edu.kalum.notas.core.models.service;

import edu.kalum.notas.core.models.dao.IAlumnoDao;
import edu.kalum.notas.core.models.entity.Alumno;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AlumnoServicesImpl implements IAlumnoService{

    @Autowired
    private IAlumnoDao alumnoDao;

    @Override
    public List<Alumno> findAll() {
        return alumnoDao.findAll();
    }

    @Override
    public Alumno findById(String id) {
        return alumnoDao.findById(id).orElse(null);
    }

    @Override
    public Alumno save(Alumno alumno) {
        return alumnoDao.save(alumno);
    }

    @Override
    public void delete(Alumno alumno) {
        alumnoDao.delete(alumno);
    }

    @Override
    public void deleteById(String id) {
        alumnoDao.deleteById(id);
    }
}
