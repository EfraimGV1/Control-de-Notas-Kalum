package edu.kalum.notas.core.models.service;

import edu.kalum.notas.core.models.entity.CarreraTecnica;
import edu.kalum.notas.core.models.entity.Modulo;

import java.util.List;

public interface IModuloService {

    public List<Modulo> findAll();

    public Modulo findById(String id);

    public  Modulo save(Modulo modulo);

    public  void  delete(Modulo modulo);

    public void deleteById(String id);

    public List<Modulo> findByCarreraTecnia(CarreraTecnica carreraTecnica);

}
