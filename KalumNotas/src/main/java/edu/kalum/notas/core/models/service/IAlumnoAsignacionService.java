package edu.kalum.notas.core.models.service;

import edu.kalum.notas.core.models.entity.Alumno;
import edu.kalum.notas.core.models.entity.AsignacionAlumno;

import java.util.List;

public interface IAlumnoAsignacionService {

    public List<AsignacionAlumno> findAll();

    public AsignacionAlumno findById(String id);

    public AsignacionAlumno save(AsignacionAlumno asignacionAlumno);

    public void delete(AsignacionAlumno asignacionAlumno);

    public void deleteById(String id);

    public List<AsignacionAlumno> findByAlumno(Alumno alumno);
}
