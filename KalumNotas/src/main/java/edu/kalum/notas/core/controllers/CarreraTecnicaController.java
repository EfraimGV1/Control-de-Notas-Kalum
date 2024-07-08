package edu.kalum.notas.core.controllers;

import edu.kalum.notas.core.models.entity.CarreraTecnica;
import edu.kalum.notas.core.models.entity.Modulo;
import edu.kalum.notas.core.models.service.ICarreraTecnicaService;
import edu.kalum.notas.core.models.service.IModuloService;
import org.hibernate.exception.JDBCConnectionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.CannotCreateTransactionException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import  org.springframework.context.expression.MapAccessor;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/kalum-notas/v1")
public class CarreraTecnicaController {

    private Logger logger = LoggerFactory.getLogger(CarreraTecnicaController.class);

    @Autowired
    private ICarreraTecnicaService carreraTecnicaService;


    
    @GetMapping (value = "/carrerasTecnicas")
    public ResponseEntity<?> listarCarrerasTecnicas()
    {
        logger.info("Iniciando Consulta");
        Map<String,Object> response = new HashMap<>();
        try
        {
            logger.debug("Iniciando consulta a la base de datos");
            List<CarreraTecnica> carreras = this.carreraTecnicaService.findAll();
            if (carreras.size() == 0)
            {
                logger.warn("no existen registros en la base de datos");
                response.put("Mensaje","No existen Registros");
                return new ResponseEntity<Map<String,Object>> (response,HttpStatus.NO_CONTENT);
            }else
            {
                logger.info("finalizando consulta");
                return  new ResponseEntity<List<CarreraTecnica>>(carreras,HttpStatus.OK);
            }
        }
        catch (CannotCreateTransactionException e)
        {
            logger.error("Error al momento de conectarse a la base de datos");
            response.put("mensaje","Error al realizar la consulta a la base de datos");
            response.put("Error",e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String,Object>>(response, HttpStatus.SERVICE_UNAVAILABLE);
        }
        catch (DataAccessException e)
        {
            logger.error("Error al consultar la info en la base de datos");
            response.put("Mensaje","Error al realizar la consulta a la base de datos");
            response.put("Error",e.getMessage().concat(":  ").concat(e.getMostSpecificCause().getMessage()));
            return  new ResponseEntity<Map<String ,Object>>(response,HttpStatus.SERVICE_UNAVAILABLE);
        }

    }
    @GetMapping("/carrerasTecnicas/{id}")
    public ResponseEntity<?> show(@PathVariable String id)
    {
        Map<String,Object> response = new HashMap<>();
        try {

            CarreraTecnica carreras = this.carreraTecnicaService.findById(id);


            if (carreras == null)
            {
                response.put("Mensaje","No existe el registro con el Id  ".concat(id));
                return  new ResponseEntity<Map<String,Object>>(response,HttpStatus.NOT_FOUND);
            }else
            {
                return  new ResponseEntity<CarreraTecnica>(carreras,HttpStatus.OK);
            }
        }
        catch (CannotCreateTransactionException e)
        {
            response.put("mensaje","Error al realizar la consulta a la base de datos");
            response.put("Error",e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String,Object>>(response, HttpStatus.SERVICE_UNAVAILABLE);
        }
        catch (DataAccessException e)
        {
            response.put("Mensaje","Error al realizar la consulta a la base de datos");
            response.put("Error",e.getMessage().concat(":  ").concat(e.getMostSpecificCause().getMessage()));
            return  new ResponseEntity<Map<String ,Object>>(response,HttpStatus.SERVICE_UNAVAILABLE);
        }


    }
    @PostMapping("/carrerasTecnicas")
    public ResponseEntity<?> create(@Valid @RequestBody CarreraTecnica elemento, BindingResult result)
    {
        CarreraTecnica carreraTecnica = null;
        Map<String,Object> response = new HashMap<>();
        if  (result.hasErrors())
        {
                List<String > errores = result.getFieldErrors().stream()
                        .map(err -> err.getDefaultMessage()).collect(Collectors.toList());

                response.put("Error",errores);
                return new ResponseEntity<Map<String, Object >>(response,HttpStatus.BAD_REQUEST);
        }
        try {
           elemento.setCodigoCarrera(UUID.randomUUID().toString());
           carreraTecnica = this.carreraTecnicaService.save(elemento);
        }

        catch (DataAccessException e)
        {
            response.put("Mensaje","Error al realizar el insert en la base de datos ");
            response.put("Error",e.getMessage().concat(":  ").concat(e.getMostSpecificCause().getMessage()));
            return  new ResponseEntity<Map<String,Object>>(response,HttpStatus.SERVICE_UNAVAILABLE);
        }

        catch (Exception e)
        {
            response.put("Mensaje","Error al realizar el insert en la base de datos ");
            response.put("Error",e.getMessage().concat(":  ").concat(e.getMessage()));
            return  new ResponseEntity<Map<String,Object>>(response,HttpStatus.SERVICE_UNAVAILABLE);
        }
        response.put("Mensaje","La carrera Tecnica ha sido creada con exito");
        response.put("Carrera Tecnica", carreraTecnica);
        return  new ResponseEntity<Map<String,Object>>(response,HttpStatus.CREATED);
    }

    @DeleteMapping("/carrerasTecnicas/{id}")
    public ResponseEntity<?> delete(@PathVariable String id)
    {
        Map<String,Object> response = new HashMap<String, Object>();
        try
        {
            CarreraTecnica registro = this.carreraTecnicaService.findById(id);
            if (registro == null)
            {
                response.put("Mensaje","No existe el registro con el ID : ".concat(id));
                response.put("Error","El ID no se encontro en la base de datos");
                return  new ResponseEntity<Map<String ,Object>>(response, HttpStatus.NOT_FOUND);
            }
            this.carreraTecnicaService.delete(registro);
        }
        catch (DataAccessException e)
        {
            response.put("Mensaje","Error al eliminar la carrera");
            response.put("Error",e.getMessage().concat(":  ").concat(e.getMostSpecificCause().getMessage()));
        }
        response.put("Mensaje", "La Carrera Tecnica has sido eliminada ");
                return new ResponseEntity<Map<String,Object>>(response,HttpStatus.OK);
    }

    @PutMapping("/carrerasTecnicas/{id}")
    public ResponseEntity<?> update (@Valid @RequestBody CarreraTecnica value
            ,BindingResult result,@PathVariable String id)
    {
        Map<String,Object> response = new HashMap<>();
        CarreraTecnica update = this.carreraTecnicaService.findById(id);
        if (update == null)
        {
            response.put("Mensaje","No existe el registro con el ID : ".concat(id));
            response.put("Error","El ID no se encontro en la base de datos");
            return  new ResponseEntity<Map<String ,Object>>(response, HttpStatus.NOT_FOUND);
        }
        if (result.hasErrors())
        {
            List<String > errores = result.getFieldErrors()
                    .stream().map(err -> err.getDefaultMessage())
                    .collect(Collectors.toList());
            response.put("Errores",errores);
            return new ResponseEntity<Map<String,Object>>(response, HttpStatus.BAD_REQUEST);
        }
        try
        {
          update.setNombre(value.getNombre());
          this.carreraTecnicaService.save(update);
          response.put("Carrera Tecnica","Datos actualizados");
          return  new ResponseEntity<Map<String ,Object>>(response,HttpStatus.NO_CONTENT);
        }
        catch (DataAccessException e)
        {
            response.put("Mensaje","Error al actualizar la data");
            response.put("Error",e.getMessage().concat(" :  ".concat(e.getMostSpecificCause().getMessage())));
            return  new ResponseEntity<Map<String,Object>>(response,HttpStatus.SERVICE_UNAVAILABLE);
        }
    }

}
