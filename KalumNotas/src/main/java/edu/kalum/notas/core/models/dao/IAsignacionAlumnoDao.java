package edu.kalum.notas.core.models.dao;

import edu.kalum.notas.core.models.entity.Alumno;
import edu.kalum.notas.core.models.entity.AsignacionAlumno;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IAsignacionAlumnoDao extends JpaRepository<AsignacionAlumno,String> {
    public List<AsignacionAlumno> findByAlumno(Alumno alumno);
}
