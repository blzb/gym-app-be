package com.blzb.data.dbo;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * Created by apimentel on 4/25/17.
 */
@Entity
@SequenceGenerator(name="marca_seq", sequenceName="marca_seq", allocationSize=1)
public class Marca {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "marca_seq")
    private Long id;
    @ManyToOne
    @NotNull
    private Persona persona;

    @ManyToOne
    @NotNull
    private Actividad actividad;

    private int duracion;
    @Temporal(value = TemporalType.DATE)
    private Date fecha;

    private int calorias;

    @PrePersist
    void prePersist() {
        if (fecha == null) {
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

    public int getCalorias() {
        return calorias;
    }

    public void setCalorias(int calorias) {
        this.calorias = calorias;
    }
}
