package com.blzb.data.dbo;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by apimentel on 4/25/17.
 */
@Entity
@SequenceGenerator(name="estado_seq", sequenceName="estado_seq", allocationSize=1)
public class Estado {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "estado_seq")
    private Long id;
    @Temporal(value = TemporalType.DATE)
    private Date fecha;
    private float estatura;
    private float peso;
    @ManyToOne
    private Persona persona;

    public Long getId() {
        return id;
    }

    @PrePersist
    void prePersist(){
        if(fecha == null){
            fecha = new Date();
        }
    }


    public void setId(Long id) {
        this.id = id;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public float getEstatura() {
        return estatura;
    }

    public void setEstatura(float estatura) {
        this.estatura = estatura;
    }

    public float getPeso() {
        return peso;
    }

    public void setPeso(float peso) {
        this.peso = peso;
    }

    public Persona getPersona() {
        return persona;
    }

    public void setPersona(Persona persona) {
        this.persona = persona;
    }
}
