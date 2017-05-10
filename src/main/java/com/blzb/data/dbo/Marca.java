package com.blzb.data.dbo;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by apimentel on 4/25/17.
 */
@Entity
public class Marca {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @ManyToOne
    private Persona persona;

    @ManyToOne
    private Actividad actividad;

    private int duracion;
    @Temporal(value = TemporalType.DATE)
    private Date fecha;

    @PrePersist
    void prePersist(){
        if(fecha == null){
            fecha = new Date();
        }
    }
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Persona getPersona() {
        return persona;
    }

    public void setPersona(Persona persona) {
        this.persona = persona;
    }

    public Actividad getActividad() {
        return actividad;
    }

    public void setActividad(Actividad actividad) {
        this.actividad = actividad;
    }

    public int getDuracion() {
        return duracion;
    }

    public void setDuracion(int duracion) {
        this.duracion = duracion;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }
}
