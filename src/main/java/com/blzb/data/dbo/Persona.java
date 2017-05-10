package com.blzb.data.dbo;

import org.hibernate.annotations.GeneratorType;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

/**
 * Created by apimentel on 4/25/17.
 */
@Entity
@SequenceGenerator(name="persona_seq", sequenceName="persona_seq", allocationSize=1)
public class Persona {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "persona_seq")
    private Long id;

    private String nombre;
    private String apellido;
    private String correo;
    @Temporal(value = TemporalType.DATE)
    private Date fechaNacimiento;

    private String password;

    @OneToMany
    List<Actividad> actividades;

    @OneToMany
    List<Estado> estados;

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

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public Date getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(Date fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public List<Actividad> getActividades() {
        return actividades;
    }

    public void setActividades(List<Actividad> actividades) {
        this.actividades = actividades;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Transient
    public String getNombreCompleto(){
        return nombre+" "+apellido;
    }
}
