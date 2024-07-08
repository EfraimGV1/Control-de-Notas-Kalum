package edu.kalum.notas.core.models.dao;

import edu.kalum.notas.core.models.entity.CarreraTecnica;
import edu.kalum.notas.core.models.entity.Modulo;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.List;

public interface IModuloDao extends JpaRepository<Modulo,String> {

    public List<Modulo>  findByCarreraTecnica (CarreraTecnica carreraTecnica);

  /*@Query("select m from Modulo m where m.carreraTecnica.codigoCarrera = ?1")
    public List<Modulo> buscarModulos(String codigoCarrera);*/
    /*son basicamente lo mismo debido a que el IDE esta mas intiutivo bajo cierto tipo de escritura */
}
