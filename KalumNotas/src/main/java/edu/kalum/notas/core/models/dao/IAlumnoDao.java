package edu.kalum.notas.core.models.dao;

import edu.kalum.notas.core.models.entity.Alumno;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IAlumnoDao extends JpaRepository<Alumno,String> {
}
