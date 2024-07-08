package edu.kalum.notas.core.controllers;

import edu.kalum.notas.core.models.entity.Alumno;
import edu.kalum.notas.core.models.entity.AsignacionAlumno;
import edu.kalum.notas.core.models.service.AlumnoServicesImpl;
import edu.kalum.notas.core.models.service.AsignacionAlumnoServicesImpl;
import edu.kalum.notas.core.models.service.IAlumnoAsignacionService;
import edu.kalum.notas.core.models.service.IAlumnoService;
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
public class AsignacionAlumnoController {

    @Autowired
    private IAlumnoAsignacionService asignacionAlumnoServices;

    @Autowired
    private IAlumnoService alumnoService;

    @GetMapping("/asignacion-alumno")
    public ResponseEntity<?> listarAsignaciones()
    {
        Map<String,Object> response = new HashMap<>();
        try {
            List<AsignacionAlumno> asignacionAlumnos  = asignacionAlumnoServices.findAll();
            if (asignacionAlumnos.size() == 0)
            {
                response.put("Mensaje","No existen Registros");
                return new ResponseEntity<Map<String,Object>> (response, HttpStatus.NO_CONTENT);
            }
            else
            {
                return new ResponseEntity<List<AsignacionAlumno>>(asignacionAlumnos,HttpStatus.OK);
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

    @GetMapping("/asignacion-alumno/{id}")
    public ResponseEntity<?> lookById(@PathVariable String id)
    {
        Map<String,Object> response = new HashMap<>();
        try {
            AsignacionAlumno asignacionAlumno =asignacionAlumnoServices.findById(id);
            if (asignacionAlumno == null)
            {
                response.put("Mensaje","No existe el registro con id { ".concat(id).concat(" }"));
                return  new ResponseEntity<Map<String,Object> >(response,HttpStatus.NOT_FOUND);
            }
            else
            {
                return new ResponseEntity<AsignacionAlumno>(asignacionAlumno,HttpStatus.OK);
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

    @PostMapping("/asignacion-alumno")
    public ResponseEntity<?> create(@Valid @RequestBody AsignacionAlumno value, BindingResult result)
    {
        Map<String, Object> response = new HashMap<>();
        AsignacionAlumno asignacionAlumno = null;
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
            value.setAsignacionId(UUID.randomUUID().toString());
            asignacionAlumno = asignacionAlumnoServices.save(value);
            response.put("Mensaje","Asignacion Creada con exito");
            return new ResponseEntity<AsignacionAlumno>(asignacionAlumno,HttpStatus.CREATED);

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

    @PutMapping("/asignacion-alumno/{id}")
    public ResponseEntity<?> modify(@PathVariable String id,@Valid @RequestBody AsignacionAlumno value,BindingResult result)
    {
        Map<String ,Object> response =  new HashMap<String, Object>();
        try {
            if (result.hasErrors())
            {
                List<String> errores = result
                        .getFieldErrors()
                        .stream().map(err -> err.getDefaultMessage())
                        .collect(Collectors.toList());
                response.put("Error",errores);
                return  new ResponseEntity<Map<String,Object>>(response,HttpStatus.BAD_REQUEST);
            }
            AsignacionAlumno update = asignacionAlumnoServices.findById(id);
            if (update == null)
            {
                response.put("Mensaje","No existe el modulo con el id [ ".concat(id).concat(" ]"));
                return new ResponseEntity<Map<String,Object>>(response,HttpStatus.NOT_FOUND);
            }
            update.setClaseId(value.getClaseId());
            update.setFechaAsignacion(value.getFechaAsignacion());
            update.setObservaciones(value.getObservaciones());
            update.setAlumno(value.getAlumno());
            asignacionAlumnoServices.save(update);
            return new ResponseEntity<Map<String,Object>> (response,HttpStatus.NO_CONTENT);


        }
        catch (CannotCreateTransactionException e)
        {
            response.put("Mensaje","Error al realizar la conexio a la base de datos");
            response.put("Error",e.getMessage().concat(" : ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String,Object>>(response,HttpStatus.SERVICE_UNAVAILABLE);
        }
        catch (DataAccessException e)
        {
            response.put("Mensaje","Error al realizar la consulta a la base de datos");
            response.put("Error",e.getMessage().concat(":").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String,Object>>(response,HttpStatus.SERVICE_UNAVAILABLE);

        }
    }

    @DeleteMapping("/asignacion-alumno/{id}")
    public ResponseEntity<?> delete(@PathVariable String id)
    {
        Map<String,Object> response = new HashMap<String, Object>();
        AsignacionAlumno asignacionAlumno = asignacionAlumnoServices.findById(id);
        try {
            if (asignacionAlumno == null)
            {
                response.put("Mensaje","No existe el ID [".concat(id).concat("]"));
                return new ResponseEntity<Map<String,Object>>(response,HttpStatus.NOT_FOUND);
            }
            asignacionAlumnoServices.delete(asignacionAlumno);
            return new ResponseEntity<AsignacionAlumno>(asignacionAlumno,HttpStatus.OK);
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
