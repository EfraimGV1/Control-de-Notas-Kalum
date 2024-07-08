package edu.kalum.notas.core.controllers;

import com.sun.org.apache.xpath.internal.operations.Mod;
import com.sun.xml.bind.v2.model.core.ID;
import edu.kalum.notas.core.models.entity.CarreraTecnica;
import edu.kalum.notas.core.models.entity.Modulo;
import edu.kalum.notas.core.models.service.ICarreraTecnicaService;
import edu.kalum.notas.core.models.service.IModuloService;
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
public class ModuloController {

    @Autowired
    private IModuloService moduloService;

    @Autowired
    private ICarreraTecnicaService carreraTecnicaService;

    @GetMapping("/modulos")
    public ResponseEntity<?> listarMdoulos()
    {
        Map<String,Object> response = new HashMap<>();
        try
        {
            List<Modulo> modulos = this.moduloService.findAll();
            if (modulos.size() == 0)
            {
                response.put("Mensaje","No existen Registros");
                return new ResponseEntity<Map<String,Object>> (response, HttpStatus.NO_CONTENT);
            }else
            {
                return  new ResponseEntity<List<Modulo>> (modulos, HttpStatus.OK);
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
    @GetMapping("/modulos/{id}")
    public ResponseEntity<?> lookById(@PathVariable String id)
    {
        Map<String,Object> response = new HashMap<>();
         try {
             Modulo modulo = moduloService.findById(id);
             if (modulo == null)
             {
                 response.put("Mensaje","No existe el registro con id { ".concat(id).concat(" }"));
                 return  new ResponseEntity<Map<String,Object> >(response,HttpStatus.NOT_FOUND);

             }
             else {
                 return new ResponseEntity<Modulo>(modulo,HttpStatus.OK);
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
    @PostMapping("/modulos")
    public ResponseEntity<?>  create(@Valid @RequestBody Modulo value, BindingResult result)
    {
        Map<String, Object> response = new HashMap<>();
        Modulo modulo  = null;
        if (result.hasErrors())
        {
            List<String> errores = result
                    .getFieldErrors()
                    .stream().map(err -> err.getDefaultMessage())
                    .collect(Collectors.toList());
            response.put("Errores",errores);
            return new ResponseEntity<Map<String, Object>>(response,HttpStatus.BAD_REQUEST);
        }
        try
        {
           value.setModuloId(UUID.randomUUID().toString());
           modulo = this.moduloService.save(value);
           response.put("Mensaje","Modulo Creada con exito");
           return new ResponseEntity<Modulo>(modulo, HttpStatus.CREATED);
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

    @PutMapping("/modulos/{id}")
    public ResponseEntity<?> update(@PathVariable String id,@Valid @RequestBody Modulo value,BindingResult result){
        Map<String ,Object> response =  new HashMap<String, Object>();
        try {
            if (result.hasErrors())
            {
                List<String> errores = result.getFieldErrors()
                        .stream().map(err -> err.getDefaultMessage()).collect(Collectors.toList());
                response.put("Error",errores);
                return  new ResponseEntity<Map<String,Object>>(response,HttpStatus.BAD_REQUEST);
            }

                Modulo update = moduloService.findById(id);
                if(update == null)
                {
                    response.put("Mensaje","No existe el modulo con el id [ ".concat(id).concat(" ]"));
                    return new ResponseEntity<Map<String,Object>>(response,HttpStatus.NOT_FOUND);
                }
                update.setCarreraTecnica(value.getCarreraTecnica());
                update.setNombreModulo(value.getNombreModulo());
                update.setNumeroSeminarios(value.getNumeroSeminarios());
                moduloService.save(update);
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
    @DeleteMapping("/modulos/{id}")
    public ResponseEntity<?> delete(@PathVariable String id)
    {
        Map<String,Object> response = new HashMap<String, Object>();
        Modulo modulo = moduloService.findById(id);
        try {
            if (modulo == null)
            {
                response.put("Mensaje","No existe el ID [".concat(id).concat("]"));
                return new ResponseEntity<Map<String,Object>>(response,HttpStatus.NOT_FOUND);
            }
            moduloService.delete(modulo);
            return new ResponseEntity<Modulo>(modulo,HttpStatus.OK);
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
