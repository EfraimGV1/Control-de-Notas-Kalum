package edu.kalum.notas.core.models.service;

import edu.kalum.notas.core.models.entity.Alumno;

import java.util.List;

public interface IAlumnoService {

    public List<Alumno> findAll();

    public Alumno findById(String id);

    public Alumno save(Alumno alumno);

    public void delete(Alumno alumno);

    public void deleteById(String id);
}
