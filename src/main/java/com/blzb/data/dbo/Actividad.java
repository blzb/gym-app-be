package com.blzb.data.dbo;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by apimentel on 4/25/17.
 */
@Entity
public class Actividad {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String nombre;
    private String codigo;
    private float mets;

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

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public float getMets() {
        return mets;
    }

    public void setMets(float mets) {
        this.mets = mets;
    }
}
