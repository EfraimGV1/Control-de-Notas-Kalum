package edu.kalum.notas.core.models.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "seminario")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Seminario implements Serializable {

    @Id
    @Column(name = "seminario_id")
    private String seminarioId;
    @Column(name = "nombre_seminario")
    @NotEmpty
    private String nombreSeminario;
    @Column(name = "fecha_inicio")
    private Date fechaInicio;
    @Column(name = "fecha_fin")
    private Date fechaFin;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "modulo_id",referencedColumnName = "modulo_id")
    private Modulo modulos;

    @JsonIgnore
    @JsonIgnoreProperties({"hibernateLazyInitializer","handler"})
    @OneToMany(mappedBy = "seminarios", fetch = FetchType.EAGER)
    private List<DetalleActividad> detalleActividad;

}
