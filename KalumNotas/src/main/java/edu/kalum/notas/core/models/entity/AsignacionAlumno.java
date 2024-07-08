package edu.kalum.notas.core.models.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "asignacion_alumno")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AsignacionAlumno implements Serializable {
    @Id
    @Column(name = "asignacion_id")
    private String asignacionId;

    @Column(name = "fecha_asignacion")
    private Date fechaAsignacion;

    @Column(name = "observaciones")
    private String observaciones;

    @Column(name = "clase_id")
    private String claseId;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "alumno_id",referencedColumnName = "id")
    private Alumno alumno;

}
