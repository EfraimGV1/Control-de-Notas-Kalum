package edu.kalum.notas.core.models.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name ="carrera_tecnica")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CarreraTecnica implements Serializable {

        @Id
        @Column(name="carrera_id")
        private String codigoCarrera;

        @NotEmpty(message = "Es necesario indicar un nombre")
        @Column(name = "nombre_carrera" ,unique = true  )
        private String nombre;
        @JsonIgnore
        @JsonIgnoreProperties({"hibernateLazyInitializer","handler"})
        @OneToMany(mappedBy = "carreraTecnica", fetch = FetchType.EAGER)
        private List<Modulo> modulos;


}
