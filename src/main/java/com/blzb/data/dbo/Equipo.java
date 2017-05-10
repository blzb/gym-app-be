package com.blzb.data.dbo;

import javax.persistence.*;
import java.util.List;

/**
 * Created by apimentel on 4/25/17.
 */
@Entity
@SequenceGenerator(name="equipo_seq", sequenceName="equipo_seq", allocationSize=1)
public class Equipo {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "equipo_seq")
    private Long id;
    private String nombre;
    private String descripcion;
    private String imagen;

    @OneToMany
    List<Reservacion> reservaciones;

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

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public List<Reservacion> getReservaciones() {
        return reservaciones;
    }

    public void setReservaciones(List<Reservacion> reservaciones) {
        this.reservaciones = reservaciones;
    }
}
