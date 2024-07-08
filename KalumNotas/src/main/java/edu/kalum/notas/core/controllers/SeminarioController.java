package edu.kalum.notas.core.controllers;

import edu.kalum.notas.core.models.entity.Modulo;
import edu.kalum.notas.core.models.entity.Seminario;
import edu.kalum.notas.core.models.service.IModuloService;
import edu.kalum.notas.core.models.service.ISeminarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.CannotCreateTransactionException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/kalum-notas/v1")
public class SeminarioController {

    @Autowired
    private ISeminarioService seminarioService;
    @Autowired
    private IModuloService moduloService;


    @GetMapping("/seminarios")
    public ResponseEntity<?> listarSeminarios()
    {
        Map<String,Object> response = new HashMap<>();

        try
        {
            List<Seminario> seminarios = this.seminarioService.findAll();
            if (seminarios.size() == 0)
            {
                response.put("Mensaje","No existen registros");
                return new ResponseEntity<Map<String,Object>> (response, HttpStatus.NO_CONTENT);
            }
            else
            {
                return  new ResponseEntity<List<Seminario>>(seminarios, HttpStatus.OK);
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
    @GetMapping("/seminarios/{id}")
    public ResponseEntity<?> show(@PathVariable String id)
    {
        Map<String,Object> response = new HashMap<>();
        try
        {
            Seminario seminario = seminarioService.findById(id);
            if (seminario == null)
            {
                response.put("Mensaje","No existe el registro con id { ".concat(id).concat(" }"));
                return  new ResponseEntity<Map<String,Object> >(response,HttpStatus.NOT_FOUND);

            }else
            {
                return new ResponseEntity<Seminario>(seminario,HttpStatus.OK);
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

    @PostMapping("/seminarios")
    public  ResponseEntity<?> create(@Valid @RequestBody Seminario value, BindingResult result)
    {
        Map<String,Object> response = new HashMap<>();
        Seminario seminario = null;
        if (result.hasErrors())
        {
            List<String> errores = result.getFieldErrors()
                    .stream()
                    .map(err -> err.getDefaultMessage())
                    .collect(Collectors.toList());
            response.put("Error",errores);
            return new ResponseEntity<Map<String,Object>>(response,HttpStatus.BAD_REQUEST);
        }
        try {
            value.setSeminarioId(UUID.randomUUID().toString());

            seminario = this.seminarioService.save(value);
            response.put("Mensaje","Seminario Creado con exito");
            return new ResponseEntity<Seminario>(seminario,HttpStatus.CREATED);
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

    @PutMapping("/seminarios/{id}")
    public ResponseEntity<?> update(@PathVariable String id, @Valid @RequestBody Seminario value, BindingResult result){
        Map<String ,Object> response =  new HashMap<String, Object>();
        try {
            if (result.hasErrors())
            {
                List<String> errores = result.getFieldErrors()
                        .stream().map(err -> err.getDefaultMessage()).collect(Collectors.toList());
                response.put("Error",errores);
                return  new ResponseEntity<Map<String,Object>>(response,HttpStatus.BAD_REQUEST);
            }

            Seminario update = seminarioService.findById(id);
            if(update == null)
            {
                response.put("Mensaje","No existe el modulo con el id [ ".concat(id).concat(" ]"));
                return new ResponseEntity<Map<String,Object>>(response,HttpStatus.NOT_FOUND);
            }
            update.setModulos(value.getModulos());
            update.setNombreSeminario(value.getNombreSeminario());
            update.setFechaInicio(value.getFechaInicio());
            update.setFechaFin(value.getFechaFin());
            seminarioService.save(update);
            return new ResponseEntity<Map<String,Object>> (response,HttpStatus.NO_CONTENT);

        }catch (CannotCreateTransactionException e)
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

    @DeleteMapping("/seminarios/{id}")
    public ResponseEntity<?> delete(@PathVariable String id)
    {
        Map<String,Object> response = new HashMap<String, Object>();
        Seminario seminario = seminarioService.findById(id);
        try {
            if (seminario == null)
            {
                response.put("Mensaje","No existe el ID [".concat(id).concat("]"));
                return new ResponseEntity<Map<String,Object>>(response,HttpStatus.NOT_FOUND);
            }
            seminarioService.delete(seminario);
            return  new ResponseEntity<Seminario>(seminario,HttpStatus.OK);
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
