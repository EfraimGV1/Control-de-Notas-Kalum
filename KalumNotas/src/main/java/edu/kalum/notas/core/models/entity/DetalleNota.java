package edu.kalum.notas.core.models.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "detalle_nota")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DetalleNota implements Serializable {

    @Id
    @Column(name = "detalle_nota_id")
    private String  detalleNotaId;


    @Column(name = "valor_nota")
    private int nota;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "alumno_id",referencedColumnName = "id")
    private Alumno alumno;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "detalle_actividad_id",referencedColumnName = "detalle_actividad_id")
    private DetalleActividad detalleActividad;


}
