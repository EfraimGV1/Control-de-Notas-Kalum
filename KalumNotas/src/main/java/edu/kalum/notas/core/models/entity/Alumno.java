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
@Table(name ="alumno")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Alumno implements Serializable {

    @Id
    @Column(name = "id")
    public String id;

    @Column(name = "carne")
    @NotEmpty(message = "Campo necesario")
    public String carne;

    @Column(name = "nombres")
    @NotEmpty(message = "Campo Necesario")
    public String nombre;

    @Column(name = "apellidos")
    @NotEmpty(message = "Campo Necesario")
    public String apellidos;

    @Column(name = "fecha_nacimiento")
    private Date fechaNacimiento;

    @Column(name = "no_expediente")
    private String noExpediente;

    @JsonIgnore
    @JsonIgnoreProperties({"hibernateLazyInitializer","handler"})
    @OneToMany(mappedBy = "alumno", fetch = FetchType.EAGER)
    private List<DetalleNota> detalleNotas;

    @JsonIgnore
    @JsonIgnoreProperties({"hibernateLazyInitializer","handler"})
    @OneToMany(mappedBy = "alumno",fetch = FetchType.LAZY)
    private List<AsignacionAlumno> asignacionAlumnos;
}
