package edu.kalum.notas.core.models.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "detalle_actividad")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DetalleActividad implements Serializable {

    @Id
    @Column(name = "detalle_actividad_id")
    private String actividadId;

    @Column(name = "nombre_actividad")
    private String nombreActividad;

    @Column(name = "nota_actividad")
    private int nota;

    @Column(name = "fecha_creacion")
    private Date fechaCreacion;

    @Column(name = "fecha_entrega")
    private Date fechaEntrega;

    @Column(name = "fecha_postergacion")
    private Date fechaPostergacion;

    @Column(name = "estado")
    private String estado;


    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "seminario_id",referencedColumnName = "seminario_id")
    private Seminario seminarios;

    @JsonIgnore
    @JsonIgnoreProperties({"hibernateLazyInitializer","handler"})
    @OneToMany(mappedBy = "detalleActividad",fetch = FetchType.EAGER)
    private List<DetalleNota> detalleNotas;
}
