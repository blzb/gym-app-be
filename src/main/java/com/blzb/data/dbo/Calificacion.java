package com.blzb.data.dbo;

import javax.persistence.*;

/**
 * Created by apimentel on 4/25/17.
 */
@Entity
@SequenceGenerator(name="calificacion_seq", sequenceName="calificacion_seq", allocationSize=1)
public class Calificacion {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "calificacion_seq")
    private Long id;
    private float valor;
    private String materia;
    private String semetre;
    @OneToOne
    Persona persona;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public float getValor() {
        return valor;
    }

    public void setValor(float valor) {
        this.valor = valor;
    }

    public String getMateria() {
        return materia;
    }

    public void setMateria(String materia) {
        this.materia = materia;
    }

    public String getSemetre() {
        return semetre;
    }

    public void setSemetre(String semetre) {
        this.semetre = semetre;
    }

    public Persona getPersona() {
        return persona;
    }

    public void setPersona(Persona persona) {
        this.persona = persona;
    }

}
