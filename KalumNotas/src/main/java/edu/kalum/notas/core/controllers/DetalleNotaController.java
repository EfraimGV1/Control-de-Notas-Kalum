package edu.kalum.notas.core.controllers;

import edu.kalum.notas.core.models.entity.DetalleNota;
import edu.kalum.notas.core.models.entity.Modulo;
import edu.kalum.notas.core.models.service.IAlumnoService;
import edu.kalum.notas.core.models.service.IDetalleActividadService;
import edu.kalum.notas.core.models.service.IDetalleNotaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.CannotCreateTransactionException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.swing.plaf.PanelUI;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/kalum-notas/v1")
public class DetalleNotaController {

    @Autowired
    private IDetalleNotaService detalleNotaService;

    @Autowired
    private IAlumnoService alumnoService;

    @Autowired
    private IDetalleActividadService detalleActividadService;

    @RequestMapping("/detalle-nota")
    public ResponseEntity<?> listarNotas()
    {
        Map<String,Object> response = new HashMap<>();
        try {
            List<DetalleNota> detalleNotas = this.detalleNotaService.findAll();
            if (detalleNotas.size() == 0)
            {
                response.put("Mensaje","No existen Registros");
                return new ResponseEntity<Map<String,Object>> (response, HttpStatus.NO_CONTENT);
            }
            else
            {
                return new ResponseEntity<List<DetalleNota>>(detalleNotas,HttpStatus.OK);
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

    @RequestMapping("/detalle-nota/{id}")
    public ResponseEntity<?> lookById(@PathVariable String id)
    {
        Map<String,Object> response = new HashMap<>();
        try {
            DetalleNota detalleNota = detalleNotaService.findById(id);
            if (detalleNota == null)
            {
                response.put("Mensaje","No existe el registro con id { ".concat(id).concat(" }"));
                return  new ResponseEntity<Map<String,Object> >(response,HttpStatus.NOT_FOUND);
            }
            else
            {
                return new ResponseEntity<DetalleNota>(detalleNota,HttpStatus.OK);
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

    @PostMapping("/detalle-nota")
    public ResponseEntity<?> create(@Valid @RequestBody DetalleNota value, BindingResult result)
    {
        Map<String, Object> response = new HashMap<>();
        DetalleNota detalleNota = null;
        if (result.hasErrors())
        {
            List<String> errores = result
                    .getFieldErrors()
                    .stream().map(err -> err.getDefaultMessage())
                    .collect(Collectors.toList());
            response.put("Errores",errores);
            return new ResponseEntity<Map<String, Object>>(response,HttpStatus.BAD_REQUEST);
        }
        try {
            value.setDetalleNotaId(UUID.randomUUID().toString());
            detalleNota = detalleNotaService.save(value);
            response.put("Mensaje","Nota Creada con exito");
            return new ResponseEntity<DetalleNota>(detalleNota,HttpStatus.CREATED);
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

    @PutMapping("/detalle-nota/{id}")
    public ResponseEntity<?> modify(@PathVariable String id, @Valid @RequestBody DetalleNota value, BindingResult result) {
        Map<String, Object> response = new HashMap<String, Object>();
        try {
            if (result.hasErrors())
            {
                List<String> errores = result
                        .getFieldErrors()
                        .stream()
                        .map(err -> err.getDefaultMessage())
                        .collect(Collectors.toList());
                response.put("Error",errores);
                return  new ResponseEntity<Map<String,Object>>(response,HttpStatus.BAD_REQUEST);
            }
            DetalleNota update = detalleNotaService.findById(id);
            if (update == null)
            {
                response.put("Mensaje","No existe el modulo con el id [ ".concat(id).concat(" ]"));
                return new ResponseEntity<Map<String,Object>>(response,HttpStatus.NOT_FOUND);
            }
            update.setNota(value.getNota());
            update.setAlumno(value.getAlumno());
            update.setDetalleActividad(value.getDetalleActividad());
            detalleNotaService.save(update);
            return new ResponseEntity<Map<String,Object>>(response,HttpStatus.NO_CONTENT);

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

    public ResponseEntity<?> delete(@PathVariable String id)
    {
        Map<String,Object> response = new HashMap<String, Object>();

        DetalleNota detalleNota = detalleNotaService.findById(id);
        try {
            if (detalleNota == null)
            {
                response.put("Mensaje","No existe el ID [".concat(id).concat("]"));
                return new ResponseEntity<Map<String,Object>>(response,HttpStatus.NOT_FOUND);
            }
            detalleNotaService.delete(detalleNota);
            return new ResponseEntity<DetalleNota>(detalleNota,HttpStatus.OK);
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
