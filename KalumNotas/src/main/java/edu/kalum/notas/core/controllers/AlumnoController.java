package edu.kalum.notas.core.controllers;

import edu.kalum.notas.core.models.entity.Alumno;
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
public class AlumnoController {

    @Autowired
    private IAlumnoService alumnoService;

    @RequestMapping("/alumnos")
    public ResponseEntity<?> listarAlumnos()
    {
        Map<String,Object> response = new HashMap<>();

        try {
            List<Alumno> alumnos = alumnoService.findAll();
            if (alumnos.size() == 0)
            {
                response.put("Mensaje","No existen Registros");
                return new ResponseEntity<Map<String,Object>> (response, HttpStatus.NO_CONTENT);
            }
            else
            {
                return new ResponseEntity<List< Alumno>>(alumnos,HttpStatus.OK);
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

    @GetMapping("/alumnos/{id}")
    public ResponseEntity<?> lookById(@PathVariable String id)
    {
        Map<String,Object> response = new HashMap<>();
        try {
            Alumno alumno = alumnoService.findById(id);
            if (alumno == null)
            {
                response.put("Mensaje","No existe el registro con id { ".concat(id).concat(" }"));
                return  new ResponseEntity<Map<String,Object> >(response,HttpStatus.NOT_FOUND);
            }
            else
            {
                return new ResponseEntity<Alumno>(alumno,HttpStatus.OK);
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

    @PostMapping("/alumnos")
    public ResponseEntity<?> create(@Valid @RequestBody Alumno value, BindingResult result)
    {
        Map<String, Object> response = new HashMap<>();
        Alumno alumno = null;
        if (result.hasErrors())
        {
            List<String> errores = result.getFieldErrors()
                    .stream().map(err -> err.getDefaultMessage()).collect(Collectors.toList());
            response.put("Errores",errores);
            return new ResponseEntity<Map<String, Object>>(response,HttpStatus.BAD_REQUEST);
        }
        try
        {
            value.setId(UUID.randomUUID().toString());
            alumno = alumnoService.save(value);
            response.put("Mensaje","Alumno Creado correctamente");
            return new ResponseEntity<Alumno>(alumno,HttpStatus.CREATED);
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
    @PutMapping("/alumnos/{id}")
    public ResponseEntity<?> update(@PathVariable String id,@Valid @RequestBody Alumno value,BindingResult result)
    {
        Map<String ,Object> response =  new HashMap<String, Object>();
        try {
            if (result.hasErrors())
            {
                List<String> errores = result.getFieldErrors()
                        .stream().map(err -> err.getDefaultMessage()).collect(Collectors.toList());
                response.put("Error",errores);
                return  new ResponseEntity<Map<String,Object>>(response,HttpStatus.BAD_REQUEST);
            }
            Alumno update = alumnoService.findById(id);
            if(update == null)
            {
                response.put("Mensaje","No existe el modulo con el id [ ".concat(id).concat(" ]"));
                return new ResponseEntity<Map<String,Object>>(response,HttpStatus.NOT_FOUND);
            }
            update.setCarne(value.getCarne());
            update.setNombre(value.getNombre());
            update.setApellidos(value.getApellidos());
            update.setFechaNacimiento(value.getFechaNacimiento());
            update.setNoExpediente(value.getNoExpediente());
            alumnoService.save(update);
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

    @DeleteMapping("/alumnos/{id}")
    public ResponseEntity<?> delete(@PathVariable String id)
    {
        Map<String,Object> response = new HashMap<String, Object>();
        Alumno alumno = alumnoService.findById(id);
        try
        {
            if (alumno == null)
            {
                response.put("Mensaje","No existe el ID [".concat(id).concat("]"));
                return new ResponseEntity<Map<String,Object>>(response,HttpStatus.NOT_FOUND);
            }
            alumnoService.delete(alumno);
            return new ResponseEntity<Alumno>(alumno,HttpStatus.OK);
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
