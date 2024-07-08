package edu.kalum.notas.core.models.service;

import edu.kalum.notas.core.models.entity.CarreraTecnica;

import java.util.List;

public interface ICarreraTecnicaService {

    public List<CarreraTecnica> findAll();

    public CarreraTecnica findById(String id);

    public  CarreraTecnica save(CarreraTecnica carreraTecnica);

    public void delete(CarreraTecnica carreraTecnica);

    public void deleteById (String id);
}
