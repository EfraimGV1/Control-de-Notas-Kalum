package edu.kalum.notas.core.models.service;

import edu.kalum.notas.core.models.entity.Modulo;
import edu.kalum.notas.core.models.entity.Seminario;

import java.util.List;

public interface ISeminarioService {

    public List<Seminario> findAll();
    public  Seminario findById(String id);
    public Seminario save(Seminario seminario);
    public  void  delete(Seminario seminario);
    public void deleteById(String id);
    public List<Seminario> findByModulos(Modulo modulos);



}
