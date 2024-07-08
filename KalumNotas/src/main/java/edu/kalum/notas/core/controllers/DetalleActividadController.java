package edu.kalum.notas.core.controllers;

import edu.kalum.notas.core.models.entity.DetalleActividad;
import edu.kalum.notas.core.models.entity.Modulo;
import edu.kalum.notas.core.models.service.DetalleActividadServicesImpl;
import edu.kalum.notas.core.models.service.IDetalleActividadService;
import edu.kalum.notas.core.models.service.ISeminarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.CannotCreateTransactionException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/kalum-notas/v1")
public class DetalleActividadController {

    @Autowired
    private IDetalleActividadService detalleActividadServices;

    @Autowired
    private ISeminarioService seminarioService;

    @GetMapping("/detalle-actividad")
    public ResponseEntity<?> listarActividades()
    {   Map<String,Object> response = new HashMap<>();
        try {

            List<DetalleActividad> detalleActividadList = this.detalleActividadServices.findAll();
            if (detalleActividadList.size() == 0)
            {
                response.put("Mensaje","No existen Registros");
                return new ResponseEntity<Map<String,Object>> (response, HttpStatus.NO_CONTENT);
            }
            else
            {
                return new ResponseEntity<List<DetalleActividad>>(detalleActividadList,HttpStatus.OK);
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

    @GetMapping("/detalle-actividad/{id}")
    public ResponseEntity<?> show(@PathVariable String id) {
        Map<String, Object> response = new HashMap<>();
        try {
          DetalleActividad detalleActividad =  detalleActividadServices.findById(id);
            if (detalleActividad == null)
            {
                response.put("Mensaje","No existe el registro con id { ".concat(id).concat(" }"));
                return  new ResponseEntity<Map<String,Object> >(response,HttpStatus.NOT_FOUND);
            }
            else
            {
                return new ResponseEntity<DetalleActividad>(detalleActividad,HttpStatus.OK);
            }

        }
        catch (CannotCreateTransactionException e)
        {
            response.put("Mensaje","Error al realizar la consulta a la base de datos");
            response.put("Error", e.getMessage().concat(" : ").concat(e.getMostSpecificCause().getMessage()));
            return  new ResponseEntity<Map<String,Object>>(response,HttpStatus.SERVICE_UNAVAILABLE);

        }
        catch (DataAccessException e)
        {
            response.put("Mensaje","Error al realizar la consulta a la base de datos");
            response.put("Error",e.getMessage().concat(" : ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String,Object>>(response,HttpStatus.SERVICE_UNAVAILABLE);
        }
    }

    @PostMapping("/detalle-actividad")
    public ResponseEntity<?> create(@Valid @RequestBody DetalleActividad value,BindingResult result)
    {
        Map<String, Object> response = new HashMap<>();
        DetalleActividad detalleActividad = null;
        if (result.hasErrors())
        {
            List<String> errores = result.getFieldErrors()
                    .stream()
                    .map(err -> err.getDefaultMessage())
                    .collect(Collectors.toList());
            response.put("Error",errores);
            return new ResponseEntity<Map<String,Object>>(response,HttpStatus.BAD_REQUEST);
        }
        try
        {
            value.setActividadId(UUID.randomUUID().toString());
            detalleActividad = detalleActividadServices.save(value);
            return new ResponseEntity<DetalleActividad>(detalleActividad,HttpStatus.CREATED);
        }
        catch (CannotCreateTransactionException e)
        {
            response.put("Mensaje","Error en la conexion a la base de datos");
            response.put("Error",e.getMessage().concat(":  ").concat(e.getMostSpecificCause().getMessage()));
            return  new ResponseEntity<Map<String,Object>>(response,HttpStatus.SERVICE_UNAVAILABLE);
        }
        catch (DataAccessException e)
        {
            response.put("Mensaje","Error al realizar la consulta a la base de datos");
            response.put("Error", e.getMessage().concat(" : ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String,Object>>(response,HttpStatus.SERVICE_UNAVAILABLE);
        }
    }
    @PutMapping("/detalle-actividad/{id}")
    public ResponseEntity<?> update(@PathVariable String id,@Valid @RequestBody DetalleActividad value, BindingResult result) {
        Map<String, Object> response = new HashMap<String, Object>();
        try {
            if (result.hasErrors()) {
                List<String> errores = result.getFieldErrors()
                        .stream().map(err -> err.getDefaultMessage()).collect(Collectors.toList());
                response.put("Error", errores);
                return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
            }
            DetalleActividad update = detalleActividadServices.findById(id);
            if (update == null) {
                response.put("Mensaje", "No existe el modulo con el id [ ".concat(id).concat(" ]"));
                return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
            }

            update.setNombreActividad(value.getNombreActividad());
            update.setNota(value.getNota());
            update.setFechaCreacion(value.getFechaCreacion());
            update.setFechaEntrega(value.getFechaEntrega());
            update.setFechaPostergacion(value.getFechaPostergacion());
            update.setEstado(value.getEstado());
            update.setSeminarios(value.getSeminarios());
            detalleActividadServices.save(update);
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NO_CONTENT);
        } catch (CannotCreateTransactionException e) {
            response.put("Mensaje", "Error al realizar la conexio a la base de datos");
            response.put("Error", e.getMessage().concat(" : ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.SERVICE_UNAVAILABLE);
        } catch (DataAccessException e) {
            response.put("Mensaje", "Error al realizar la consulta a la base de datos");
            response.put("Error", e.getMessage().concat(":").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.SERVICE_UNAVAILABLE);

        }


    }

    @DeleteMapping("/detalle-actividad/{id}")
    public ResponseEntity<?> delete(@PathVariable String id)
    {
        Map<String,Object> response = new HashMap<String, Object>();
        DetalleActividad detalleActividad  = detalleActividadServices.findById(id);
        try {
            if (detalleActividad == null)
            {
                response.put("Mensaje","No existe el ID [".concat(id).concat("]"));
                return new ResponseEntity<Map<String,Object>>(response,HttpStatus.NOT_FOUND);
            }
            detalleActividadServices.delete(detalleActividad);
            return new ResponseEntity<DetalleActividad>(detalleActividad,HttpStatus.OK);
        }
        catch (CannotCreateTransactionException e)
        {
            response.put("Mensaje","Error al realizar la conexion a DB");
            return new ResponseEntity<Map<String,Object>>(response, HttpStatus.SERVICE_UNAVAILABLE);
        }
        catch (DataAccessException e)
        {
            response.put("Mensaje","Error al realizar la consulta a la base de datos");
            response.put("Error",e.getMessage().concat(":").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String,Object>>(response,HttpStatus.SERVICE_UNAVAILABLE);
        }
    }

}