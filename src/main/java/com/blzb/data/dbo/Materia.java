package com.blzb.data.dbo;

import javax.persistence.*;

/**
 * Created by apimentel on 4/25/17.
 */
@Entity
@SequenceGenerator(name="materia_seq", sequenceName="materia_seq", allocationSize=1)
public class Materia {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "materia_seq")
    private Long id;

    private String nombre;
    private Long semestre;
    private String carrera;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Long getSemestre() {
        return semestre;
    }

    public void setSemestre(Long semestre) {
        this.semestre = semestre;
    }

    public String getCarrera() {
        return carrera;
    }

    public void setCarrera(String carrera) {
        this.carrera = carrera;
    }
}
