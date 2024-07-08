package edu.kalum.notas.core.models.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "modulo")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Modulo implements Serializable {

    @Id
    @Column(name = "modulo_id")
    private String moduloId;

    @NotEmpty
    @Column(name = "nombre_modulo")
    private String nombreModulo;


    @Column(name = "numero_seminarios")
    @Min(1)
    @Max(5)
    private int numeroSeminarios;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "carrera_id",referencedColumnName = "carrera_id")
    private CarreraTecnica carreraTecnica;

    @JsonIgnore
    @JsonIgnoreProperties({"hibernateLazyInitializer","handler"})
    @OneToMany(mappedBy = "modulos", fetch = FetchType.EAGER)
    private List<Seminario> seminarios;



}
