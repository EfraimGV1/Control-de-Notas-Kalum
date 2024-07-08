package edu.kalum.notas.core.models.dao;

import edu.kalum.notas.core.models.entity.CarreraTecnica;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ICarreraTecnicaDao extends JpaRepository<CarreraTecnica,String> {
}
